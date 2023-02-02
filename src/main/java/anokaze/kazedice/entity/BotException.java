package anokaze.kazedice.entity;

import anokaze.kazedice.constants.BotExceptions;

/**
 * Bot在运行中产生的可控报错
 * @author AnoKaze
 * @date 2023/2/2
 */
public class BotException extends RuntimeException {
    private final String code;
    private final String message;
    private final Object data;

    public BotException(BotExceptions exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
        this.data = null;
    }

    public BotException(BotExceptions exceptionEnum, Object data){
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
