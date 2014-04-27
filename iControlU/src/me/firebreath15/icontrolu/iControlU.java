package me.firebreath15.icontrolu;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class iControlU extends JavaPlugin{
	
	INVAPI api;
	HashMap<Player,String> data; //   player [c|p]
	HashMap<Player,Player> relations; //   player | player
	
	public void onEnable(){
		data = new HashMap<Player,String>();
		relations = new HashMap<Player,Player>();
		
		this.getConfig().set("controllers", null);
		this.getConfig().set("controlled", null);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new onMove(this), this);
		this.getServer().getPluginManager().registerEvents(new iListener(this), this);
		api=new INVAPI();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
		
		if(cmd.getName().equalsIgnoreCase("icu")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(args.length == 0 || args.length > 2){
					p.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help v1.7.0]==========");
					p.sendMessage(ChatColor.BLUE+"/icu control <player>"+ChatColor.GREEN+" Enter Control Mode with <player>.");
					p.sendMessage(ChatColor.BLUE+"/icu stop"+ChatColor.GREEN+" Exit Control Mode.");
					p.sendMessage("");
					p.sendMessage(ChatColor.DARK_PURPLE+"Created by FireBreath15");
					p.sendMessage(ChatColor.YELLOW+"==========[ iControlU Help v1.7.0]==========");
				}
				
				if(args.length==2){
					if(args[0].equalsIgnoreCase("control")){
						if(p.hasPermission("icu.control") || p.hasPermission("icontrolu.control")){
							Player puppet = Bukkit.getPlayer(args[1]);
							if(puppet != null){
								if(!(puppet.hasPermission("icu.exempt") || puppet.hasPermission("icontrolu.exempt"))){
									data.put(p, "c");
									data.put(puppet, "p");
									relations.put(p, puppet);
									relations.put(puppet, p);
									p.hidePlayer(puppet);
									
									p.setGameMode(puppet.getGameMode());
									
									Player[] ops = Bukkit.getOnlinePlayers();
									for(int i=0; i<ops.length; i++){
										ops[i].hidePlayer(p);
									}
									
									@SuppressWarnings("unused")
									BukkitTask sync = new InvSync(this,p,puppet).runTaskTimer(this, 20, 5);
									
									p.teleport(puppet);
									p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.GREEN+"Began controlling "+ChatColor.RED+puppet.getName()+ChatColor.GREEN+".");
									p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.GRAY+"Your target will now mimick every action you perform, except commands.");
									p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.GRAY+"To stop, use command "+ChatColor.GOLD+"/icu stop");
									
									api.storePlayerInventory(p.getName());
									api.storePlayerArmor(p.getName());
									p.getInventory().setContents(puppet.getInventory().getContents());
									p.getInventory().setArmorContents(puppet.getInventory().getArmorContents());
								}else{
									p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You can't control that player!");
								}
							}else{
								p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"Player not found!");
							}
						}else{
							p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You don't have permission!");
						}
					}else{
						p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"Unknown command / wrong syntax!");
					}
				}
				
				if(args.length==1){
					if(args[0].equalsIgnoreCase("stop")){
						if(p.hasPermission("icu.stop") || p.hasPermission("icontrolu.stop")){
							if(this.data.containsKey(p)){
								if(this.data.get(p).equalsIgnoreCase("c")){
									Player puppet = this.relations.get(p);
									this.data.remove(p);
									this.data.remove(puppet);
									this.relations.remove(p);
									this.relations.remove(puppet);
									api.restorePlayerArmor(p.getName());
									api.restorePlayerInventory(p.getName());
									p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.GREEN+"You are no longer controlling "+ChatColor.RED+puppet.getName()+ChatColor.GREEN+".");
									p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,2));
									
									Player[] ops = Bukkit.getOnlinePlayers();
									for(int i=0; i<ops.length; i++){
										ops[i].showPlayer(p);
									}
									p.showPlayer(puppet);
									
								}else{
									p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"Only your master can stop it!");
								}
							}else{
								p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You aren't controlling anyone!");
							}
						}else{
							p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"You don't have permission!");
						}
					}else{
						p.sendMessage(ChatColor.GOLD+"[iControlU] "+ChatColor.RED+"Unknown command / wrong syntax!");
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
