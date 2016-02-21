package me.firebreath15.icontrolu;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ControlTimer extends BukkitRunnable{
	
	Player c;
	Player v;
	iControlU plugin;
	ControlTimer(Player controller, Player victim, iControlU pl){
		c=controller;
		v=victim;
		plugin=pl;
	}
	
	public void run(){
		if(c != null && v != null){
			plugin.stopControlling(v, c);
		}
		
		this.cancel();
	}
}
