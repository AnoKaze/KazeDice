package anokaze.kazedice.mapper;

import anokaze.kazedice.KazeDicePlugin;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
public class MapperManager {

    private final MongoClient client;
    private final RoleMapper roleMapper;

    public MapperManager(){
        String uri = KazeDicePlugin.getInstance().getConfig().getString("mongo.uri");
        assert uri != null;
        client = MongoClients.create(uri);
        MongoDatabase database = client.getDatabase("kazedice");

        roleMapper = new RoleMapper(database.getCollection("role"));
    }

    public void disconnect(){
        client.close();
    }

    public RoleMapper getRoleMapper(){
        return roleMapper;
    }
}
