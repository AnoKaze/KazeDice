package anokaze.kazedice.entity.expression;

import lombok.Data;

import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/2/24
 */
@Data
public class StateExpression {
    private String roleName;
    private Map<String, Integer> attributes;
}
