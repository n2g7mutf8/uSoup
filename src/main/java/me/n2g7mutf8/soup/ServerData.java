package me.n2g7mutf8.soup;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Data;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cuboid.Cuboid;
import me.n2g7mutf8.soup.utils.location.LocationUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Data
public class ServerData {

    private final Config config;

    private String spawn, spawnBoss, firstCI, secondCI;
    private boolean freeKitsMode;

    public ServerData(Config config) {
        this.config = config;
        loadServer();
    }

    private void loadServer() {
        if (!config.getBoolean("Location.Is-MongoDB")) {
            if (config.getString("Location.Spawn.Location") != null) {
                spawn = config.getString("Location.Spawn.Location");
            } else {
                Bukkit.getLogger().severe(ColorText.translate("&3[uSoup] &7Spawn location cannot be NULL!"));
            }
            if (config.getString("Location.Spawn.Corner-1") != null) {
                firstCI = config.getString("Location.Spawn.Corner-1");
            } else {
                Bukkit.getLogger().severe(ColorText.translate("&3[uSoup] &7Spawn Corner 1 cannot be NULL!"));
            }
            if (config.getString("Location.Spawn.Corner-2") != null) {
                secondCI = config.getString("Location.Spawn.Corner-2");
            } else {
                Bukkit.getLogger().severe(ColorText.translate("&3[uSoup] &7Spawn Corner 2 cannot be NULL!"));
            }
            if (config.getString("Location.Boss-Spawn") != null) {
                spawnBoss = config.getString("Location.Boss-Spawn");
            } else {
                Bukkit.getLogger().severe(ColorText.translate("&3[uSoup] &7Boss Spawn location cannot be NULL!"));
            }
        } else {
            Document document = (Document) SoupPvP.getInstance().getPvPDB().getServerInformation().find(Filters.eq("_faggot_", "mc")).first();
            if (document != null) {
                if (document.containsKey("spawn")) {
                    spawn = document.getString("spawn");
                }
                if (document.containsKey("spawnBoss")) {
                    spawnBoss = document.getString("spawnBoss");
                }
                if (document.containsKey("firstCI")) {
                    firstCI = document.getString("firstCI");
                }
                if (document.containsKey("secondCI")) {
                    secondCI = document.getString("secondCI");
                }
            }
        }
    }

    public void saveServer() {
        if (!config.getBoolean("Location.Is-MongoDB")) {
            config.save();
        } else {
            Document document = new Document("_faggot_", "mc");
            if (spawn != null) {
                document.put("spawn", spawn);
            }
            if (spawnBoss != null) {
                document.put("spawnBoss", spawnBoss);
            }
            if (firstCI != null) {
                document.put("firstCI", firstCI);
            }
            if (secondCI != null) {
                document.put("secondCI", secondCI);
            }
            SoupPvP.getInstance().getPvPDB().getServerInformation().replaceOne(Filters.eq("_faggot_", "mc"), document, new ReplaceOptions().upsert(true));
        }
    }

    public Cuboid getSpawnCuboID() {
        Location first = LocationUtils.getLocation(firstCI);
        Location second = LocationUtils.getLocation(secondCI);
        if (first == null || second == null) {
            return null;
        }
        return new Cuboid(first, second);
    }

    public Location getSpawnLocation() {
        return LocationUtils.getLocation(spawn);
    }

    public Location getSpawnBossLocation() {
        return LocationUtils.getLocation(spawnBoss);
    }

}
