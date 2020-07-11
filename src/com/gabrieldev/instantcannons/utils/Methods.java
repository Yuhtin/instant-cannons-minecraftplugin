package com.gabrieldev.instantcannons.utils;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class Methods {

    public static int getPlayerDirection(Player player) {
        float yaw = player.getLocation().getYaw();
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw >= 315 || yaw < 45) {
            return 180;
        } else if (yaw < 135) {
            return 270;
        } else if (yaw < 225) {
            return 0;
        } else if (yaw < 315) {
            return 90;
        }
        return 0;
    }

    public static void loadSchematic(Location startBlock, String fileName, Player player) {
        BukkitWorld world = new BukkitWorld(startBlock.getWorld());
        EditSession session = new EditSession((LocalWorld) world, 10000);
        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        WorldEdit we = wep.getWorldEdit();
        LocalConfiguration config = we.getConfiguration();
        BukkitPlayer p = wep.wrapPlayer(startBlock.getWorld().getPlayers().get(0));
        File dir = we.getWorkingDirectoryFile(config.saveDir);
        Vector v = new Vector(startBlock.getX(), startBlock.getY(), startBlock.getZ());
        try {
            File f = we.getSafeOpenFile(p, dir, fileName, "schematic", "schematic");
            CuboidClipboard cc = SchematicFormat.MCEDIT.load(f);
            cc.rotate2D(getPlayerDirection(player));
            cc.paste(session, v, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeItem(Player player, int amount){
        ItemStack hand = player.getInventory().getItemInHand();
        hand.setAmount(hand.getAmount() - amount);
        player.getInventory().setItemInHand(hand);
    }

}
