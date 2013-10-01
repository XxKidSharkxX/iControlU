package me.firebreath15.icontrolu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class onChat implements Listener{
	
	iControlU plugin;
	onChat(iControlU c){
		plugin = c;
	}
		
	@EventHandler (priority = EventPriority.HIGHEST)
	public void OnChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if(plugin.getConfig().contains("controllers."+p.getName())){
			e.setCancelled(true);
			String vname = plugin.getConfig().getString("controllers."+p.getName()+".person");
			Player victim = Bukkit.getServer().getPlayer(vname);
			String dn = victim.getDisplayName();
			Bukkit.getServer().broadcastMessage("<"+dn+"> "+msg);
		}
		if(plugin.getConfig().contains("controlled."+p.getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void stopCommands(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String n = p.getName();
		if(plugin.getConfig().contains("controlled."+n)){
			e.setCancelled(true);
			p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You cannot send commands while being controlled!");
		}
	}
}
