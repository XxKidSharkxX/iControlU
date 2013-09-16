package me.firebreath15.icontrolu;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onHurt implements Listener{

	iControlU plugin;
	onHurt(iControlU c){
		plugin=c;
	}
	
	public void damaged(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			String n = p.getName();
			if(plugin.getConfig().contains("controlled."+n)){
				e.setCancelled(true);
			}
			if(plugin.getConfig().contains("controllers."+n)){
				e.setCancelled(true);
			}
		}
	}
	
}
