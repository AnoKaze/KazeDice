package anokaze.kazedice.mapper;

import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.util.GsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

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

    public InsertOneResult insertRole(RolePojo role){
        return collection.insertOne(GsonUtil.toDocument(role));
    }

    public DeleteResult deleteRole(String id){
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.deleteOne(query);
    }

    public UpdateResult updateRole(RolePojo role){
        BasicDBObject query = new BasicDBObject();
        query.put("_id", role.getId());
        return collection.replaceOne(query, GsonUtil.toDocument(role));
    }

    public RolePojo findRole(RolePojo role){
        BasicDBObject query = getQueryBson(role);
        Document find = collection.find(query).first();
        return GsonUtil.toBean(find, RolePojo.class);
    }

    public List<RolePojo> findRoles(RolePojo role){
        List<RolePojo> result = new ArrayList<>();

        BasicDBObject query = getQueryBson(role);
        FindIterable<Document> finds = collection.find(query);
        for(Document find: finds){
            result.add(GsonUtil.toBean(find, RolePojo.class));
        }
        return result;
    }

    private BasicDBObject getQueryBson(RolePojo role){
        BasicDBObject query = new BasicDBObject();
        if(role.getId() != null){
            query.put("_id", role.getId());
        }
        if(role.getName() != null){
            query.put("name", role.getName());
        }
        if(role.getUserId() != null){
            query.put("userId", role.getUserId());
        }
        return query;
    }
}
