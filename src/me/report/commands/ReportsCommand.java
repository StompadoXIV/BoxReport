package me.report.commands;

import me.report.inventories.ReportInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReportsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] a) {

        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("§cO console não executa esse comando.");
            return true;
        }

        Player p = (Player)s;
        if (!p.hasPermission("boxreport.viewreports")) {
            p.sendMessage("§cVocê não possui permissão para esse comando.");
            return true;
        }

        new ReportInventory().openReportInventory(p);

        return false;
    }
}
