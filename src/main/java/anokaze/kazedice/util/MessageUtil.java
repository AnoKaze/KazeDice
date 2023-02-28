package anokaze.kazedice.util;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class MessageUtil {
    public static String getProgressBar(Integer current, Integer max){
        return getProgressBar(current, max, ":green_square:", ":white_large_square:", 10);
    }

    public static String getProgressBar(Integer current, Integer max, String filled, String empty){
        return getProgressBar(current, max, filled, empty, 10);
    }

    public static String getProgressBar(Integer current, Integer max, String filled, String empty, Integer granularity){
        StringBuilder result = new StringBuilder();
        result.append(current).append(" / ").append(max).append("\t");

        int greenNum = current * granularity / max;
        for(int i = 0; i < granularity; i++){
            if(i < greenNum){ result.append(filled); }
            else{ result.append(empty); }
        }

        return result.toString();
    }
}
