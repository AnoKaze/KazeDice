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
    private ObjectId id;
    private String categoryId;
    private String userId;
    private ObjectId roleId;
}
