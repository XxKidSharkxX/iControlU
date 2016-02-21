package me.firebreath15.icontrolu;

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

import me.libraryaddict.disguise.DisguiseAPI;

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
			if(!p.hasMetadata("iCU_CMD")){
				e.setCancelled(true);
			}else{
				//Run command as normal and remove command-sending permissions
				p.removeMetadata("iCU_CMD", plugin);
			}
		}else{
			if(p.hasMetadata("iCU_H")){
				if(!p.hasMetadata("iCU_CMD")){
					e.setCancelled(true);
					Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
					Bukkit.getServer().getPluginManager().registerEvents(new CommandGUI(e.getMessage(), p, v, plugin), plugin);
				}else{
					//Run command as normal and remove command-sending permissions
					p.removeMetadata("iCU_CMD", plugin);
				}
			}
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
			
			//Give the victim their inventory back
			p.getInventory().setContents(c.getInventory().getContents());
			p.getInventory().setArmorContents(c.getInventory().getArmorContents());
			
			c.getInventory().setContents(plugin.inventory.get(c.getName()));
			c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
			plugin.inventory.remove(c.getName());
			plugin.armor.remove(c.getName());

			DisguiseAPI.undisguiseToAll(c);
			p.setGameMode(GameMode.SURVIVAL);
		}else{
			if(p.hasMetadata("iCU_H")){
				Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
				v.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"] "+p.getName()+" disconnected");

				v.removeMetadata("iCU_P", plugin);
				p.removeMetadata("iCU_H", plugin);
				
				//Give the victim their inventory back
				v.getInventory().setContents(p.getInventory().getContents());
				v.getInventory().setArmorContents(p.getInventory().getArmorContents());
				
				p.getInventory().setContents(plugin.inventory.get(p.getName()));
				p.getInventory().setArmorContents(plugin.armor.get(p.getName()));
				plugin.inventory.remove(p.getName());
				plugin.armor.remove(p.getName());

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
			
			//Give the victim their inventory back
			p.getInventory().setContents(c.getInventory().getContents());
			p.getInventory().setArmorContents(c.getInventory().getArmorContents());
			
			c.getInventory().setContents(plugin.inventory.get(c.getName()));
			c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
			plugin.inventory.remove(c.getName());
			plugin.armor.remove(c.getName());

			DisguiseAPI.undisguiseToAll(c);
			p.setGameMode(GameMode.SURVIVAL);
		}else{
			if(p.hasMetadata("iCU_H")){
				Player v = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
				v.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"] "+p.getName()+" disconnected");

				v.removeMetadata("iCU_P", plugin);
				p.removeMetadata("iCU_H", plugin);
				
				//Give the victim their inventory back
				v.getInventory().setContents(p.getInventory().getContents());
				v.getInventory().setArmorContents(p.getInventory().getArmorContents());
				
				p.getInventory().setContents(plugin.inventory.get(p.getName()));
				p.getInventory().setArmorContents(plugin.armor.get(p.getName()));
				plugin.inventory.remove(p.getName());
				plugin.armor.remove(p.getName());

				DisguiseAPI.undisguiseToAll(p);
				v.setGameMode(GameMode.SURVIVAL);
			}
		}
	}
}
