package anokaze.kazedice.util;

import anokaze.kazedice.constants.BotExceptions;
import anokaze.kazedice.constants.Patterns;
import anokaze.kazedice.entity.BotException;
import anokaze.kazedice.entity.DiceGroup;
import anokaze.kazedice.entity.DiceExpression;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author AnoKaze
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
        if(faces < 0) {
            return null;
        } else if(faces == 0) {
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

    /***
     * 根据字符串获取骰子组对象
     * @param diceGroup 骰子组的字符串形式
     * @return 骰子表达式对象
     */
    public static DiceGroup diceGroupParser(String diceGroup){
        Matcher matcher;
        matcher = Patterns.nxxDicePattern.matcher(diceGroup);
        if(matcher.find()){
            int n = Integer.parseInt(matcher.group(1));
            return new DiceGroup(n, null, null);
        }
        matcher = Patterns.ndxDicePattern.matcher(diceGroup);
        if(matcher.find()){
            int n = matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1));
            int d = Integer.parseInt(matcher.group(2));
            return new DiceGroup(n,d,null);
        }
        matcher = Patterns.ndkDicePattern.matcher(diceGroup);
        if(matcher.find()){
            int n = Integer.parseInt(matcher.group(1));
            int d = Integer.parseInt(matcher.group(2));
            int k = Integer.parseInt(matcher.group(3));
            if(Math.abs(n) < k){
                throw new BotException(BotExceptions.DICE_GROUP_PARSE_EXCEPTION, diceGroup);
            }
            return new DiceGroup(n,d,k);
        }
        throw new BotException(BotExceptions.DICE_GROUP_PARSE_EXCEPTION, diceGroup);
    }

    /***
     * 根据字符串获取骰子表达式对象
     * @param expression 骰子表达式的字符串形式
     * @return 骰子表达式
     */
    public static DiceExpression diceExpressionParser(String expression){
        List<String> operators = getOperators(expression);
        String[] diceGroupStr = expression.split(String.valueOf(Patterns.operatorPattern));
        List<DiceGroup> diceGroups = new ArrayList<>();
        for(String item: diceGroupStr){
            DiceGroup newGroup = diceGroupParser(item);
            diceGroups.add(newGroup);
        }
        return new DiceExpression(operators, diceGroups);
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
