package anokaze.kazedice.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class DiceGroup {
    private final Integer total;
    private final Integer sides;
    private final List<Integer> points;
    private final Integer value;

    public DiceGroup(Integer total, Integer sides, List<Integer> points){
        this.total = total;
        this.sides = sides;
        this.points = points;
        this.value = initValue();
    }

    @Override
    public String toString() {
        if(total == 0 || sides == 0) { return "0"; }
        if(sides == -1) { return total.toString(); }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < points.size(); i++){
            if(i != 0){ result.append("+"); }
            result.append(points.get(i));
        }

        if(total == 1) { return result.toString(); }
        else { return "(" + result + ")"; }
    }

    private Integer initValue(){
        int value = 0;
        if(total == 0 || sides == 0){
            return 0;
        }
        if(sides == -1){
            return total;
        }

        for(Integer point: points){
            value += point;
        }
        return value;
    }


}
