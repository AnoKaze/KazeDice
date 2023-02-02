package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * 可控报错的种类
 * @author AnoKaze
 * @date 2023/2/2
 */
@Getter
public enum BotExceptions {
    /**
     * 骰子组打印错误
     */
    DICE_GROUP_PARSE_EXCEPTION("0001", "Parse DiceGroup error");

    private final String code;
    private final String message;

    BotExceptions(String code, String message){
        this.code = code;
        this.message = message;
    }

}
