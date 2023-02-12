package anokaze.kazedice.exception;

import anokaze.kazedice.constants.BotExceptionEnum;

/**
 * Bot在运行中产生的可控报错
 * @author AnoKaze
 * @since 2023/02/02
 */
public class BotException extends RuntimeException {
    private final String code;
    private final String message;
    private final Object data;

    public BotException(BotExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
        this.data = null;
    }

    public BotException(BotExceptionEnum exceptionEnum, Object data){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
        this.data = data;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public Object getData() { return this.data; }
}
