package me.firebreath15.icontrolu;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onMove implements Listener{
	
	iControlU plugin;
	
	onMove(iControlU c){
		plugin = c;
	}
	
	@EventHandler
	public void OnPMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		
		if(plugin.getConfig().contains("controlled."+name)){
			Location old = event.getFrom();
			Location neww = event.getTo();
			
			if(old.getWorld().getName().equalsIgnoreCase(neww.getWorld().getName())){
				if(old.distance(neww)>=1){
					event.setCancelled(true);
				}
			}
		}
		
		if(plugin.getConfig().contains("controllers."+name)){
				String person = plugin.getConfig().getString("controllers."+name+".person");
				Player puppet = plugin.getServer().getPlayer(person);
				
				puppet.teleport(player.getLocation());
		}
		
	}
}
