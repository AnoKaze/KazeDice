import anokaze.kazedice.entity.Role;
import anokaze.kazedice.util.GsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
public class MongodbTest {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://49.234.30.82:27017");
        MongoDatabase database = client.getDatabase("kazedice");
        MongoCollection<Document> collection = database.getCollection("role");

        insert(collection);

        BasicDBObject query = new BasicDBObject("name", "Cristina");
        Document find = collection.findOneAndDelete(query);
        Role findRole = GsonUtil.toBean(find, Role.class);
        System.out.println(findRole.toString());
        client.close();
    }

    private static void insert(MongoCollection<Document> collection){
        Map<String, Integer> map = new HashMap<>();
        map.put("力量", 50);
        Role role = new Role();
        role.setId(null);
        role.setName("Cristina");
        role.setUserId("userId");
        role.setCategoryId("categoryId");
        role.setAttributes(map);
        InsertOneResult result = collection.insertOne(GsonUtil.toDocument(role));
        System.out.println(result);
    }
}
