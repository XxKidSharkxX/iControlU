package me.firebreath15.icontrolu;

import java.util.HashMap;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class iControlU extends JavaPlugin {

	public HashMap<String, ItemStack[]> inventory = new HashMap<String,ItemStack[]>();
	public HashMap<String, ItemStack[]> armor = new HashMap<String,ItemStack[]>();

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new iListener(this), this);
	}
	
	// Made by FireBreath15

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){

		if(cmd.getName().equalsIgnoreCase("icu")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(args.length == 0 || args.length > 2){
					String version = this.getDescription().getVersion().toString(); 
					p.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help v"+version+"]==========");
					p.sendMessage(ChatColor.BLUE+"/icu control <player>"+ChatColor.GREEN+" Enter Control Mode with <player>.");
					p.sendMessage(ChatColor.BLUE+"/icu stop"+ChatColor.GREEN+" Exit Control Mode.");
					p.sendMessage("");
					p.sendMessage(ChatColor.DARK_PURPLE+"Created by FireBreath15");
					p.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help v"+version+"]==========");
				}

				if(args.length == 2){
					if(args[0].equalsIgnoreCase("control")){
						if(p.hasPermission("icu.control")){
							Player victim = this.getServer().getPlayer(args[1]);
							if(!p.hasMetadata("iCU_H")){
								if(victim != null){
									if(!victim.hasMetadata("iCU_P")){
										if(victim != p){
											if(!(victim.hasPermission("icu.exempt"))){												
												victim.setMetadata("iCU_P", new FixedMetadataValue(this,p.getName()));
												p.setMetadata("iCU_H", new FixedMetadataValue(this,victim.getName()));

												this.inventory.put(p.getName(), p.getInventory().getContents());
												this.armor.put(p.getName(), p.getInventory().getArmorContents());
												p.getInventory().setContents(victim.getInventory().getContents());
												p.getInventory().setArmorContents(victim.getInventory().getArmorContents());
												
												victim.teleport(p);
												victim.setGameMode(GameMode.SPECTATOR);
												
												PlayerDisguise disV = new PlayerDisguise(victim.getName());
												DisguiseAPI.disguiseToAll(p, disV);
												
												//Start a handling task
												new CheckVictim(victim, p).runTaskTimer(this, 100, 100);
												
												p.sendMessage("§c[§6iControlU§c] §eYou §aactivated Control Mode  with §e"+victim.getName());
												victim.sendMessage("§c[§6iControlU§c] §e"+p.getName()+" §aactivated Control Mode  with §eYou");
											}else{
												p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You can't control that player!");
											}
										}else{
											p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You can't control yourself!");
										}
									}else{
										p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" That player is already being controlled!");
									}
								}else{
									p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Player not found!");
								}
							}else{
								p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You are controlling someone else!");
							}
						}else{
							p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You don't have permission");
						}
					}else{
						p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Wrong command or usage!");
					}
				}

				if(args.length == 1){
					if(args[0].equalsIgnoreCase("stop")){
						if(p.hasPermission("icu.stop")){
							if(p.hasMetadata("iCU_H")){
								Player victim = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
								p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1));
								
								victim.removeMetadata("iCU_P", this);
								victim.setGameMode(GameMode.SURVIVAL);

								p.removeMetadata("iCU_H", this);
								DisguiseAPI.undisguiseToAll(p);

								p.getInventory().setContents(inventory.get(p.getName()));
								p.getInventory().setArmorContents(armor.get(p.getName()));

								p.sendMessage("§c[§6iControlU§c] §eYou §cdeactivated Control Mode  with §e"+victim.getName());
								victim.sendMessage("§c[§6iControlU§c] §e"+p.getName()+" §cdeactivated Control Mode  with §eYou");
							}else{
								p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You are not in Control Mode!");
							}
						}else{
							p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You don't have permission!");
						}
					}else{
						p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Wrong command or usage!");
					}
				}
			}else{
				sender.sendMessage(ChatColor.GOLD+"[iControlU]"+ChatColor.RED+" iControlU commands can only be sent from a player!");
			}
			return true;
		}

		return false;
	}
}
