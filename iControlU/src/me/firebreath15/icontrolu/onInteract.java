package me.firebreath15.icontrolu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class onInteract implements Listener{
	iControlU plugin;
	onInteract(iControlU c){
		plugin=c;
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e){
		if(plugin.getConfig().contains("controlled."+e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerBuildEvent(BlockPlaceEvent e){
		if(plugin.getConfig().contains("controlled."+e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerBreakEvent(BlockBreakEvent e){
		if(plugin.getConfig().contains("controlled."+e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
		if(plugin.getConfig().contains("controlled."+e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
}
