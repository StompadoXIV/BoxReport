package me.report;

import me.report.commands.ReportCommand;
import me.report.commands.ReportsCommand;
import me.report.listeners.ReportsSelectEvent;
import me.report.runnable.Cooldowns;
import me.report.utils.DateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BoxReport extends JavaPlugin {

    private static BoxReport Instance;
    private ConfigManager Settings;

    public void onEnable() {
        Instance = this;
        registerYaml();
        registerCommands();
        registerEvents();
        sendMessage();

        new Cooldowns().runTaskTimerAsynchronously(this, 20L, 20L);
    }

    private void registerCommands() {
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("reports").setExecutor(new ReportsCommand());
    }

    private void registerEvents() {
        new ReportsSelectEvent(this);
    }

    private void registerYaml() {
        Settings = new ConfigManager();
        saveDefaultConfig();
        DateManager.createConfig("inventarios");
        Settings.loadConfig();
    }

    private void sendMessage() {
        Bukkit.getConsoleSender().sendMessage("§e[BoxReport] §fCriado por §b[Stompado]");
        Bukkit.getConsoleSender().sendMessage("§b[Discord] §fhttps://discord.gg/Z6PbQgdweB");
        Bukkit.getConsoleSender().sendMessage("§e[BoxReport] §aO plugin §eBoxReport §afoi iniciado com sucesso.");
    }

    public static BoxReport getInstance() {
        return Instance;
    }

    public ConfigManager getSettings() {
        return Settings;
    }
}
