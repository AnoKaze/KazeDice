package anokaze.kazedice.util;

import anokaze.kazedice.constants.Patterns;
import anokaze.kazedice.entity.DiceGroup;
import anokaze.kazedice.entity.DiceExpression;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;

public class DiceUtil {
    private static final SecureRandom secureRandom = new SecureRandom();

    /***
     * 投出一枚指定面数的骰子
     * @param sides 骰子面数
     * @return 骰子结果
     */
    public static Integer rollDice(int sides) {
        return secureRandom.nextInt(sides) + 1;
    }

    /***
     * 根据字符串获取骰子表达式对象
     * @param diceGroup 骰子组的字符串形式
     * @return 骰子表达式对象
     */
    public static DiceGroup DiceGroupParser(String diceGroup){
        Matcher matcher = Patterns.dicePattern.matcher(diceGroup);
        if(matcher.find()){
            int total = matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1));
            int sides = matcher.group(2) == null ? -1 : Integer.parseInt(matcher.group(2));
            if(sides == -1 || sides == 0) {
                return new DiceGroup(total, sides, null);
            }
            else {
                List<Integer> points = new ArrayList<>(total);
                for(int i = 0; i < total; i++){
                    points.add(DiceUtil.rollDice(sides));
                }
                return new DiceGroup(total, sides, points);
            }
        }
        return null;
    }

    public static DiceExpression DiceExpressionParser(String expression){
        List<String> operators = getOperators(expression);
        String[] diceGroupStr = expression.split(String.valueOf(Patterns.operatorPattern));
        List<DiceGroup> diceGroups = new ArrayList<>();
        for(String item: diceGroupStr){
            DiceGroup newGroup = DiceGroupParser(item);
            if(newGroup == null) { return null; }
            diceGroups.add(newGroup);
        }
        return new DiceExpression(expression, operators, diceGroups);
    }

    private static List<String> getOperators(String expression){
        List<String> operators = new ArrayList<>();
        Matcher m = Patterns.operatorPattern.matcher(expression);
        while(m.find()){
            operators.add(m.group());
        }
        return operators;
    }
}
