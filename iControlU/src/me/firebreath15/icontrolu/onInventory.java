package me.firebreath15.icontrolu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class onInventory implements Listener{
	iControlU plugin;
	onInventory(iControlU c){
		plugin=c;
	}
	
	/*
	 * UNIMPLEMENTED CODE - THIS CODE IS NOT USED AS OF YET. IT DOESNT WORK COMPLETELY BUT I KEPT IT HERE SO I CAN IMPROVE IT IN THE
	 * FUTURE!
	 */
	
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e){
		Player p = (Player)e.getPlayer();
		String name = p.getName();
		
		if(plugin.getConfig().contains("controllers."+name)){
			String pn = plugin.getConfig().getString("controllers."+name+".person");
			Player target = plugin.getServer().getPlayer(pn);
			e.setCancelled(true); //cancel the controller's inventory opening
			p.openInventory(target.getInventory()); //and open his target's.
		}
	}
	
	@EventHandler
	public void onTargetInteract(PlayerInteractEvent e){
		if(plugin.getConfig().contains("controlled."+e.getPlayer().getName())){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You can't do that while being controlled!");
		}
	}
}
