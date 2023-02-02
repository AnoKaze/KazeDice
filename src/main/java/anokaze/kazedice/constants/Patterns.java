package anokaze.kazedice.constants;

import java.util.regex.Pattern;

/**
 * @author AnoKaze
 */
public class Patterns {
    public static Pattern nxxDicePattern = Pattern.compile("^(-?\\d+)$");
    public static Pattern ndxDicePattern = Pattern.compile("^(-?\\d+)?[dD](\\d+)$");
    public static Pattern ndkDicePattern = Pattern.compile("^(-?\\d+)[dD](\\d+)[kK](\\d+)$");
    public static Pattern operatorPattern = Pattern.compile("(?<=\\d)[-+*/](?!$)");
}
