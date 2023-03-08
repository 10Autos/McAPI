package net.ckranz.mc.McAPI.inv.listeners;

import org.bukkit.event.inventory.InventoryDragEvent;

public abstract class ItemDragEvent {
    public abstract void onItemDrag(InventoryDragEvent e);
}