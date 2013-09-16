package me.firebreath15.icontrolu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@SuppressWarnings("deprecation")
public class onChat implements Listener{
	
	iControlU plugin;
	onChat(iControlU c){
		plugin = c;
	}
		
	@EventHandler
	public void OnChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		String n = player.getName();
		String msg = event.getMessage();
		
		if(plugin.getConfig().contains("controllers."+n)){
			event.setCancelled(true);
			String vname = plugin.getConfig().getString("controllers."+player.getName()+".person");
			Player victim = player.getServer().getPlayer(vname);
			victim.chat("icu"+event.getMessage());
		}
		
		if(plugin.getConfig().contains("controlled."+n)){
			if(!(msg.startsWith("icu"))){
				event.setCancelled(true);
				player.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You cannot talk while being controlled!");
			}else{
				String nmsg = msg.replaceFirst("icu", "");
				event.setMessage(nmsg);
			}
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
