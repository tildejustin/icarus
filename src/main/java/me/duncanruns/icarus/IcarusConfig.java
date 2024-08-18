package me.duncanruns.icarus;

import org.mcsr.speedrunapi.config.api.SpeedrunConfig;
import org.mcsr.speedrunapi.config.api.annotations.Config;

public class IcarusConfig implements SpeedrunConfig {
    @Config.Numbers.Whole.Bounds(min = 1, max = 3)
    public static int flightDuration = 3;

    public static boolean offhand = false;

    @Override
    public String modID() {
        return "icarus";
    }
}
