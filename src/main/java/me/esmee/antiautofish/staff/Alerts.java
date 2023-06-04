package me.esmee.antiautofish.staff;

import java.util.ArrayList;
import java.util.UUID;

public class Alerts {

    private static final ArrayList<UUID> toggledAlertsOn = new ArrayList<>();

    public static void addToggledAlertsOn(UUID uuid) {
        toggledAlertsOn.add(uuid);
    }

    public static void removeToggledAlertsOn(UUID uuid) {
        toggledAlertsOn.remove(uuid);
    }

    public static boolean hasAlertsOn(UUID uuid) {
        return toggledAlertsOn.contains(uuid);
    }

}
