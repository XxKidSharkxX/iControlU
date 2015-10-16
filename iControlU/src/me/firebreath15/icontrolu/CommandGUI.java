package me.firebreath15.icontrolu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class CommandGUI implements Listener{
	String cmd;
	Player c;
	Player v;
	public Inventory gui;
	iControlU plugin;
	
	CommandGUI(String command, Player cont, Player vict, iControlU cl){
		cmd=command;
		c=cont;
		v=vict;
		plugin=cl;
		
		gui = Bukkit.createInventory(null, 9, "§8§ki §8Run Command §ki");
		
		ItemStack rat = new ItemStack(Material.BARRIER);
		ItemMeta im = rat.getItemMeta();
		im.setDisplayName("§c§lRun as "+v.getName());
		List<String> lore = new ArrayList<String>();
		lore.add("§7Runs the command as your target");
		lore.add("§7Command: §6"+cmd);
		im.setLore(lore);
		rat.setItemMeta(im);
		
		ItemStack raa = new ItemStack(Material.ARROW);
		ItemMeta im2 = raa.getItemMeta();
		im2.setDisplayName("§c§lRun as "+c.getName());
		List<String> lore2 = new ArrayList<String>();
		lore2.add("§7Run the command as yourself");
		lore2.add("§7Command: §6"+cmd);
		im2.setLore(lore2);
		raa.setItemMeta(im2);
		
		gui.setItem(2, rat);
		gui.setItem(6, raa);
		
		c.openInventory(gui);
	}
	
	@EventHandler
	public void clickGUI(InventoryClickEvent e){
		if(e.getInventory().equals(gui) || e.getInventory().getName().equalsIgnoreCase(gui.getName())){
			Player p = (Player)e.getWhoClicked();
			p.closeInventory();
			
			if(e.getCurrentItem() != null){
				if(e.getCurrentItem().getType()==Material.BARRIER){
					v.setMetadata("iCU_CMD", new FixedMetadataValue(plugin, true));
					v.chat(cmd);
				}else{
					if(e.getCurrentItem().getType()==Material.ARROW){
						c.setMetadata("iCU_CMD", new FixedMetadataValue(plugin, true));
						c.chat(cmd);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory().equals(gui) || e.getInventory().getName().equalsIgnoreCase(gui.getName())){
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryCloseEvent.getHandlerList().unregister(this);
		}
	}
}
