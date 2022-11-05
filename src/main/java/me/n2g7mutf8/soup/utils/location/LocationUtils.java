package me.n2g7mutf8.soup.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static String getString(Location loc) {
        if (loc == null) {
            return "Location Not Found";
        }
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getName() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    public static Location getLocation(final String s) {
        try {
            final String[] data = s.split(";");
            final double x = Double.parseDouble(data[0]);
            final double y = Double.parseDouble(data[1]);
            final double z = Double.parseDouble(data[2]);
            final World world = Bukkit.getWorld(data[3]);
            final float yaw = Float.parseFloat(data[4]);
            final float pitch = Float.parseFloat(data[5]);
            return new Location(world, x, y, z, yaw, pitch);
        } catch (NullPointerException c) {
            Bukkit.getServer().getLogger().severe("[Location Debugger] SOS ! There is an error while try to get location !");
            throw c;
        }
    }

    public static boolean isSameLocation(Location loc1, Location loc2) {
        return loc1 != null && loc1.equals(loc2);
    }
}