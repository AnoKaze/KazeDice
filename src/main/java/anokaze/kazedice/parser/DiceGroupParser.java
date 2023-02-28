package anokaze.kazedice.parser;

import anokaze.kazedice.constants.Constants;
import anokaze.kazedice.entity.expression.DiceGroup;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class DiceGroupParser implements Function<String, DiceGroup> {
    private static final Pattern NXX = Pattern.compile("^(-?\\d+)$");
    private static final Pattern NDX = Pattern.compile("^(-?\\d+)?[dD](\\d+)$");
    private static final Pattern NDK = Pattern.compile("^(-?\\d+)[dD](\\d+)[kK](\\d+)$");

    @Override
    public DiceGroup apply(String s) {
        Matcher matcher;
        matcher = NXX.matcher(s);
        if(matcher.find()){
            int n = Integer.parseInt(matcher.group(1));
            return new DiceGroup(n, null, null, Constants.DICE_GROUP_NXX);
        }
        matcher = NDX.matcher(s);
        if(matcher.find()){
            int n = matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1));
            int d = Integer.parseInt(matcher.group(2));
            return new DiceGroup(n, d, null, Constants.DICE_GROUP_NDX);
        }
        matcher = NDK.matcher(s);
        if(matcher.find()){
            int n = Integer.parseInt(matcher.group(1));
            int d = Integer.parseInt(matcher.group(2));
            int k = Integer.parseInt(matcher.group(3));
            if(Math.abs(n) < k){
                return null;
            }
            return new DiceGroup(n, d, k, Constants.DICE_GROUP_NDK);
        }
        return null;
    }
}
