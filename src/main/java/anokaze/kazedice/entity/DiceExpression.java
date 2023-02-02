package anokaze.kazedice.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DiceExpression {
    private final List<String> operators;
    private final List<DiceGroup> diceGroups;
    private final Integer value;

    public DiceExpression(List<String> operators, List<DiceGroup> diceGroups){
        this.operators = operators;
        this.diceGroups = diceGroups;
        this.value = initValue();
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

    private Integer initValue(){
        List<Integer> numberCache = new ArrayList<>(diceGroups.size());
        List<String> operatorCache = new ArrayList<>(operators.size());

        numberCache.add(diceGroups.get(0).getValue());
        for(int i = 0; i < operators.size(); i++){
            Integer operand2 = diceGroups.get(i+1).getValue();
            String operator = operators.get(i);

            if("*".equals(operator) || "/".equals(operator)){
                int index = numberCache.size() - 1;
                Integer operand1 = numberCache.get(index);
                numberCache.set(index, operate(operand1, operand2, operators.get(i)));
            }
            else{
                numberCache.add(operand2);
                operatorCache.add(operator);
            }
        }

        for(int i = 0; i < operatorCache.size();i++){
            Integer operand1 = numberCache.get(i);
            Integer operand2 = numberCache.get(i+1);
            String operator = operatorCache.get(i);
            numberCache.set(i+1, operate(operand1, operand2, operator));
        }
        return numberCache.get(numberCache.size() - 1);
    }

    private Integer operate(Integer operand1, Integer operand2, String operator){
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                return operand1;
        }
    }
}
