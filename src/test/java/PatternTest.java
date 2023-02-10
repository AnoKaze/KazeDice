import anokaze.kazedice.constants.Patterns;

import java.util.regex.Matcher;

/**
 * @author ymma
 * @date 2023/2/10
 */
public class PatternTest {
    public static void main(String[] args) {
        String target = "力量50敏捷45外貌80";
        Matcher m = Patterns.STATES_PATTERN.matcher(target);
        m.find();
        for(int i = 0; i < m.groupCount(); i++){
            System.out.println(m.group(i));
        }
    }
}
