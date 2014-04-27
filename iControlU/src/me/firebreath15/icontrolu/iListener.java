package me.firebreath15.icontrolu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class iListener implements Listener{
	iControlU plugin;
	iListener(iControlU c){
		plugin = c;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void OnChat(AsyncPlayerChatEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("p")){
				e.setCancelled(true);
			}
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("c")){
				e.setCancelled(true);
				Player puppet = plugin.relations.get(e.getPlayer());
				String dn = puppet.getDisplayName();
				Bukkit.getServer().broadcastMessage("<"+dn+"> "+e.getMessage());
			}
		}
	}
	
	@EventHandler
	public void stopCommands(PlayerCommandPreprocessEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("p")){
				e.setCancelled(true);
			}
		}
	}
	
	public void damaged(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			if(plugin.data.containsKey(p)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("p")){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void playerBuildEvent(BlockPlaceEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("p")){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void playerBreakEvent(BlockBreakEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("p")){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("p")){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent e){
		if(plugin.data.containsKey(e.getPlayer())){
			Player other = plugin.relations.get(e.getPlayer());
			plugin.data.remove(e.getPlayer());
			other.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.YELLOW+e.getPlayer().getName()+" left the game");
			plugin.data.remove(other);
			plugin.relations.remove(other);
			plugin.relations.remove(e.getPlayer());
			
			Player[] ops = Bukkit.getOnlinePlayers();
			for(int i=0; i<ops.length; i++){
				ops[i].showPlayer(e.getPlayer());
			}
			
			if(plugin.data.get(e.getPlayer()).equalsIgnoreCase("c")){
				plugin.api.restorePlayerArmor(e.getPlayer().getName());
				plugin.api.restorePlayerInventory(e.getPlayer().getName());
			}
			
			if(plugin.data.get(other).equalsIgnoreCase("c")){
				plugin.api.restorePlayerArmor(other.getName());
				plugin.api.restorePlayerInventory(other.getName());
			}
		}
	}
}
