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
	public void OnPMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		
		if(plugin.getConfig().contains("controlled."+name)){
			int x = event.getFrom().getBlockX();
			int y = event.getFrom().getBlockX();
			int z = event.getFrom().getBlockX();
			String wn = event.getFrom().getWorld().getName();
			
			int nx = event.getTo().getBlockX();
			int ny = event.getTo().getBlockX();
			int nz = event.getTo().getBlockX();
			String nwn = event.getTo().getWorld().getName();
			
			Delta d = new Delta();
			if((d.ifChangeWasOne(x, nx) || d.ifChangeWasOne(y, ny) || d.ifChangeWasOne(z, nz)) && (wn == nwn)){
				//if the player moved a block and theyre in the same world as before
				event.setCancelled(true);
			}
		}
		
		if(plugin.getConfig().contains("controllers."+name)){
				String person = plugin.getConfig().getString("controllers."+name+".person");
				Player puppet = plugin.getServer().getPlayer(person);
				
				puppet.teleport(player.getLocation());
		}
		
	}
}
