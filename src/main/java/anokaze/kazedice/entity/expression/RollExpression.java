package anokaze.kazedice.entity.expression;

import anokaze.kazedice.parser.RollExpressionParser;
import lombok.Getter;

import java.util.List;

/**
 * 完整的掷骰表达式
 * @author AnoKaze
 * @since 2023/02/02
 */
@Getter
public class RollExpression {
    private final List<String> operators;
    private final List<DiceGroup> diceGroups;
    private Integer value;

    public RollExpression(String s) {
        RollExpressionParser parser = new RollExpressionParser();
        RollExpression expression = parser.apply(s);
        operators = expression.getOperators();
        diceGroups = expression.getDiceGroups();
    }

    public RollExpression(List<String> operators, List<DiceGroup> diceGroups){
        this.operators = operators;
        this.diceGroups = diceGroups;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i =  0; i < diceGroups.size(); i++){
            if(i != 0){
                result.append(operators.get(i-1));
            }
            result.append(diceGroups.get(i).getExpression());
        }

        for(int i = 0; i < diceGroups.size(); i++){
            if(i == 0){
                result.append("=").append(diceGroups.get(i).getFormula());
            } else {
                result.append(operators.get(i-1)).append(diceGroups.get(i).getFormula());
            }
        }
        if(diceGroups.size() > 1){
            result.append("=").append(getValue());
        }
        return result.toString();
    }

    public void reset(){
        for(DiceGroup diceGroup: diceGroups){
            diceGroup.reset();
        }

        int value = diceGroups.get(0).getValue();
        for(int i = 0; i < operators.size(); i++){
            value = operate(value, diceGroups.get(i+1).getValue(), operators.get(i));
        }
        this.value = value;
    }

    private Integer operate(Integer operand1, Integer operand2, String operator){
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            default:
                return operand1;
        }
    }
}
