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
    private Map<String, Integer> states;
    private Map<String, Integer> bonus;

    public RolePojo(){}

    public RolePojo(String name, String userId){
        this.name = name;
        this.userId = userId;

        attributes = new HashMap<>(60);
        AttributeEnum[] defaultAttributes = AttributeEnum.values();
        for(AttributeEnum attribute: defaultAttributes){
            attributes.put(attribute.getName(), attribute.getBase());
        }

        states = new HashMap<>(8);
        bonus = new HashMap<>();
    }
}
