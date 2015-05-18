package me.firebreath15.icontrolu;

import me.libraryaddict.disguise.DisguiseAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class iListener implements Listener{

	iControlU plugin;
	iListener(iControlU c){
		plugin=c;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.hasMetadata("iCU_P")){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		if(p.hasMetadata("iCU_P")){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(p.hasMetadata("iCU_P")){
			if(p.hasMetadata("iCU_Chat")){
				p.removeMetadata("iCU_Chat", plugin);
			}else{
				e.setCancelled(true);
			}
		}else{
			if(p.hasMetadata("iCU_H")){
				e.setCancelled(true);
				Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
				v.setMetadata("iCU_Chat", new FixedMetadataValue(plugin,true));
				v.chat(e.getMessage());
			}
		}
	}

	@EventHandler
	public void onPlayerRunCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		if(p.hasMetadata("iCU_P")){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerGetKicked(PlayerKickEvent e){
		Player p = e.getPlayer();
		if(p.hasMetadata("iCU_P")){
			Player c = Bukkit.getPlayer(p.getMetadata("iCU_P").get(0).asString());
			c.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"] "+p.getName()+" disconnected");

			p.removeMetadata("iCU_P", plugin);
			c.removeMetadata("iCU_H", plugin);

			c.getInventory().setContents(plugin.inventory.get(c.getName()));
			c.getInventory().setArmorContents(plugin.armor.get(c.getName()));

			DisguiseAPI.undisguiseToAll(c);
			p.setGameMode(GameMode.SURVIVAL);
		}else{
			if(p.hasMetadata("iCU_H")){
				Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
				v.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"] "+p.getName()+" disconnected");

				v.removeMetadata("iCU_P", plugin);
				p.removeMetadata("iCU_H", plugin);

				p.getInventory().setContents(plugin.inventory.get(p.getName()));
				p.getInventory().setArmorContents(plugin.armor.get(p.getName()));

				DisguiseAPI.undisguiseToAll(p);
				v.setGameMode(GameMode.SURVIVAL);
			}
		}
	}


	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(p.hasMetadata("iCU_P")){
			Player c = Bukkit.getPlayer(p.getMetadata("iCU_P").get(0).asString());
			c.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"] "+p.getName()+" disconnected");

			p.removeMetadata("iCU_P", plugin);
			c.removeMetadata("iCU_H", plugin);

			c.getInventory().setContents(plugin.inventory.get(c.getName()));
			c.getInventory().setArmorContents(plugin.armor.get(c.getName()));

			DisguiseAPI.undisguiseToAll(c);
			p.setGameMode(GameMode.SURVIVAL);
		}else{
			if(p.hasMetadata("iCU_H")){
				Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
				v.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"] "+p.getName()+" disconnected");

				v.removeMetadata("iCU_P", plugin);
				p.removeMetadata("iCU_H", plugin);

				p.getInventory().setContents(plugin.inventory.get(p.getName()));
				p.getInventory().setArmorContents(plugin.armor.get(p.getName()));

				DisguiseAPI.undisguiseToAll(p);
				v.setGameMode(GameMode.SURVIVAL);
			}
		}
	}
}
