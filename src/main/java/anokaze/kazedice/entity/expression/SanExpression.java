package anokaze.kazedice.entity.expression;

import anokaze.kazedice.entity.expression.DiceGroup;
import anokaze.kazedice.parser.SanExpressionParser;
import lombok.Data;

/**
 * @author AnoKaze
 * @since 2023/2/24
 */
@Data
public class SanExpression {
    private static final SanExpressionParser PARSER = new SanExpressionParser();

    private RollExpression success;
    private RollExpression failed;

    public SanExpression(String s){
        SanExpression template = PARSER.apply(s);
        this.success = template.getSuccess();
        this.failed = template.getFailed();
    }

    public SanExpression(){}

    public void reset(){
        success.reset();
        failed.reset();
    }
}
