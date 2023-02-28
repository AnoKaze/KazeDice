package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
@Getter
public enum StateEnum {
    // 状态枚举
    HP_CURRENT("生命"),
    HP_MAX("最大生命"),
    SAN_CURRENT("理智"),
    SAN_MAX("最大理智"),
    LUCK_CURRENT("幸运"),
    LUCK_MAX("最大幸运"),
    MP_CURRENT("魔法"),
    MP_MAX("最大魔法");

    private final String value;
    StateEnum(String value){
        this.value = value;
    }
}
