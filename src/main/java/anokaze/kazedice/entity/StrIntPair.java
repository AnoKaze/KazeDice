package anokaze.kazedice.entity;

import lombok.Data;

/**
 * @author AnoKaze
 * @since 2023/2/14
 */
@Data
public class StrIntPair {
    private String string;
    private Integer integer;

    public StrIntPair(String string, Integer integer){
        this.string = string;
        this.integer = integer;
    }
}
