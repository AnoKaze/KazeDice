package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/14
 */
@Getter
public enum CharacteristicEnum {
    // 角色属性
    CHARACTERISTIC_STR("力量"),
    CHARACTERISTIC_CON("体质"),
    CHARACTERISTIC_SIZ("体型"),
    CHARACTERISTIC_DEX("敏捷"),
    CHARACTERISTIC_APP("外貌"),
    CHARACTERISTIC_INT("智力"),
    CHARACTERISTIC_POW("意志"),
    CHARACTERISTIC_EDU("教育"),
    CHARACTERISTIC_LUCK("幸运"),
    CHARACTERISTIC_KNOW("知识"),
    CHARACTERISTIC_IDEA("灵感");

    private final String name;
    private final Integer defaultValue;

    CharacteristicEnum(String name){
        this.name = name;
        this.defaultValue = 0;
    }
}
