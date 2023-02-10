package anokaze.kazedice.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/02/08
 */
@Data
public class Role {
    @SerializedName("_id")
    private ObjectId id;
    private String name;
    private String userId;
    private String categoryId;
    private Map<String, Integer> attributes;
}
