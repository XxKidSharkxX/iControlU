package me.firebreath15.icontrolu;

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
	public void OnPMove(PlayerMoveEvent e){
		if(plugin.relations.containsKey(e.getPlayer())){
			String role = plugin.data.get(e.getPlayer());
			Player other = plugin.relations.get(e.getPlayer());
			
			if(role.equalsIgnoreCase("p")){
				if(e.getTo().getWorld().getName().equalsIgnoreCase(e.getFrom().getWorld().getName())){
					if(e.getFrom().distance(other.getLocation())>=1){
						e.setCancelled(true);
					}
				}
				
			}
			
			if(role.equalsIgnoreCase("c")){				
				if(e.getTo().getWorld().getName().equalsIgnoreCase(e.getFrom().getWorld().getName())){
					other.teleport(e.getPlayer());
				}
			}
		}
	}
}
