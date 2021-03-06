package org.sotap.MissionTap.Utils;

import org.sotap.MissionTap.MissionTap;
import org.sotap.MissionTap.Menus.InprogressMenu;
import org.sotap.MissionTap.Menus.MainMenu;
import org.sotap.MissionTap.Menus.MissionMenu;

public final class Menus {
    public static MissionMenu dailyMissionMenu;
    public static MissionMenu weeklyMissionMenu;
    public static MissionMenu specialMissionMenu;
    public static MainMenu mainMenu;
    public static InprogressMenu inprogressMenu;

    public static void init(MissionTap plugin) {
        dailyMissionMenu = new MissionMenu("daily", plugin);
        weeklyMissionMenu = new MissionMenu("weekly", plugin);
        specialMissionMenu = new MissionMenu("special", plugin);
        mainMenu = new MainMenu(plugin);
        inprogressMenu = new InprogressMenu(plugin);
    }
}