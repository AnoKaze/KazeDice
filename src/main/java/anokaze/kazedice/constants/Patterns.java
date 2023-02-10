package anokaze.kazedice.constants;

import java.util.regex.Pattern;

/**
 * 项目中需要的正则表达式常量
 * @author AnoKaze
 * @since 2023/02/01
 */
public class Patterns {
    public static final Pattern NXX_DICE_PATTERN = Pattern.compile("^(-?\\d+)$");
    public static final Pattern NDX_DICE_PATTERN = Pattern.compile("^(-?\\d+)?[dD](\\d+)$");
    public static final Pattern NDK_DICE_PATTERN = Pattern.compile("^(-?\\d+)[dD](\\d+)[kK](\\d+)$");
    public static final Pattern OPERATOR_PATTERN = Pattern.compile("(?<=\\d)[-+*/](?!$)");
    public static final Pattern STATES_PATTERN = Pattern.compile("([\u4e00-\u9fa5]+)(\\d+)");
}
