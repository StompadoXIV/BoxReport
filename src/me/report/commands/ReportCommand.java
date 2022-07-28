package me.report.commands;

import me.report.BoxReport;
import me.report.ConfigManager;
import me.report.dao.DelayDAO;
import me.report.dao.ReportDAO;
import me.report.model.Report;
import me.report.utils.BoxUtils;
import me.report.utils.TimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    private final ConfigManager settings = BoxReport.getInstance().getSettings();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] a) {

        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("§cO console não executa esse comando.");
            return true;
        }

        Player p = (Player)s;
        if (a.length > 0) {

            if (a.length < 2) {
                BoxUtils.sendMessage(p, settings.usageCommand);
                return true;
            }

            if (DelayDAO.contains(p) && !p.hasPermission("boxreport.bypass.cooldown")) {
                String time = TimeFormatter.format(DelayDAO.getRemainingTime(p));

                BoxUtils.sendMessage(p, settings.inCooldown.replace("{tempo}", time));
                return true;
            }

            Player t = Bukkit.getPlayer(a[0]);
            if (t == null) {
                BoxUtils.sendMessage(p, settings.playerNotFound);
                return true;
            }

            if (t.hasPermission("boxreport.imune")) {
                BoxUtils.sendMessage(p, settings.immune);
                return true;
            }

            if (t == p) {
                BoxUtils.sendMessage(p, settings.reportIsPlayer);
                return true;
            }

            Report report = new Report(p.getName(), t.getName(), a[1], ReportDAO.getReports().size() + 1);
            ReportDAO.getReports().add(report);

            DelayDAO.add(p);

            for (String msg : settings.reportMessageSend)
                p.sendMessage(msg.replace("{reportado}", report.getReported()).replace("{motivo}", report.getReason()));

            for (String msg2 : settings.reportMessageReceived)
                if (p.hasPermission("boxreport.reportes")) {
                    p.sendMessage(msg2.replace("{reportado}", report.getReported()).replace("{vitima}", report.getAuthor()).replace("{motivo}", report.getReason()));
                }

        } else {
            BoxUtils.sendMessage(p, settings.usageCommand);
        }

        return false;
    }
}