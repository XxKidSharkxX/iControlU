package me.firebreath15.icontrolu;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown extends BukkitRunnable{
	iControlU plugin;
	Player c;
	Cooldown(iControlU pl, Player cont){
		plugin=pl;
		c=cont;
	}
	
	public void run(){
		plugin.cd.remove(c.getName());
		c.sendMessage("§c[§6iControlU§c] §aSystem has cooled down.");
		this.cancel();
	}
}
