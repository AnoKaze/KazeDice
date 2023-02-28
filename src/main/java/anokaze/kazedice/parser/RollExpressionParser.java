package anokaze.kazedice.parser;

import anokaze.kazedice.entity.expression.RollExpression;
import anokaze.kazedice.entity.expression.DiceGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class RollExpressionParser implements Function<String, RollExpression> {
    private static final Pattern PATTERN = Pattern.compile("(?<=\\d)[-+](?!$)");

    @Override
    public RollExpression apply(String s) {
        DiceGroupParser diceGroupParser = new DiceGroupParser();

        List<String> operators = getOperators(s);
        String[] diceGroupStr = s.split(PATTERN.pattern());
        List<DiceGroup> diceGroups = new ArrayList<>();
        for(String item: diceGroupStr){
            DiceGroup newGroup = diceGroupParser.apply(item);
            if(newGroup == null){
                return null;
            }
            diceGroups.add(newGroup);
        }
        return new RollExpression(operators, diceGroups);
    }

    private List<String> getOperators(String expression){
        List<String> operators = new ArrayList<>();

        Matcher m = PATTERN.matcher(expression);
        while(m.find()){
            operators.add(m.group());
        }
        return operators;
    }
}
