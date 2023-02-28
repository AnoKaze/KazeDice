package anokaze.kazedice.util;

import anokaze.kazedice.constants.Constants;
import anokaze.kazedice.entity.Assay;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/02/01
 */
@Slf4j
public class DiceUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /***
     * 投出一枚指定面数的骰子
     * @param faces 骰子面数
     * @return 投出结果
     */
    public static Integer rollDie(int faces){
        if(faces <= 0) {
            return 0;
        } else {
            return SECURE_RANDOM.nextInt(faces) + 1;
        }
    }

    /***
     * 投出若干枚指定面数的骰子
     * @param number 骰子个数
     * @param faces 骰子面数
     * @return 投出结果
     */
    public static List<Integer> rollDice(int number, int faces){
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < number; i++){
            result.add(rollDie(faces));
        }
        return result;
    }

    public static Assay normalAssay(String attrName, Integer attrValue){
        Assay assay = new Assay();

        assay.setAttributeName(attrName);
        assay.setAttributeValue(attrValue);
        assay.setType(Constants.ASSAY_NORMAL);
        assay.setPoint(DiceUtil.rollDie(100));

        return assay;
    }

    public static Assay bonusAssay(String attrName, Integer attrValue, Integer bonus){
        Assay assay = new Assay();

        int one = rollDie(10) - 1;
        List<Integer> tens = new ArrayList<>(bonus + 1);
        for (int i = 0; i < bonus + 1; i++){
            tens.add(rollDie(10) - 1);
        }

        assay.setAttributeName(attrName);
        assay.setAttributeValue(attrValue);
        assay.setType(Constants.ASSAY_BONUS);
        assay.setTens(tens);
        int minPoint = 100;
        for(Integer ten: tens){
            int point = ten * 10 + one;
            point = point == 0 ? 100 : point;
            if(minPoint > point){
                minPoint = point;
            }
        }
        assay.setPoint(minPoint);

        return assay;
    }

    public static Assay punishAssay(String attrName, Integer attrValue, Integer punish){
        Assay assay = new Assay();

        int one = rollDie(10) - 1;
        List<Integer> tens = new ArrayList<>(punish + 1);
        for (int i = 0; i < punish + 1; i++){
            tens.add(rollDie(10) - 1);
        }

        assay.setAttributeName(attrName);
        assay.setAttributeValue(attrValue);
        assay.setType(Constants.ASSAY_PUNISH);
        assay.setTens(tens);
        int maxPoint = 0;
        for(Integer ten: tens){
            int point = ten * 10 + one;
            point = point == 0 ? 100 : point;
            if(maxPoint < point){
                maxPoint = point;
            }
        }
        assay.setPoint(maxPoint);

        return assay;
    }
}
