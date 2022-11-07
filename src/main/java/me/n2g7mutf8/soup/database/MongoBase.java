package me.n2g7mutf8.soup.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.utils.configuration.Config;

@Getter
public class MongoBase {

    private final Config config = SoupPvP.getInstance().getSettings();
    private final MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection profiles;
    private final MongoCollection serverInformation;

    public MongoBase() {

        client = MongoClients.create(config.getString("MongoDB.URI"));

        database = client.getDatabase("SoupPvP");
        profiles = database.getCollection("profiles");
        serverInformation = database.getCollection("server");
    }
}
