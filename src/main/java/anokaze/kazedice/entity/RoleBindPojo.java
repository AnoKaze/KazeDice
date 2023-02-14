package anokaze.kazedice.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * @author AnoKaze
 * @since 2023/2/11
 */
@Data
public class RoleBindPojo {
    @SerializedName("_id")
    private String id;
    private String categoryId;
    private String userId;
    private String roleId;

    public RoleBindPojo(){}

    public RoleBindPojo(String categoryId, String userId, String roleId){
        this.id = categoryId + userId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.roleId = roleId;
    }
}
