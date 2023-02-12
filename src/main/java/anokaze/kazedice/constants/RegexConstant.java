package anokaze.kazedice.constants;

/**
 * 项目中需要的正则表达式常量
 * @author AnoKaze
 * @since 2023/02/01
 */
public class RegexConstant {
    public static final String NXX_DICE_REGEX = "^(-?\\d+)$";
    public static final String NDX_DICE_REGEX = "^(-?\\d+)?[dD](\\d+)$";
    public static final String NDK_DICE_REGEX = "^(-?\\d+)[dD](\\d+)[kK](\\d+)$";
    public static final String OPERATOR_REGEX = "(?<=\\d)[-+*/](?!$)";
    public static final String STATES_REGEX = "^(?:[\u4e00-\u9fa5]+\\d+)+$";
    public static final String STATE_REGEX = "([\u4e00-\u9fa5]+)(\\d+)";
}
