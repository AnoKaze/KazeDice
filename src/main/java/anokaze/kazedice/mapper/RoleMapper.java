package anokaze.kazedice.mapper;

import anokaze.kazedice.entity.Role;
import anokaze.kazedice.util.GsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/02/08
 */
public class RoleMapper {
    private final MongoCollection<Document> collection;

    public RoleMapper(MongoCollection<Document> collection){
        this.collection = collection;
    }

    public void insertRole(Role role){
        collection.insertOne(GsonUtil.toDocument(role));
    }

    public boolean removeRoleByName(String name){
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        Document find = collection.findOneAndDelete(query);
        return find != null;
    }

    public Role findCurrentRole(String userId, String categoryId){
        BasicDBObject query = new BasicDBObject();
        query.put("userId", userId);
        query.put("categoryId",categoryId);
        Document find = collection.find(query).first();
        return GsonUtil.toBean(find, Role.class);
    }

    public List<Role> findUserRoles(String userId){
        List<Role> result = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        query.put("userId", userId);
        FindIterable<Document> finds = collection.find(query);
        for(Document find: finds){
            result.add(GsonUtil.toBean(find, Role.class));
        }
        return result;
    }

    public Role findRoleByName(String name){
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        Document find = collection.find(query).first();
        return GsonUtil.toBean(find, Role.class);
    }

    public boolean updateRoleByName(String name, Role role){
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        Document find = collection.findOneAndReplace(query, GsonUtil.toDocument(role));
        return find != null;
    }
}
