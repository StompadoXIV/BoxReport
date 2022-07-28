package me.report.runnable;

import me.report.dao.DelayDAO;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldowns extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().stream().filter(DelayDAO::contains).forEach(players -> {
            long time = DelayDAO.getRemainingTime(players);
            if (time <= 0) {
                DelayDAO.remove(players);
                return;
            }

            DelayDAO.getTime().replace(players.getName(), time, (time - 1000L));
        });
    }
}