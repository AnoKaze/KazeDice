package anokaze.kazedice.entity;

import anokaze.kazedice.util.DiceUtil;
import lombok.Getter;

import java.util.List;

@Getter
public class DiceGroup {
    private final Integer n;
    private final Integer d;
    private final Integer k;
    private final List<Integer> points;

    public DiceGroup(Integer n, Integer d, Integer k){
        this.n = n;
        this.d = d;
        this.k = k;
        this.points = initPoints();
    }

    public Integer getValue(){
        assert n != null;
        // nxx
        if(d == null) {
            return n;
        }
        else{
            int value = 0;
            int number = Math.abs(n);
            // ndx
            if(k == null){
                for(int i = 0; i < number; i++){
                    value += points.get(i);
                }
            }
            // ndk
            else {
                for(int i = 0; i < k; i++){
                    value += points.get(i);
                }
            }
            return n < 0 ? -value : value;
        }
    }

    public String getExpression(){
        StringBuilder result = new StringBuilder();
        result.append(n);
        if (d != null) {
            result.append("d").append(d);
            if (k != null) {
                result.append("k").append(k);
            }
        }
        return result.toString();
    }

    public String getFormula() {
        if(n == 0){
            return "0";
        }

        StringBuilder result = new StringBuilder();
        if(d == null){
            result.append(n);
        }
        else{
            int nAbs = Math.abs(n);
            if(n < 0){
                result.append("-");
            }

            int number = k == null ? nAbs : k;
            if(number == 1){
                result.append(points.get(0));
            }
            else {
                result.append("(");
                for(int i = 0; i < number; i++){
                    if(i != 0){
                        result.append("+");
                    }
                    result.append(points.get(i));
                }
                result.append(")");
            }

            if(k != null){
                result.append("[");
                for(int i = 0; i < nAbs; i++){
                    if(i != 0){
                        result.append(",");
                    }
                    result.append(points.get(i));
                }
                result.append("]");
            }
        }
        return result.toString();
    }

    private List<Integer> initPoints(){
        if(d == null){
            return null;
        }
        int nAbs = Math.abs(n);
        List<Integer> result = DiceUtil.rollDice(nAbs, d);
        if (k != null) {
            result.sort((o1, o2) -> o2 - o1);
        }
        return result;
    }
}
