/*
 *
 * Criado por Gabriel Santos (GabrielDev)
 * Todos os direitos reservados. 2020 - 2025
 * Discord: Gabriel Santos#4626
 *
 */
package com.gabrieldev.instantcannons;

import com.gabrieldev.instantcannons.commands.CanhaoCommand;
import com.gabrieldev.instantcannons.listeners.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("givecanhao").setExecutor(new CanhaoCommand());
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getConsoleSender().sendMessage("§a[gaInstantCannon] Plugin habilitado com sucesso!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§c[gaInstantCannon] Plugin desabilitado com sucesso!");
    }

    public static Main getInstance(){
        return getPlugin(Main.class);
    }

}
