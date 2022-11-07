package me.n2g7mutf8.soup.user;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.n2g7mutf8.soup.ServerData;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.kit.KitHandler;
import me.n2g7mutf8.soup.utils.configuration.Config;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Profile {

    private final SoupPvP plugin = SoupPvP.getInstance();

    private final UUID uniqueId;
    private Kit currentKit, lastKit;
    private List<Kit> unlockedKits = new ArrayList<>();
    private PlayerState playerState;
    private int kills, deaths, bounty, credits, currentKillstreak, highestKillstreak;
    private boolean scoreboardEnabled;

    public Profile(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.kills = 0;
        this.deaths = 0;
        this.bounty = 0;
        this.credits = 0;
        this.currentKillstreak = 0;
        this.highestKillstreak = 0;
        this.scoreboardEnabled = true;
        this.playerState = PlayerState.SPAWN;

        unlockedKits.addAll(plugin.getServerData().getDefaultKits());

        loadProfile();
    }

    private void loadProfile() {
        Document document = (Document) SoupPvP.getInstance().getPvPDB().getProfiles().find(Filters.eq("uuid", uniqueId.toString())).first();
        if (document != null) {
            if (document.containsKey("kills")) {
                kills = document.getInteger("kills");
            }
            if (document.containsKey("deaths")) {
                deaths = document.getInteger("deaths");
            }
            if (document.containsKey("bounty")) {
                bounty = document.getInteger("bounty");
            }
            if (document.containsKey("credits")) {
                credits = document.getInteger("credits");
            }
            if (document.containsKey("lastKit")) {
                lastKit = KitHandler.getByName(document.getString("lastKit"));
            }
            if (document.containsKey("scoreboardEnabled")) {
                scoreboardEnabled = document.getBoolean("scoreboardEnabled");
            }
            if (document.containsKey("unlockedKits")) {
                for (Object o : (ArrayList<?>) document.get("unlockedKits")) {
                    Kit kit = KitHandler.getByName(((Document) o).getString("name"));
                    if (kit == null) {
                        continue;
                    }
                    unlockedKits.add(kit);
                }
            }
        }
    }

    void saveProfile() {
        Document document = new Document();
        document.put("uuid", uniqueId.toString());
        document.put("kills", kills);
        document.put("deaths", deaths);
        document.put("bounty", bounty);
        document.put("credits", credits);
        document.put("scoreboardEnabled", scoreboardEnabled);

        if (lastKit != null) {
            document.put("lastKit", lastKit.getName());
        }

        BasicDBList objects = new BasicDBList();

        for (Kit kit : unlockedKits) {
            BasicDBObject object = new BasicDBObject();
            object.append("name", kit.getName());
            objects.add(object);
        }

        document.put("unlockedKits", objects);

        SoupPvP.getInstance().getPvPDB().getProfiles().replaceOne(Filters.eq("uuid", uniqueId.toString()), document, new ReplaceOptions().upsert(true));
    }
}
