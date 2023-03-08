package net.ckranz.mc.mcapi.inv;

import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class PageInv {
    private final ArrayList<ChestInv> pages = new ArrayList<>();

    public ArrayList<ChestInv> getPages() {
        return pages;
    }

    public ChestInv getPage(int page) {
        return pages.get(page);
    }

    public void addPage(ChestInv inv) {
        pages.add(inv);
    }

    public void removePage(int page) {
        pages.remove(page);
    }

    public void openInventory(Player player) {
        openInventory(player, 0);
    }

    public void openInventory(Player player, int page) {
        ChestInv inv = pages.get(page);
        if(pages.size() != 0) {
            if(page < pages.size() - 1) {
                InvItem item = new InvItem(Material.ARROW, (page + 2) + " >");
                item.addClickEvent(new ItemClickEvent() {
                    @Override
                    public void onItemClick(InventoryClickEvent e) {
                        e.setCancelled(true);
                        openInventory(player, page + 1);
                    }
                });
                inv.setItem(item, inv.getLines() * 9 - 1);
            }
            if(page > 0) {
                InvItem item = new InvItem(Material.ARROW, "< " + page);
                item.addClickEvent(new ItemClickEvent() {
                    @Override
                    public void onItemClick(InventoryClickEvent e) {
                        e.setCancelled(true);
                        openInventory(player, page - 1);
                    }
                });
                inv.setItem(item, inv.getLines() * 9 - 9);
            }
        }
        inv.openInventory(player);
    }
}