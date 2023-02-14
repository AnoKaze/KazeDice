package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/14
 */
@Getter
public enum CharacteristicEnum {
    // 角色属性
    CHARACTERISTIC_STR("力量", 0),
    CHARACTERISTIC_CON("体质", 0),
    CHARACTERISTIC_SIZ("体型", 0),
    CHARACTERISTIC_DEX("敏捷", 0),
    CHARACTERISTIC_APP("外貌", 0),
    CHARACTERISTIC_INT("智力", 0),
    CHARACTERISTIC_POW("意志", 0),
    CHARACTERISTIC_EDU("教育", 0),
    CHARACTERISTIC_LUCK("幸运", 0),
    CHARACTERISTIC_KNOW("知识", 0),
    CHARACTERISTIC_IDEA("灵感", 0);

    private final String name;
    private final Integer defaultValue;

    CharacteristicEnum(String name, Integer defaultValue){
        this.name = name;
        this.defaultValue = defaultValue;
    }
}
