package me.firebreath15.icontrolu;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutPosition;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MoveTask extends BukkitRunnable{
	
	private EntityPlayer c;
	private EntityPlayer v;
	
	MoveTask(Player co, Player vi){
		c=((CraftPlayer)co).getHandle();
		v=((CraftPlayer)vi).getHandle();
	}
	//
	public void run(){
		if(!c.getBukkitEntity().hasMetadata("iCU_H")){
			this.cancel();
		}
		
		Location from = new Location(c.getBukkitEntity().getWorld(),c.lastX,c.lastY,c.lastZ,c.lastYaw,c.lastPitch);
		Location to = new Location(c.getBukkitEntity().getWorld(),c.locX,c.locY,c.locZ,c.yaw,c.pitch);
		v.setLocation(to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());
		Packet packet;
		
	    if(from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()){
	    	packet = new PacketPlayOutPosition(to.getX(), to.getY() + 1.62, to.getZ(), to.getYaw(), to.getPitch(), true);
	    }else{
	    	if(c.isSneaking()){
	    		packet = new PacketPlayOutPosition(to.getX(), to.getY() + v.getBukkitEntity().getEyeHeight(true) + 1.62, to.getZ(), to.getYaw(), to.getPitch(), true);
	        }else{
	        	packet = new PacketPlayOutPosition(to.getX(), to.getY() + v.getBukkitEntity().getEyeHeight(false) + 1.62, to.getZ(), to.getYaw(), to.getPitch(), true);
	        }
	    }
	    
	    v.playerConnection.sendPacket(packet);
	    if(v.getBukkitEntity().hasMetadata("iCU_P")){
		    v.getBukkitEntity().getInventory().setContents(c.getBukkitEntity().getInventory().getContents());
			v.getBukkitEntity().getInventory().setArmorContents(c.getBukkitEntity().getInventory().getArmorContents());
	    }
	}
}
