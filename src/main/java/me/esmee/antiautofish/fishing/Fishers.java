package me.esmee.antiautofish.fishing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Fishers {

    public static ArrayList<Hooked> hooked = new ArrayList<>();

    public static void addHooked(Hooked hooked) {
        getHooked().add(0, hooked);
    }

    public static ArrayList<Hooked> getHooked() {
        return hooked;
    }

    public static Hooked getLastHooked(UUID uuid) {
        return getHooked().stream().filter(
                hooked -> hooked.getUUID().equals(uuid) && !hooked.caughtFish()
        ).findFirst().orElse(null);
    }

    public static Hooked getHookById(String id) {
        return getHooked().stream().filter(
                hooked -> hooked.getId().equals(id)
        ).findFirst().orElse(null);
    }

    public static ArrayList<Hooked> getHooks(UUID uuid, int start, Integer limit) {
        ArrayList<Hooked> hooks = new ArrayList<>();

        getHooked().stream().filter(
                hooked -> hooked.getUUID().equals(uuid) && hooked.isSuspicious()
        ).forEach(hooks::add);

        if (limit != null && hooks.size() > limit) {
            hooks = new ArrayList<>(hooks.subList(start, limit));
        }

        return hooks;
    }

    public static boolean hasAlerted(UUID uuid) {
        return getHooked().stream().anyMatch(
                hooked -> hooked.getUUID().equals(uuid) && hooked.isSuspicious()
        );
    }

    public static double getAverageProbability(UUID uuid) {
        double averageProbability = getHooked().stream().filter(
                hooked -> hooked.getUUID().equals(uuid)
        ).mapToDouble(Hooked::getProbability).average().orElse(0);

        return (double) Math.round(averageProbability * 100) / 100;
    }

}
