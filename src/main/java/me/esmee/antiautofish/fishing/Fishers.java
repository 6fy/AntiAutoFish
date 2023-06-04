package me.esmee.antiautofish.fishing;

import java.util.ArrayList;
import java.util.UUID;

public class Fishers {

    public static ArrayList<Hooked> hooked = new ArrayList<>();

    public static void addHooked(Hooked hooked) {
        getHooked().add(hooked);
    }

    public static void removeHooked(Hooked hooked) {
        getHooked().remove(hooked);
    }

    public static ArrayList<Hooked> getHooked() {
        return hooked;
    }

    public static Hooked getLastHooked(UUID uuid) {
        return getHooked().stream().filter(
                hooked -> hooked.getUUID().equals(uuid) && !hooked.caughtFish()
        ).findFirst().orElse(null);
    }

    public static double getAverageProbability(UUID uuid) {
        return getHooked().stream().filter(
                hooked -> hooked.getUUID().equals(uuid)
        ).mapToDouble(Hooked::getProbability).average().orElse(0);
    }

}
