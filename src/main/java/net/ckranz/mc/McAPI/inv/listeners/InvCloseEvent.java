package net.ckranz.mc.McAPI.inv.listeners;

import org.bukkit.event.inventory.InventoryCloseEvent;

public abstract class InvCloseEvent {
    public abstract void onInventoryClose(InventoryCloseEvent e);
}