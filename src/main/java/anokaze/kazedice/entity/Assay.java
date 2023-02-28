package anokaze.kazedice.entity;

import anokaze.kazedice.constants.Constants;
import anokaze.kazedice.constants.SuccessLevel;
import lombok.Data;

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/16
 */
@Data
public class Assay {
    private String attributeName;
    private Integer attributeValue;
    private Byte type;
    private List<Integer> tens;
    private Integer point;

    public String getAssayString(){
        StringBuilder result = new StringBuilder();
        result.append("进行了[").append(attributeName).append("]检定：")
              .append(getAssayLevel().toString()).append(" D100 = ");

        if(type.equals(Constants.ASSAY_NORMAL)){
            result.append(point).append(" / ").append(attributeValue);
        }
        if(type.equals(Constants.ASSAY_BONUS)){
            result.append(point).append("[奖励骰：");
            for(int i = 0; i < tens.size(); i++){
                if(i != 0){
                    result.append(", ");
                }
                result.append(tens.get(i));
            }
            result.append("] / ").append(attributeValue);
        }
        if(type.equals(Constants.ASSAY_PUNISH)){
            result.append(point).append("[惩罚骰：");
            for(int i = 0; i < tens.size(); i++){
                if(i != 0){
                    result.append(", ");
                }
                result.append(tens.get(i));
            }
            result.append("] / ").append(attributeValue);
        }
        return result.toString();
    }

    public SuccessLevel getAssayLevel(){
        int critical = 5;
        int fumble = 96;

        if(point <= critical) {
            if(point <= attributeValue){ return SuccessLevel.CRITICAL; }
            else{ return SuccessLevel.FAIL; }
        }
        if(point >= fumble) {
            if(point > attributeValue){ return SuccessLevel.FUMBLE; }
            else { return SuccessLevel.REGULAR; }
        }
        if(point <= attributeValue / 5){
            return SuccessLevel.EXTREME;
        }
        if(point <= attributeValue / 2){
            return SuccessLevel.HARD;
        }
        if(point <= attributeValue){
            return SuccessLevel.REGULAR;
        }
        return SuccessLevel.FAIL;
    }
}
