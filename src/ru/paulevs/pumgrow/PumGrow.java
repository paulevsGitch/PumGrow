package ru.paulevs.pumgrow;

import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Pumpkin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

public class PumGrow extends JavaPlugin implements Listener {

	@Override
	   public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		@SuppressWarnings("unused")
		boolean loggingc = this.getConfig().getBoolean("Write log commands into chat", false);
		@SuppressWarnings("unused")
		int growspeedc = this.getConfig().getInt("Grow time in seconds", 120);
		
	    Bukkit.getPluginManager().registerEvents(this, this);
	   }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		final Player p = event.getPlayer();
	        if(p.getItemInHand().getType() == Material.PUMPKIN_SEEDS){
	        	event.setCancelled(true);
	        }
	        if(p.getItemInHand().getType() == Material.PUMPKIN){
	        	((Pumpkin) event.getBlockPlaced()).setFacingDirection(BlockFace.DOWN);
	        }
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event) {
		
		final Player p = event.getPlayer();

		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
		
		if(p.getItemInHand().getType() == Material.PUMPKIN_SEEDS){
			if (getConfig().getBoolean("Write log commands into chat") == true){
				p.sendMessage("Try to place");
			}
			final Location pl = p.getTargetBlock((Set<Material>) null, 0).getLocation();
			final Location plabove = p.getTargetBlock((Set<Material>) null, 0).getLocation();
			pl.setX(pl.getX()+0.5);
			pl.setZ(pl.getZ()+0.3);
			pl.setY(pl.getY()+0.18);
			plabove.setY(plabove.getY()+1);
			if (pl.getBlock().getType() == Material.SOIL && plabove.getBlock().getType() == Material.AIR) {
				pl.setY(pl.getY()+0.25);
				ArmorStand plant = p.getLocation().getWorld().spawn(pl, ArmorStand.class);
				pl.setY(pl.getY()-0.6);
				ArmorStand plant2 = p.getLocation().getWorld().spawn(pl, ArmorStand.class);
				p.getWorld().getBlockAt(plabove).setType(Material.LONG_GRASS);
				p.getWorld().getBlockAt(plabove).setData((byte)2);
				plant.setCustomName("pumkin1");
				plant.setSmall(true);
				plant.setGravity(false);
				ItemStack helm = new ItemStack(Material.PUMPKIN);
				plant.setHelmet(helm);
				plant.setHeadPose(EulerAngle(90,0,0));
				plant.setVisible(false);
				plant2.setGravity(false);
				plant2.setVisible(false);
				
				//Timer
				int seconds = getConfig().getInt("Grow time in seconds");
				@SuppressWarnings("unused")
				BukkitTask Timer = getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
					public void run() {
						if (plant.isDead() == false){
							if (getConfig().getBoolean("Write log commands into chat") == true){
								getServer().broadcastMessage("grow pumpkin!");
							}
						plant.remove();
						
						p.getWorld().getBlockAt(plabove).setType(Material.LONG_GRASS);
						p.getWorld().getBlockAt(plabove).setData((byte)2);
						plant2.setCustomName("pumkin1");
						plant2.setSmall(false);
						plant2.setGravity(false);
						plant2.setHelmet(helm);
						plant2.setHeadPose(EulerAngle(90,0,0));
						plant2.setVisible(false);
						}
						}
				    }, 20L*seconds);
				if (plant.isDead() == false){
				@SuppressWarnings("unused")
				BukkitTask Timer2 = getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
					public void run() {
						if (plant2.isDead() == false){
							if (getConfig().getBoolean("Write log commands into chat") == true){
								getServer().broadcastMessage("create pumpkin!");
							}
						p.getWorld().getBlockAt(plabove).setType(Material.PUMPKIN);
						byte dir = (byte)(Math.round(Math.random()*3));
						p.getWorld().getBlockAt(plabove).setData(dir);
						
						plant2.remove();
						}
						}
				    }, 20L*seconds*2);
				}
				}
	    	}
		//Melons
		if(p.getItemInHand().getType() == Material.MELON_SEEDS){
			if (getConfig().getBoolean("Write log commands into chat") == true){
				p.sendMessage("Try to place");
			}
			final Location pl = p.getTargetBlock((Set<Material>) null, 0).getLocation();
			final Location plabove = p.getTargetBlock((Set<Material>) null, 0).getLocation();
			pl.setX(pl.getX()+0.5);
			pl.setZ(pl.getZ()+0.5);
			plabove.setY(plabove.getY()+1);
			if (pl.getBlock().getType() == Material.SOIL && plabove.getBlock().getType() == Material.AIR) {
				pl.setY(pl.getY()+0.25);
				ArmorStand plantm = p.getLocation().getWorld().spawn(pl, ArmorStand.class);
				pl.setY(pl.getY()-0.75);
				ArmorStand plantm2 = p.getLocation().getWorld().spawn(pl, ArmorStand.class);
				p.getWorld().getBlockAt(plabove).setType(Material.LONG_GRASS);
				p.getWorld().getBlockAt(plabove).setData((byte)2);
				plantm.setCustomName("melon1");
				plantm.setSmall(true);
				plantm.setGravity(false);
				ItemStack helm = new ItemStack(Material.MELON_BLOCK);
				plantm.setHelmet(helm);
				plantm.setVisible(false);
				plantm2.setGravity(false);
				plantm2.setVisible(false);
				
				//Timer
				int seconds = getConfig().getInt("Grow time in seconds");
				@SuppressWarnings("unused")
				BukkitTask Timer = getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
					public void run() {
						if (plantm.isDead() == false){
							if (getConfig().getBoolean("Write log commands into chat") == true){
								getServer().broadcastMessage("grow melon!");
							}
						plantm.remove();
						
						p.getWorld().getBlockAt(plabove).setType(Material.LONG_GRASS);
						p.getWorld().getBlockAt(plabove).setData((byte)2);
						plantm2.setCustomName("melon1");
						plantm2.setSmall(false);
						plantm2.setGravity(false);
						plantm2.setHelmet(helm);
						plantm2.setVisible(false);
						}
						}
				    }, 20L*seconds);
				if (plantm.isDead() == false){
				@SuppressWarnings("unused")
				BukkitTask Timer2 = getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
					public void run() {
						if (plantm2.isDead() == false){
							if (getConfig().getBoolean("Write log commands into chat") == true){
								getServer().broadcastMessage("create melon!");
							}
						p.getWorld().getBlockAt(plabove).setType(Material.MELON_BLOCK);
						byte dir = (byte)(Math.round(Math.random()*3));
						p.getWorld().getBlockAt(plabove).setData(dir);
						
						plantm2.remove();
						}
						}
				    }, 20L*seconds*2);
				}
				}
	    	}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		final Player p = event.getPlayer();
		Location pos = event.getBlock().getLocation();
		pos.setX(pos.getX()-0.5);
		pos.setZ(pos.getZ()-0.7);
		pos.setY(pos.getY()-0.72);
		Location posf = event.getBlock().getLocation();
		posf.setY(posf.getY()-1);
		posf.setX(posf.getX()+0.5);
		Collection<Entity> ent = pos.getWorld().getNearbyEntities(pos, 1, 1, 1);
        if (event.getBlock().getType() == Material.LONG_GRASS && event.getBlock().getData() == ((byte)2) && ent.isEmpty() == false){
        	if (getConfig().getBoolean("Write log commands into chat") == true){
        		p.sendMessage("Try to breake");
        	}
    		List<Entity> near = pos.getWorld().getEntities();
    		for(Entity e : near) {
    		    if(e.getLocation().distanceSquared(posf) <= 0.75 && e.getType()==EntityType.ARMOR_STAND){
    		    	String name = e.getCustomName();
    		        e.remove();
    		        p.getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);
    		        ItemStack drop = new ItemStack(Material.PUMPKIN_SEEDS);
    		        if (name == "pumpkin1"){
    		        	drop.setType(Material.PUMPKIN_SEEDS);
    		        }
    		        if (name == "melon1"){
    		        	drop.setType(Material.MELON_SEEDS);
    		        }
					p.getWorld().dropItem(event.getBlock().getLocation(), drop );
    		    }
    		}
        }
            
        
     }
	
	private EulerAngle EulerAngle(int i, int j, int k) {
		double id = Math.toRadians(i);
		double jd = Math.toRadians(j);
		double kd = Math.toRadians(k);
		final org.bukkit.util.EulerAngle Angle = new EulerAngle(id, jd, kd);
		return Angle;
	}

	@EventHandler
	public void onPlayerUse(PlayerInteractAtEntityEvent event) {
		final EntityType actortype = event.getRightClicked().getType();
		String actorname = event.getRightClicked().getName();
		if (actortype == EntityType.ARMOR_STAND && actorname == "pumkin1"){
			event.setCancelled(true);
		}
		if (actortype == EntityType.ARMOR_STAND && actorname == "melon1"){
			event.setCancelled(true);
		}
	}
}
