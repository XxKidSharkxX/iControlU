package me.firebreath15.icontrolu;

import java.util.HashSet;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R2.PacketPlayOutPosition.EnumPlayerTeleportFlags;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
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
		PacketPlayOutPosition packet;

		if(from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()){
			packet = new PacketPlayOutPosition((double) to.getX(),(double) to.getY(),(double) to.getZ(),(float) to.getYaw(),(float)  to.getPitch(), new HashSet<EnumPlayerTeleportFlags>());
		}else{
			if(c.isSneaking()){
				packet = new PacketPlayOutPosition((double) to.getX(),(double) to.getY() + v.getBukkitEntity().getEyeHeight(true),(double) to.getZ(),(float) to.getYaw(),(float) to.getPitch(), new HashSet<EnumPlayerTeleportFlags>());
			}else{
				packet = new PacketPlayOutPosition((double) to.getX(),(double) to.getY() + v.getBukkitEntity().getEyeHeight(false),(double) to.getZ(),(float) to.getYaw(),(float) to.getPitch(), new HashSet<EnumPlayerTeleportFlags>());
			}
		}

		v.playerConnection.sendPacket(packet);
		if(v.getBukkitEntity().hasMetadata("iCU_P")){
			v.getBukkitEntity().getInventory().setContents(c.getBukkitEntity().getInventory().getContents());
			v.getBukkitEntity().getInventory().setArmorContents(c.getBukkitEntity().getInventory().getArmorContents());
		}
	}
}
