package net.ckranz.mc.mcapi.inv;

import net.ckranz.mc.mcapi.inv.listeners.ItemClickEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvItem extends ItemStack {
    private final ArrayList<ItemClickEvent> clickEvents = new ArrayList<>();

    public InvItem(Material material) {
        super(material);
    }

    public InvItem(Material material, String itemName) {
        this(material, itemName, null);
    }

    public InvItem(Material material, String itemName, List<String> lore) {
        super(material);
        ItemMeta itemMeta = super.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        super.setItemMeta(itemMeta);
    }

    ArrayList<ItemClickEvent> getClickEvents() {
        return clickEvents;
    }

    public void addClickEvent(ItemClickEvent e) {
        clickEvents.add(e);
    }


    public static List<String> createLore(String lore) {
        return Arrays.asList(lore.split("\n"));
    }
}