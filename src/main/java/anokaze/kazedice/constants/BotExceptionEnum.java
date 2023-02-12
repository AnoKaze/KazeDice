package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * 可控报错的种类
 * @author AnoKaze
 * @since 2023/02/02
 */
@Getter
public enum BotExceptionEnum {
    // 骰子组打印错误
    DICE_GROUP_PARSE_EXCEPTION("0001", "Parse DiceGroup error");

    private final String code;
    private final String message;

    BotExceptionEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

}
