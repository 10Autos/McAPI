package net.ckranz.mc.mcapi.inv;

import java.util.ArrayList;

import net.ckranz.mc.mcapi.inv.listeners.InvCloseEvent;
import net.ckranz.mc.mcapi.inv.listeners.InvOpenEvent;
import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import net.ckranz.mc.mcapi.inv.listeners.ItemDragEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestInv {
    private final Inventory inv;
    private final String title;
    private final JavaPlugin plugin;
    private final ArrayList<HumanEntity> players = new ArrayList<>();

    public ChestInv(String title, int lines, JavaPlugin plugin) {
        inv = Bukkit.createInventory(null, lines * 9, title);
        this.title = title;
        this.plugin = plugin;
    }

    public Inventory getInventory() {
        return inv;
    }

    public void openInventory(Player player) {
        player.openInventory(inv);
    }


    public int getLines() {
        return inv.getSize() / 9;
    }

    public ItemStack getItem(int position) {
        return inv.getItem(position);
    }

    public void setItem(ItemStack item, int position) {
        inv.setItem(position, item);
        if(item instanceof InvItem) {
            for(ItemClickEvent listener : (((InvItem) item).getClickEvents())) {
                addClickEvent(new ItemClickEvent() {
                    @Override
                    public void onItemClick(InventoryClickEvent e) {
                        if(e.getCurrentItem() != null && e.getSlot() == position) {
                            listener.onItemClick(e);
                        }
                    }
                });
            }
        }
    }

    public void addOpenEvent(InvOpenEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryOpen(InventoryOpenEvent event) {
                if(event.getInventory().equals(inv)) {
                    e.onInventoryOpen(event);
                }
                if(!event.isCancelled()) {
                    players.add(event.getPlayer());
                }
            }
        }, plugin);
    }

    public void addCloseEvent(InvCloseEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if(event.getView().getTitle().equals(title) && players.contains(event.getPlayer())) {
                    e.onInventoryClose(event);
                }
                players.remove(event.getPlayer());
            }
        }, plugin);
    }

    public void addClickEvent(ItemClickEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onItemClick(InventoryClickEvent event) {
                if(event.getClickedInventory() != null && event.getView().getTitle().equals(title) && players.contains(event.getWhoClicked())) {
                    e.onItemClick(event);
                }
            }
        }, plugin);
    }

    public void addDragEvent(ItemDragEvent e) {
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onItemDrag(InventoryDragEvent event) {
                if(event.getView().getTitle().equals(title) && players.contains(event.getWhoClicked())) {
                    e.onItemDrag(event);
                }
            }
        }, plugin);
    }
}