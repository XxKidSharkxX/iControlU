package me.firebreath15.icontrolu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class InvSync extends BukkitRunnable{
	
	iControlU plugin;
	InvSync(iControlU c){
		plugin=c;
	}
	
	public void run(){
		Player[] ops = Bukkit.getServer().getOnlinePlayers();
		for(int i=0; i<ops.length; i++){
			Player p = ops[i];
			if(plugin.getConfig().contains("controllers."+p.getName())){
				String victim = plugin.getConfig().getString("controllers."+p.getName()+".person");
				Player v = Bukkit.getServer().getPlayer(victim);
				v.getInventory().setContents(p.getInventory().getContents());
				v.getInventory().setArmorContents(p.getInventory().getArmorContents());
			}
		}
		@SuppressWarnings("unused")
		BukkitTask sync = new InvSync(plugin).runTaskLater(plugin, 20);
	}
	
}
