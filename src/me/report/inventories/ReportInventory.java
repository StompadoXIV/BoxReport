package me.report.inventories;

import me.report.BoxReport;
import me.report.ConfigManager;
import me.report.dao.ReportDAO;
import me.report.utils.BoxUtils;
import me.report.utils.ItemBuilder;
import me.report.utils.Scroller;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportInventory {

    private final ConfigManager settings = BoxReport.getInstance().getSettings();

    public void openReportInventory(Player p) {

        if (ReportDAO.getReports().isEmpty()) {

            Inventory inv = Bukkit.createInventory(null, 9 * 5, settings.inventoryName);

            ItemStack item = new ItemBuilder(Material.WEB).setName("§cSem reportes...").build();
            inv.setItem(22, item);

            p.openInventory(inv);
            BoxUtils.playSound(p, Sound.CHEST_OPEN);
            return;
        }

        List<ItemStack> items = new ArrayList<>();
        ReportDAO.getReports().forEach(report -> {

            String name = settings.titleReport.replace("{autor}", report.getAuthor());

            List<String> lore = settings.loreReport;
            lore = lore.stream().map(l -> l.replace("{acusado}", report.getReported()).replace("{data}", report.getDate()).replace("{motivo}", report.getReason())).collect(Collectors.toList());

            ItemStack icon = new ItemBuilder(BoxUtils.getPlayerHead(report.getReported())).setName(name).setLore(lore).build();

            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(icon);
            NBTTagCompound itemCompound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();

            itemCompound.setInt("Report-ID", report.getId());
            nmsItem.setTag(itemCompound);

            items.add(CraftItemStack.asBukkitCopy(nmsItem));
        });

        Scroller scroller = new Scroller.ScrollerBuilder().withName(settings.inventoryName).withArrowsSlots(18, 26).withItems(items).build();
        scroller.open(p);

        BoxUtils.playSound(p, Sound.CHEST_OPEN);
    }
}