package me.firebreath15.icontrolu;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InvSync extends BukkitRunnable{
	
	Player p;
	Player puppet;
	iControlU plugin;
	InvSync(iControlU c, Player con, Player vic){
		plugin=c;
		p=con;
		puppet=vic;
	}
	
	public void run(){
		if(plugin.data.containsKey(p) && plugin.data.containsKey(puppet)){
			puppet.getInventory().setContents(p.getInventory().getContents());
			puppet.getInventory().setArmorContents(p.getInventory().getArmorContents());
		}else{
			this.cancel();
		}
	}
	
}
