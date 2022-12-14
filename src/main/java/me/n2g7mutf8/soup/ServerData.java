package me.n2g7mutf8.soup;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Data;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.kit.KitHandler;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cuboid.Cuboid;
import me.n2g7mutf8.soup.utils.location.LocationUtils;
import org.bson.Document;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServerData {

    private final Config config = SoupPvP.getInstance().getSettings();

    private String spawn, spawnBoss, firstCI, secondCI;
    private boolean freeKitsMode;
    private List<Kit> defaultKits;

    public ServerData() {
        defaultKits = new ArrayList<>();
        loadServer();
    }

    private void loadServer() {
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
        for (String kitName : config.getStringList("General.Default-Kits")) {
            Kit kit = KitHandler.getByName(kitName);

            if (kit == null) continue;
            defaultKits.add(kit);
        }
    }

    public void saveServer() {
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

    public Cuboid getSpawnCuboID() {
        Location first = LocationUtils.getLocation(firstCI);
        Location second = LocationUtils.getLocation(secondCI);
        return new Cuboid(first, second);
    }

    public Location getSpawnLocation() {
        return LocationUtils.getLocation(spawn);
    }

    public Location getSpawnBossLocation() {
        return LocationUtils.getLocation(spawnBoss);
    }

}
