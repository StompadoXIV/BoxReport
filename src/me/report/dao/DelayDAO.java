package me.report.dao;

import me.report.BoxReport;
import me.report.ConfigManager;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class DelayDAO {

    private static final Map<String, Long> time = new WeakHashMap<>();

    private static final ConfigManager config = BoxReport.getInstance().getSettings();

    public static void add(Player p) {
        time.put(p.getName(), TimeUnit.MINUTES.toMillis(config.cooldown));
    }

    public static void remove(Player p) {
        time.remove(p.getName());
    }

    public static long getRemainingTime(Player p) {
        return time.get(p.getName());
    }

    public static boolean contains(Player p) {
        return time.containsKey(p.getName());
    }

    public static Map<String, Long> getTime() {
        return time;
    }
}