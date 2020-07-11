package com.gabrieldev.instantcannons.listeners;

import com.gabrieldev.instantcannons.Main;
import com.gabrieldev.instantcannons.apis.ItemComposer;
import com.gabrieldev.instantcannons.utils.Methods;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Events extends Methods implements Listener {

    @EventHandler
    void event(PlayerInteractEvent event){

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        MPlayer mp = MPlayer.get(player);
        PS ps = PS.valueOf(mp.getPlayer().getLocation());
        Faction fac = BoardColl.get().getFactionAt(ps);

        FileConfiguration config = Main.getInstance().getConfig();

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(fac.getName().equals(mp.getFaction().getName())
                    || fac.getName().equals(FactionColl.get().getNone().getName())){

                for(String section : config.getConfigurationSection("Canhoes").getKeys(false)){

                    ItemStack canhao = ItemComposer.of(config.getString("Canhoes." + section + ".Skull"))
                            .setName(config.getString("Canhoes." + section + ".Name"))
                            .setLore(config.getStringList("Canhoes." + section + ".Lore"))
                            .setNBTTag("gbCanhão", section)
                            .toItemStack();

                    if(item.isSimilar(canhao)){

                        player.sendMessage(config.getString("Canhoes." + section + ".msgPosicionado").replace("&", "§"));
                        loadSchematic(player.getLocation(), config.getString("Canhoes." + section + ".Schematic"), player);
                        removeItem(player, 1);

                    }

                }

            }else{

                player.sendMessage(config.getString("Mensagens.noZone").replace("&", "§"));

            }

        }

    }

    @EventHandler
    void block(BlockPlaceEvent e){

        Block blockPlaced = e.getBlockPlaced();

        if(!blockPlaced.getType().equals(Material.SKULL_ITEM) && !e.getItemInHand().hasItemMeta())return;

        e.setCancelled(true);

    }

}
