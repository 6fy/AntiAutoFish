package me.esmee.antiautofish.staff;

import java.util.ArrayList;
import java.util.UUID;

public class Alerts {

    private static final ArrayList<UUID> toggledAlertsOff = new ArrayList<>();

    public static void addToggledAlertsOn(UUID uuid) {
        toggledAlertsOff.remove(uuid);
    }

    public static void removeToggledAlertsOn(UUID uuid) {
        toggledAlertsOff.add(uuid);
    }

    public static boolean hasAlertsOn(UUID uuid) {
        return !toggledAlertsOff.contains(uuid);
    }

}
