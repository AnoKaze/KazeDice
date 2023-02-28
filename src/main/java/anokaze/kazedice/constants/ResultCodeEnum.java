package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * 可控报错的种类
 * @author AnoKaze
 * @since 2023/02/02
 */
@Getter
public enum ResultCodeEnum {
    // 成功处理
    SUCCESS("00000", "success"),
    // 骰子组打印错误
    DICE_GROUP_PARSE_ERROR("10001", "打印骰子组错误");

    private final String code;
    private final String message;

    ResultCodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

}
