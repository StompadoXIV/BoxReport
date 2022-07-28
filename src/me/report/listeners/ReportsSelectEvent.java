package me.report.listeners;

import me.report.BoxReport;
import me.report.ConfigManager;
import me.report.dao.ReportDAO;
import me.report.model.Report;
import me.report.utils.BoxUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ReportsSelectEvent implements Listener {

    private final ConfigManager settings;

    public ReportsSelectEvent(BoxReport main) {
        settings = main.getSettings();
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void onSelectReport(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        ItemStack item = e.getCurrentItem();
        ClickType click = e.getClick();
        Player p = (Player)e.getWhoClicked();


        if (!title.equals(settings.inventoryName)) return;
        e.setCancelled(true);

        if (item == null || item.getType() == Material.AIR) return;

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();

        if (!itemCompound.hasKey("Report-ID")) return;

        Report report = ReportDAO.findReportByID(itemCompound.getInt("Report-ID"));
        Player reported = report.getPlayerReported();

        if (click == ClickType.LEFT) {

            if (reported == null) {
                BoxUtils.sendMessage(p, settings.reportNotFound);
                return;
            }

            p.teleport(reported);
            p.closeInventory();

            ReportDAO.getReports().remove(report);

            BoxUtils.sendMessage(p, settings.messageTeleport.replace("{reportado}", reported.getName()));

        } else if (click == ClickType.RIGHT) {
            p.closeInventory();
            ReportDAO.getReports().remove(report);
            BoxUtils.sendMessage(p, settings.reportExcluded);

        }
    }
}