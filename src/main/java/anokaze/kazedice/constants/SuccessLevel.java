package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/16
 */
@Getter
public enum SuccessLevel {
    // 成功等级
    CRITICAL("大成功"),
    EXTREME("极难成功"),
    HARD("困难成功"),
    REGULAR("成功"),
    FAIL("失败"),
    FUMBLE("大失败");

    private final String name;

    SuccessLevel(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return "[" + name + "]";
    }
}
