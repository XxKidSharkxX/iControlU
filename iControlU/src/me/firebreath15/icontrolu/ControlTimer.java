package me.firebreath15.icontrolu;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.libraryaddict.disguise.DisguiseAPI;

public class ControlTimer extends BukkitRunnable{
	
	Player c;
	Player v;
	iControlU plugin;
	ControlTimer(Player controller, Player victim, iControlU pl){
		c=controller;
		v=victim;
		plugin=pl;
	}
	
	//This class runs the same code as "/icu stop" but is run forcefully if config.yml limits control time
	
	public void run(){
		if(c != null && v != null){
			c.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1));
			
			v.removeMetadata("iCU_P", plugin);
			v.setGameMode(GameMode.SURVIVAL);
				
			c.removeMetadata("iCU_H", plugin);
			DisguiseAPI.undisguiseToAll(c);
			
			v.getInventory().setContents(c.getInventory().getContents());
			v.getInventory().setArmorContents(c.getInventory().getArmorContents());
			
			c.getInventory().setContents(plugin.inventory.get(c.getName()));
			c.getInventory().setArmorContents(plugin.armor.get(c.getName()));
			plugin.inventory.remove(c.getName());
			plugin.armor.remove(c.getName());
			
			plugin.unsetVictimCamera(v);
			c.sendMessage("§c[§6iControlU§c] §eYou §cdeactivated Control Mode with §e"+v.getName());
			
			if(plugin.showMessages)
				v.sendMessage("§c[§6iControlU§c] §e"+c.getName()+" §cdeactivated Control Mode with §eYou");
			
			if(plugin.cooldown > 0){
				plugin.cd.add(c.getName());
				new Cooldown(plugin, c).runTaskLater(plugin, plugin.cooldown*20);
			}
		}
		
		this.cancel();
	}
}
