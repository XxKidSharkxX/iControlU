package me.firebreath15.icontrolu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLogout implements Listener{
	
	iControlU plugin;
	onLogout(iControlU c){
		plugin=c;
	}
	
	@EventHandler
	public void OnPLogout(PlayerQuitEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		
		if(plugin.getConfig().contains("controlled."+name)){
			String controller = plugin.getConfig().getString("controlled."+name);
			Player controlfreak = plugin.getServer().getPlayer(controller);
			plugin.getConfig().set("controllers."+controller, null);
			plugin.getConfig().set("controlled."+name,null);
			plugin.saveConfig();
			controlfreak.sendMessage(ChatColor.GOLD+"[iControlU]"+ChatColor.RED+name+ChatColor.BLUE+" left the game!");
			
			for(int i=0; i<plugin.getServer().getOnlinePlayers().length; i++){
				Player[] pl = plugin.getServer().getOnlinePlayers();
				pl[i].showPlayer(player);
				pl[i].showPlayer(controlfreak);
			}
			
			//the victim logged out, so the controller should get his inventory back.
			plugin.api.restorePlayerInventory(controlfreak.getName());
			plugin.api.restorePlayerArmor(controlfreak.getName());
			
		}else{
			if(plugin.getConfig().contains("controllers."+name)){
				String vname = plugin.getConfig().getString("controllers."+name+".person");
				Player victim = player.getServer().getPlayer(vname);
				plugin.getConfig().set("controllers."+name, null);
				plugin.getConfig().set("controlled."+victim.getName(), null);
				victim.sendMessage(ChatColor.GOLD+"[iControlU]"+ChatColor.RED+"The player who was controlling you left. You were set free!");
				plugin.saveConfig();
				
				for(int i=0; i<plugin.getServer().getOnlinePlayers().length; i++){
					Player[] pl = plugin.getServer().getOnlinePlayers();
					pl[i].showPlayer(player);
					pl[i].showPlayer(victim);
				}
				
				//the controller logged out, so he should get his inventory back
				plugin.api.restorePlayerInventory(player.getName());
				plugin.api.restorePlayerArmor(player.getName());
			}
		}
	}
}
