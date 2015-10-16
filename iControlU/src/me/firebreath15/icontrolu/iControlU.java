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
			if(args.length == 0 || args.length > 3){
				String version = this.getDescription().getVersion().toString(); 
				sender.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help v"+version+"]==========");
				sender.sendMessage(ChatColor.BLUE+"/icu control [controller] <player>"+ChatColor.GREEN+" Enter Control Mode.");
				sender.sendMessage(ChatColor.BLUE+"/icu stop [controller]"+ChatColor.GREEN+" Exit Control Mode.");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.DARK_PURPLE+"Created by FireBreath15");
				sender.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help v"+version+"]==========");
			}
			
			if(args.length == 3){
				if(args[0].equalsIgnoreCase("control")){
					if(sender.hasPermission("icu.control")){
						Player p = Bukkit.getPlayer(args[1]);
						Player victim = this.getServer().getPlayer(args[2]);
						if(p != null){
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
												
												p.teleport(victim);
												victim.setGameMode(GameMode.SPECTATOR);
													
												PlayerDisguise disV = new PlayerDisguise(victim.getName());
												DisguiseAPI.disguiseToAll(p, disV);
										
												//Start a handling task
												new CheckVictim(victim, p).runTaskTimer(this, 100, 100);
												
												this.setVictimCamera(victim, p);
												p.sendMessage("§c[§6iControlU§c] §e"+sender.getName()+" §aactivated your Control Mode with §e"+victim.getName());
												victim.sendMessage("§c[§6iControlU§c] §e"+p.getName()+" §aactivated Control Mode with §eYou");
											}else{
												sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+victim.getName()+" cannot be controlled!");
											}
										}else{
											p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+p.getName()+" can already control themself!");
										}
									}else{
										sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+victim.getName()+" is already being controlled!");
									}
								}else{
									sender.sendMessage("§c[§6iControlU§c] Player "+args[2]+" not found");
								}
							}else{
								sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" That player is controlling someone else!");
							}
						}else{
							sender.sendMessage("§c[§6iControlU§c] Player "+args[1]+" not found");
						}
					}else{
						sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You don't have permission");
					}
				}else{
					sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Wrong command or usage!");
				}
			}
			
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("control")){
					if(sender.hasPermission("icu.control")){
						if(sender instanceof Player){
							Player p = (Player)sender;
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
												
												p.teleport(victim);
												victim.setGameMode(GameMode.SPECTATOR);
												
												PlayerDisguise disV = new PlayerDisguise(victim.getName());
												DisguiseAPI.disguiseToAll(p, disV);
										
												//Start a handling task
												new CheckVictim(victim, p).runTaskTimer(this, 100, 100);
												
												this.setVictimCamera(victim, p);
												p.sendMessage("§c[§6iControlU§c] §eYou §aactivated Control Mode with §e"+victim.getName());
												victim.sendMessage("§c[§6iControlU§c] §e"+p.getName()+" §aactivated Control Mode with §eYou");
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
							sender.sendMessage("§c[§6iControlU§c] You must be a player to run this command!");
						}
					}else{
						sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You don't have permission");
					}
				}else{
					if(args[0].equalsIgnoreCase("stop")){
						if(sender.hasPermission("icu.stop")){
							Player otherp = Bukkit.getPlayer(args[1]);
							if(otherp != null){
								if(otherp.hasMetadata("iCU_H")){
									//stop their controlling
									Player victim = Bukkit.getPlayer(otherp.getMetadata("iCU_H").get(0).asString());
									otherp.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1));
									
									victim.removeMetadata("iCU_P", this);
									victim.setGameMode(GameMode.SURVIVAL);
										
									otherp.removeMetadata("iCU_H", this);
									DisguiseAPI.undisguiseToAll(otherp);
									
									//Give the victim their inventory back
									victim.getInventory().setContents(otherp.getInventory().getContents());
									victim.getInventory().setArmorContents(otherp.getInventory().getArmorContents());
									
									otherp.getInventory().setContents(inventory.get(otherp.getName()));
									otherp.getInventory().setArmorContents(armor.get(otherp.getName()));
									inventory.remove(otherp.getName());
									armor.remove(otherp.getName());
									
									this.unsetVictimCamera(victim);
									otherp.sendMessage("§c[§6iControlU§c] §eYou §cdeactivated Control Mode with §e"+victim.getName());
									victim.sendMessage("§c[§6iControlU§c] §e"+otherp.getName()+" §cdeactivated Control Mode with §eYou");
									sender.sendMessage("§c[§6iControlU§c] §e"+otherp.getName()+" §6is no longer controlling §e"+victim.getName());
								}else{
									sender.sendMessage("§c[§6iControlU§c] That player is not controlling anyone!");
								}
							}else{
								sender.sendMessage("§c[§6iControlU§c] Player not found!");
							}
						}else{
							sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You don't have permission!");
						}
					}else{
						sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Wrong command or usage!");
					}
				}
			}

			if(args.length == 1){
				if(args[0].equalsIgnoreCase("stop")){
					if(sender instanceof Player){
						Player p = (Player)sender;
						if(p.hasPermission("icu.stop")){
							if(p.hasMetadata("iCU_H")){
								Player victim = Bukkit.getPlayer(p.getMetadata("iCU_H").get(0).asString());
								p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1));
								
								victim.removeMetadata("iCU_P", this);
								victim.setGameMode(GameMode.SURVIVAL);
									
								p.removeMetadata("iCU_H", this);
								DisguiseAPI.undisguiseToAll(p);
								
								//Give the victim their inventory back
								victim.getInventory().setContents(p.getInventory().getContents());
								victim.getInventory().setArmorContents(p.getInventory().getArmorContents());
								
								p.getInventory().setContents(inventory.get(p.getName()));
								p.getInventory().setArmorContents(armor.get(p.getName()));
								inventory.remove(p.getName());
								armor.remove(p.getName());
								
								this.unsetVictimCamera(victim);
								p.sendMessage("§c[§6iControlU§c] §eYou §cdeactivated Control Mode with §e"+victim.getName());
								victim.sendMessage("§c[§6iControlU§c] §e"+p.getName()+" §cdeactivated Control Mode with §eYou");
							}else{
								p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You are not in Control Mode!");
							}
						}else{
							p.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" You don't have permission!");
						}
					}else{
						sender.sendMessage("§c[§6iControlU§c] You must be a player to run this command!");
					}
				}else{
					sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Wrong command or usage!");
				}
			}
		return true;
	}

		return false;
	}
	
	public void setVictimCamera(Player v, Player c){
		c.setPassenger(v);
	}
	
	public void unsetVictimCamera(Player v){
		v.leaveVehicle();
	}
}
