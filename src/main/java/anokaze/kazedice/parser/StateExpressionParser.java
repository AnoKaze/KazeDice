package anokaze.kazedice.parser;

import anokaze.kazedice.entity.expression.StateExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AnoKaze
 * @since 2023/2/24
 */
public class StateExpressionParser implements Function<String, StateExpression> {
    private static final Pattern STATE_REGEX = Pattern.compile("^(?:[^-]+-)?(?:[a-zA-Z\u4e00-\u9fa5]+\\d+)+$");
    private static final Pattern ATTR_REGEX = Pattern.compile("([\u4e00-\u9fa5]+)(\\d+)");

    @Override
    public StateExpression apply(String s) {
        StateExpression result = new StateExpression();

        if(!Pattern.matches(STATE_REGEX.pattern(), s)){
            result.setRoleName(s);
            return result;
        }

        String splitChar = "-";

        String name = null;
        String attribute = s;
        if(s.contains(splitChar)){
            String[] splits = s.split(splitChar);
            name = splits[0];
            attribute = splits[1];
        }

        Map<String, Integer> attributes = new HashMap<>(16);
        Matcher m = ATTR_REGEX.matcher(attribute);
        while(m.find()){
            String key = m.group(1);
            Integer value = Integer.parseInt(m.group(2));
            attributes.put(key, value);
        }


        result.setRoleName(name);
        result.setAttributes(attributes);
        return result;
    }
}
