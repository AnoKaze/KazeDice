package anokaze.kazedice.entity;

import anokaze.kazedice.constants.SuccessLevel;
import anokaze.kazedice.util.DiceUtil;
import lombok.Data;

/**
 * @author AnoKaze
 * @since 2023/2/16
 */
@Data
public class Assay {
    private String attributeName;
    private Integer attributeValue;
    private Integer points;

    public Assay(String attributeName, Integer attributeValue){
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.points = DiceUtil.rollDie(100);
    }

    public String getAssayString(){
        return "进行了[" + attributeName + "]检定：" + getAssayLevel().toString() +
                " D100 = " + points + " / " + attributeValue;
    }

    public SuccessLevel getAssayLevel(){
        int critical = 5;
        int fumble = 96;

        if(points <= critical) {
            if(points <= attributeValue){ return SuccessLevel.CRITICAL; }
            else{ return SuccessLevel.FAIL; }
        }
        if(points >= fumble) {
            if(points > attributeValue){ return SuccessLevel.FUMBLE; }
            else { return SuccessLevel.REGULAR; }
        }
        if(points <= attributeValue / 5){
            return SuccessLevel.EXTREME;
        }
        if(points <= attributeValue / 2){
            return SuccessLevel.HARD;
        }
        if(points <= attributeValue){
            return SuccessLevel.REGULAR;
        }
        return SuccessLevel.FAIL;
    }
}
