package anokaze.kazedice.mapper;

import anokaze.kazedice.entity.RoleBindPojo;
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
 * @since 2023/2/11
 */
public class RoleBindMapper {
    private final MongoCollection<Document> collection;
    public RoleBindMapper(MongoCollection<Document> collection){
        this.collection = collection;
    }

    public InsertOneResult insertRoleBind(RoleBindPojo roleBind){
        return collection.insertOne(GsonUtil.toDocument(roleBind));
    }

    public DeleteResult deleteRoleBind(ObjectId id){
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.deleteOne(query);
    }

    public UpdateResult updateRoleBind(ObjectId id, RoleBindPojo roleBindPojo){
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.replaceOne(query, GsonUtil.toDocument(roleBindPojo));
    }

    public RoleBindPojo findRoleBind(RoleBindPojo roleBind){
        BasicDBObject query = getQueryBson(roleBind);
        Document find = collection.find(query).first();
        return GsonUtil.toBean(find, RoleBindPojo.class);
    }

    public List<RoleBindPojo> findRoleBinds(RoleBindPojo roleBind){
        List<RoleBindPojo> result = new ArrayList<>();

        BasicDBObject query = getQueryBson(roleBind);
        FindIterable<Document> finds = collection.find(query);
        for(Document find: finds){
            result.add(GsonUtil.toBean(find, RoleBindPojo.class));
        }
        return result;
    }

    private BasicDBObject getQueryBson(RoleBindPojo roleBind){
        BasicDBObject query = new BasicDBObject();
        if(roleBind.getId() != null){
            query.put("_id", roleBind.getId());
        }
        if(roleBind.getCategoryId() != null){
            query.put("categoryId", roleBind.getCategoryId());
        }
        if(roleBind.getUserId() != null){
            query.put("userId", roleBind.getUserId());
        }
        if(roleBind.getRoleId() != null){
            query.put("roleId", roleBind.getRoleId());
        }
        return query;
    }
}
