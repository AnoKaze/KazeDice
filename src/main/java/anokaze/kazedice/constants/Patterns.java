package anokaze.kazedice.constants;

import java.util.regex.Pattern;

public class Patterns {
    public static Pattern dicePattern = Pattern.compile("^(-?\\d+)*[d,D]?(\\d+)*$");
    public static Pattern operatorPattern = Pattern.compile("(?<=\\d)[-+*/](?!$)");
}
