package me.esmee.antiautofish.fishing;

import me.esmee.antiautofish.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.UUID;

public class Hooked {

    private final UUID uuid;
    private long rodDownTime;
    private long rodRetractTime;

    private boolean hasCaughtFish = false;

    private boolean suspicious = false;

    private long probability = 0;

    private final Location originalLocation;

    private final String id;

    private final ArrayList<String> reasons = new ArrayList<>();

    public Hooked(Player fisher) {
        this.uuid = fisher.getUniqueId();
        this.originalLocation = fisher.getLocation();
        this.id = UUID.randomUUID().toString();
    }

    public void queue() {
        if (!this.getFisher().getGameMode().equals(GameMode.SURVIVAL)) return;
        if (!this.getFisher().isOnline()) return;

        this.setHasCaughtFish();
        this.setPlayerBehavior();

        this.rodRetractTime = System.currentTimeMillis();

        if (!this.getFisher().isInsideVehicle()) {
            this.monitorPlayerMovement();
        }

        long reactionTime = this.getRetractReactionTime();
        if (reactionTime <= Config.suspiciousReactionTime) {
            this.setSuspicious(true);
            this.setProbability((long) (100 - (reactionTime / 10) * 3.5));
            this.addReason("Suspicious reaction time");
        }
    }

    private void setPlayerBehavior() {
        Player player = this.getFisher();
        if (player == null) return;

        // check if player has an empty inventory slot
        if (player.getInventory().firstEmpty() == -1) {
            this.setSuspicious(true);
            this.setProbability(20);
            this.addReason("No empty inventory slot");
        }

        // check if player is holding a fishing rod
        if (
                player.getInventory().getItemInMainHand().getType() == null ||
                player.getInventory().getItemInOffHand().getType() == null
        ) {
            this.setSuspicious(true);
            this.setProbability(50);
            this.addReason("No fishing rod in hand");
        }

        // check if player is in an inventory
        if (
                player.getOpenInventory() == null
                || (
                        player.getOpenInventory().getType() != null
                        && player.getOpenInventory().getType().equals(InventoryType.CRAFTING)
                )
        ) return;

        // player is in an inventory, so should be unable to retract the fishing rod
        this.setSuspicious(true);
        this.setProbability(100);

        String inventoryName = player.getOpenInventory().getType().name();
        if (player.getOpenInventory().getTopInventory().getName() != null) {
            inventoryName = player.getOpenInventory().getTopInventory().getName();
        }

        this.addReason("Player is in an inventory (" + ChatColor.stripColor(inventoryName) + ")");
    }

    private void monitorPlayerMovement() {
        Player player = this.getFisher();
        if (player == null) return;

        if (this.isSuspicious()) return;

        Location location = player.getLocation();
        if (location.equals(this.originalLocation)) {
            this.setSuspicious(true);
            this.setProbability(20);
            this.addReason("Player is not moving");
            return;
        }

        if (
                location.getYaw() == this.originalLocation.getYaw()
                && location.getPitch() == this.originalLocation.getPitch()
        ) {
            this.setSuspicious(true);
            this.setProbability(15);
            this.addReason("Player is not looking around");
            return;
        }

        if (
                location.getX() == this.originalLocation.getX()
                && location.getY() == this.originalLocation.getY()
                && location.getZ() == this.originalLocation.getZ()
        ) {
            this.setSuspicious(true);
            this.setProbability(5);
            this.addReason("Player moved with mouse only");
        }
    }

    public String getId() {
        return id;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public void addReason(String reason) {
        this.reasons.add(reason);
    }

    public void setProbability(long probability) {
        if (this.getProbability() > probability) return;
        this.probability = probability;
    }

    public void setWentUnderWater() {
        this.rodDownTime = System.currentTimeMillis();
    }

    public void setHasCaughtFish() {
        this.hasCaughtFish = true;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public double getProbability() {
        return probability;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean caughtFish() {
        return hasCaughtFish;
    }

    public Player getFisher() {
        return Bukkit.getPlayer(uuid);
    }

    public long getRetractReactionTime() {
        return rodRetractTime - rodDownTime;
    }

    public String getReasons() {
        return String.join(", ", reasons);
    }
}
