package anokaze.kazedice.entity;

import anokaze.kazedice.constants.ResultCodeEnum;
import lombok.Data;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
@Data
public class CommonResult {
    private String code;
    private String message;
    private Object data;

    public CommonResult(ResultCodeEnum resultCode, Object data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static CommonResult success(){
        return new CommonResult(ResultCodeEnum.SUCCESS, null);
    }

    public static CommonResult success(Object data){
        return new CommonResult(ResultCodeEnum.SUCCESS, data);
    }

    public static CommonResult failed(ResultCodeEnum resultCode){
        return new CommonResult(resultCode, null);
    }

    public static CommonResult failed(ResultCodeEnum resultCode, Object data){
        return new CommonResult(resultCode, data);
    }

    public boolean isSuccess(){
        return code.equals(ResultCodeEnum.SUCCESS.getCode());
    }
}
