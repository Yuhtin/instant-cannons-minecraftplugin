package com.gabrieldev.instantcannons.commands;

import com.gabrieldev.instantcannons.Main;
import com.gabrieldev.instantcannons.apis.ItemComposer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CanhaoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender.hasPermission("gainstacannon.givecommand")){

            if(args.length == 2){

                FileConfiguration config = Main.getInstance().getConfig();

                for(String section : config.getConfigurationSection("Canhoes").getKeys(false)){

                    ItemStack item = ItemComposer.of(config.getString("Canhoes." + section + ".Skull"))
                            .setName(config.getString("Canhoes." + section + ".Name"))
                            .setLore(config.getStringList("Canhoes." + section + ".Lore"))
                            .setNBTTag("gbCanhão", section)
                            .toItemStack();

                    if(args[0].equalsIgnoreCase(section)){

                        Player target = Bukkit.getPlayer(args[1]);

                        if(!(target == null) && target.isOnline()){

                            target.getInventory().addItem(item);

                        }else{

                            sender.sendMessage("§cEste jogador encontra-se offline ou não existe!");
                            return true;

                        }

                    }

                }

            }else {

                sender.sendMessage("§cArgumentos inválidos! Utilize /givecanhao <tipo-do-canhão> <jogador>");
                return true;

            }

        }else{

            sender.sendMessage(Main.getInstance().getConfig().getString("Mensagens.noPermission").replace("&", "§"));
            return true;

        }
        return false;
    }

}
