package anokaze.kazedice.entity.expression;

import anokaze.kazedice.constants.Constants;
import anokaze.kazedice.parser.DiceGroupParser;
import anokaze.kazedice.util.DiceUtil;
import lombok.Getter;

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/02/01
 */
@Getter
public class DiceGroup {
    private static final DiceGroupParser PARSER = new DiceGroupParser();

    private final Integer n;
    private final Integer d;
    private final Integer k;
    private final Byte type;
    private List<Integer> points;
    private Integer value;

    public DiceGroup(String s){
        DiceGroup template = PARSER.apply(s);
        this.n = template.getN();
        this.d = template.getD();
        this.k = template.getK();
        this.type = template.getType();
    }

    public DiceGroup(Integer n, Integer d, Integer k, Byte type){
        this.n = n;
        this.d = d;
        this.k = k;
        this.type = type;
    }

    public String getExpression(){
        if(type.equals(Constants.DICE_GROUP_NXX)){
            return n.toString();
        }
        if(type.equals(Constants.DICE_GROUP_NDX)){
            return n + "d" + d;
        }
        if(type.equals(Constants.DICE_GROUP_NDK)){
            return n + "d" + d + "k" + k;
        }
        return null;
    }

    public String getFormula() {
        StringBuilder result = new StringBuilder();
        if(type.equals(Constants.DICE_GROUP_NXX)){
            result.append(n);
        }
        if(type.equals(Constants.DICE_GROUP_NDX)){
            for(int i = 0; i < Math.abs(n); i++){
                if(i != 0) {
                    result.append("+");
                }
                result.append(points.get(i));
            }
            if(Math.abs(n) > 1){
                result.insert(0, "(");
                result.append(")");
            }
            if(n < 0){
                result.insert(0, "-");
            }
        }
        if(type.equals(Constants.DICE_GROUP_NDK)){
            result.append("(");
            for(int i = 0; i < k; i++){
                if(i != 0) {
                    result.append("+");
                }
                result.append(points.get(i));
            }
            result.append(")").append("[");
            for (int i = 0; i < Math.abs(n); i++){
                if(i != 0) {
                    result.append(",");
                }
                result.append(points.get(i));
            }
            result.append("]");
            if(n < 0){
                result.insert(0, "-");
            }
        }
        return result.toString();
    }

    public void reset(){
        if(type.equals(Constants.DICE_GROUP_NXX)) {
            this.value = n;
            return;
        }

        List<Integer> result = DiceUtil.rollDice(Math.abs(n), d);
        if (k != null) {
            result.sort((o1, o2) -> o2 - o1);
        }
        points = result;

        if(type.equals(Constants.DICE_GROUP_NDX)) {
            int value = 0;
            for(int i = 0; i < Math.abs(n); i++){
                value += points.get(i);
            }
            this.value = n < 0 ? -value : value;
            return;
        }

        if(type.equals(Constants.DICE_GROUP_NDK)) {
            int value = 0;
            for(int i = 0; i < k; i++){
                value += points.get(i);
            }
            this.value = n < 0 ? -value : value;
            return;
        }

        this.value = 0;
    }
}
