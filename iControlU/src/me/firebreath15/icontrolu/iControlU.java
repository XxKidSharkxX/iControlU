package me.firebreath15.icontrolu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

public class iControlU extends JavaPlugin {

	public HashMap<String, ItemStack[]> inventory;
	public HashMap<String, ItemStack[]> armor;
	public List<String> cd;
	public int maxControlTime;
	public int cooldown;
	public boolean showMessages;

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new iListener(this), this);
		this.saveDefaultConfig();
		this.reloadConfig();
		
		armor = new HashMap<String,ItemStack[]>();
		inventory = new HashMap<String,ItemStack[]>();
		
		maxControlTime = this.getConfig().getInt("maxControlTime");
		cooldown = this.getConfig().getInt("cooldown");
		showMessages = this.getConfig().getBoolean("showMessages");
		cd=new ArrayList<String>();
	}
	
	// Made by xFyreWolfx

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){

		if(cmd.getName().equalsIgnoreCase("icu")){
			if(args.length == 0 || args.length > 3){
				String version = this.getDescription().getVersion().toString(); 
				sender.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help "+version+"]==========");
				sender.sendMessage(ChatColor.BLUE+"/icu control [controller] <player>"+ChatColor.GREEN+" Enter Control Mode.");
				sender.sendMessage(ChatColor.BLUE+"/icu stop [controller]"+ChatColor.GREEN+" Exit Control Mode.");
				sender.sendMessage(ChatColor.BLUE+"/icu reload"+ChatColor.GREEN+" Reload the plugin.");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.DARK_PURPLE+"Created by FireBreath15");
				sender.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help "+version+"]==========");
			}
			
			if(args.length == 3){
				if(args[0].equalsIgnoreCase("control")){
					if(sender.hasPermission("icu.control")){
						Player otherp = Bukkit.getPlayer(args[1]);
						Player victim = this.getServer().getPlayer(args[2]);
						if(otherp != null){
							if(!otherp.hasMetadata("iCU_H")){
								if(victim != null){
									if(!victim.hasMetadata("iCU_P")){
										if(victim != otherp){
											if(!(victim.hasPermission("icu.exempt"))){
												if(!cd.contains(otherp.getName())){
													this.startControlling(victim, otherp);
												}else{
													otherp.sendMessage("§c[§6iControlU§c] §cSystem must cool down!");
												}
											}else{
												sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+victim.getName()+" cannot be controlled!");
											}
										}else{
											otherp.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+otherp.getName()+" can already control themself!");
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
												if(!cd.contains(p.getName())){
													this.startControlling(victim, p);
												}else{
													p.sendMessage("§c[§6iControlU§c] §cSystem must cool down!");
												}
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
									Player victim = Bukkit.getPlayer(otherp.getMetadata("iCU_H").get(0).asString());
									this.stopControlling(victim, otherp);
									
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
								this.stopControlling(victim, p);
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
					if(args[0].equalsIgnoreCase("reload")){
						this.reloadConfig();
						maxControlTime = this.getConfig().getInt("maxControlTime");
						cooldown = this.getConfig().getInt("cooldown");
						showMessages = this.getConfig().getBoolean("showMessages");
						sender.sendMessage("§c[§6iControlU§c] §aConfiguration Reloaded");
					}else{
						sender.sendMessage(ChatColor.RED+"["+ChatColor.GOLD+"iControlU"+ChatColor.RED+"]"+ChatColor.RED+" Wrong command or usage!");
					}
				}
			}
		return true;
	}

		return false;
	}
	
	public void stopControlling(Player v, Player c){
		//Call ONLY when both players are online
		c.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1));
			
		v.removeMetadata("iCU_P", this);
		v.setGameMode(GameMode.SURVIVAL);
				
		c.removeMetadata("iCU_H", this);
		DisguiseAPI.undisguiseToAll(c);
			
		//Give victim their "new" inventory
		v.getInventory().setContents(c.getInventory().getContents());
		v.getInventory().setArmorContents(c.getInventory().getArmorContents());
			
		//Give controller the original inventory back
		c.getInventory().setContents(inventory.get(c.getName()));
		c.getInventory().setArmorContents(armor.get(c.getName()));
		inventory.remove(c.getName());
		armor.remove(c.getName());
			
		v.teleport(c);
		c.sendMessage("§c[§6iControlU§c] §eYou §cdeactivated Control Mode with §e"+v.getName());
			
		if(showMessages)
			v.sendMessage("§c[§6iControlU§c] §e"+c.getName()+" §cdeactivated Control Mode with §eYou");
		
		if(cooldown > 0){
			cd.add(c.getName());
			new Cooldown(this, c).runTaskLater(this, cooldown*20);
		}
	}
	
	public void startControlling(Player v, Player c){
		v.setMetadata("iCU_P", new FixedMetadataValue(this,c.getName()));
		c.setMetadata("iCU_H", new FixedMetadataValue(this,v.getName()));
		
		this.inventory.put(c.getName(), c.getInventory().getContents());
		this.armor.put(c.getName(), c.getInventory().getArmorContents());
		c.getInventory().setContents(v.getInventory().getContents());
		c.getInventory().setArmorContents(v.getInventory().getArmorContents());
		
		c.teleport(v);
		v.setGameMode(GameMode.SPECTATOR);
		
		PlayerDisguise disV = new PlayerDisguise(v.getName());
		DisguiseAPI.disguiseToAll(c, disV);

		//Start a handling task
		new CheckVictim(v, c).runTaskTimer(this, 100, 100);
		
		c.sendMessage("§c[§6iControlU§c] §eYou §aactivated Control Mode with §e"+v.getName());
		
		if(showMessages)
			v.sendMessage("§c[§6iControlU§c] §e"+c.getName()+" §aactivated Control Mode with §eYou");
		
		//Start a limit timer if configured to do so
		if(maxControlTime > 0)
			new ControlTimer(c,v,this).runTaskLater(this, maxControlTime*20);
		
		new Get(c).runTaskLater(this, 260);
	}
}
