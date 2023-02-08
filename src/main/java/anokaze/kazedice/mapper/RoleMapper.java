package anokaze.kazedice.mapper;

import anokaze.kazedice.KazeDicePlugin;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author ymma
 * @date 2023/2/8
 */
public class RoleMapper {
    private final static String ROLE_COLLECTION_NAME = "Role";
    private MongoCollection<Document> getCollection(){
        MongoDatabase database = KazeDicePlugin.getMongodb();
        return database.getCollection(ROLE_COLLECTION_NAME);
    }
}
