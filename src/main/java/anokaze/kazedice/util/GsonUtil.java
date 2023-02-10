package anokaze.kazedice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

/**
 * @author AnoKaze
 * @since 2023/2/10
 */
public class GsonUtil {
    private static final Gson GSON = new GsonBuilder().create();

    public static String toJson(Object object){
        return GSON.toJson(object);
    }

    public static <T> T toBean(String json, Class<T> beanClass){
        return GSON.fromJson(json, beanClass);
    }

    public static Document toDocument(Object object){
        String json = toJson(object);
        return Document.parse(json);
    }

    public static <T> T toBean(Document document, Class<T> beanClass){
        if(document == null){
            return null;
        }
        String json = document.toJson();
        return toBean(json, beanClass);
    }
}
