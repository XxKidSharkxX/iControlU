package me.firebreath15.icontrolu;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class onMove implements Listener{
	
	iControlU plugin;
	
	onMove(iControlU c){
		plugin = c;
	}
	
	/*
	 * CODE NOT USED - onMove2 HAS TAKEN THE PLACE OF THIS LISTENER. 
	 * THIS CODE IS OUTDATED AND IS NO LONGER USED IN THE PLUGIN.
	 * IN THE FUTURE THIS CLASS WILL BE DELETED, BUT IT IS BEING KEPT
	 * IN CASE IT CONTAINS INFORMATION NEEDED. FEEL FREE TO STUDY IT
	 * BUT KNOW THAT IT IS "DECOMISSIONED"
	 *  
	 */
	
	
	
	@EventHandler
	public void OnPMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		
		if(plugin.getConfig().contains("controlled."+name)){
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			float yaw = player.getLocation().getYaw();
			float pitch = player.getLocation().getPitch();
			Location loc = new Location(player.getWorld(),x,y,z,yaw,pitch);
			player.teleport(loc);
		}
		
		if(plugin.getConfig().contains("controllers."+name)){
			String person = plugin.getConfig().getString("controllers."+name+".person");
			Player puppet = plugin.getServer().getPlayer(person);
			
			double x1 = event.getFrom().getX();
			double x2 = event.getTo().getX();
			double x3 = x1-x2;
			
			double y1 = event.getFrom().getY();
			double y2 = event.getTo().getY();
			double y3 = y1-y2;
			
			double z1 = event.getFrom().getZ();
			double z2 = event.getTo().getZ();
			double z3 = z1-z2;
			
			float yaw1 = event.getFrom().getYaw();
			float yaw2 = event.getTo().getYaw();
			float yaw3 = yaw1-yaw2;
			
			float pitch1 = event.getFrom().getPitch();
			float pitch2 = event.getTo().getPitch();
			float pitch3 = pitch1-pitch2;
			
			double px = puppet.getLocation().getX();
			double py = puppet.getLocation().getY();
			double pz = puppet.getLocation().getZ();
			float pyaw = puppet.getLocation().getYaw();
			float ppitch = puppet.getLocation().getPitch();
			
			Location loc = new Location(puppet.getWorld(),px-x3,py-y3,pz-z3,pyaw-yaw3,ppitch-pitch3);
			
			puppet.teleport(loc);
			
			//
			// Okay, I must apologize to my Algebra teacher. I said you'd never use algebra in real life.
			// Well I just did delta y, delta x and delta z... wow...
			// Anyways, this moves the target however many steps the controller took in any direction
			//
		}
		
	}
}
