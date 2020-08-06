package org.sotap.MissionTap;

import org.bukkit.plugin.java.JavaPlugin;
import org.sotap.MissionTap.Utils.Functions;
import org.sotap.MissionTap.Utils.Logger;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Functions.initUtils(this);
        Functions.initMissions(this);
        Functions.refreshMissions(this);
        // todo: menu
    }

    @Override
    public void onDisable() {

    }

    public void log(String message) {
        this.getLogger().info(Logger.translateColor(message));;
    }

    public void reload() {
        Functions.initUtils(this);
    }
}