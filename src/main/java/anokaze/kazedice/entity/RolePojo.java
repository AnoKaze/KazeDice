package anokaze.kazedice.entity;

import anokaze.kazedice.constants.AttributeEnum;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/02/08
 */
@Data
public class RolePojo {
    @SerializedName("_id")
    private ObjectId id;
    private String name;
    private String userId;
    private Map<String, Integer> attributes;

    public void initAttributes(){
        attributes = new HashMap<>(60);
        AttributeEnum[] defaultAttributes = AttributeEnum.values();
        for(AttributeEnum attribute: defaultAttributes){
            attributes.put(attribute.getName(), attribute.getBase());
        }
    }
}
