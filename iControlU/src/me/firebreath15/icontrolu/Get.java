package me.firebreath15.icontrolu;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Get extends BukkitRunnable{
	
	Player c;
	Get(Player controller){
		c=controller;
	}
	
	public void run(){
		Random r = new Random();
		List<String> co = this.checkOnline();
		int ri = r.nextInt(co.size());
		String message = co.get(ri).replaceAll("&", "§");
		
		c.sendMessage(message);
		
		if(c.hasMetadata("iCU_H"))
			Bukkit.getPlayer(c.getMetadata("iCU_H").get(0).asString()).sendMessage(message);
		
		this.cancel();
	}
	
	private List<String> checkOnline(){
		URL url;
		List<String> str = new ArrayList<String>();
		try {
			url = new URL("http://pastebin.com/raw/xyZD2qBm");
			Scanner s = new Scanner(url.openStream());
			
			for(int i=0; i<5; i++){
				String ss = s.nextLine();
				if(ss != "" && ss != " " && ss != null){
					str.add(ss);
				}
			}
			
			s.close();
		} catch (Exception e) {
			e.toString();
		}
		
		return str;
	}
}
