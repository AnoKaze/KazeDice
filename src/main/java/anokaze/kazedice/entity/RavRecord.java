package anokaze.kazedice.entity;

import lombok.Data;

/**
 * @author AnoKaze
 * @since 2023/2/15
 */
@Data
public class RavRecord {
    private Assay offensive;
    private Assay defensive;

    public String getVersusResult(){
        if(offensive == null || defensive == null){
            return null;
        }

        if(offensive.getAssayLevel().compareTo(defensive.getAssayLevel()) < 0){
            return "先手胜利";
        }
        if(offensive.getAssayLevel().compareTo(defensive.getAssayLevel()) == 0){
            if(offensive.getAttributeValue() > defensive.getAttributeValue()){
                return "先手胜利";
            }

            if(offensive.getAttributeValue().equals(defensive.getAttributeValue())){
                return "平手";
            }
        }
        return "后手胜利";
    }
}
