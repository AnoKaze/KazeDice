package anokaze.kazedice.parser;

import anokaze.kazedice.entity.expression.DiceGroup;
import anokaze.kazedice.entity.expression.RollExpression;
import anokaze.kazedice.entity.expression.SanExpression;

import java.util.function.Function;

/**
 * @author AnoKaze
 * @since 2023/2/24
 */
public class SanExpressionParser implements Function<String, SanExpression> {
    @Override
    public SanExpression apply(String s) {
        RollExpressionParser parser = new RollExpressionParser();
        int sanCheckPart = 2;

        String[] splits = s.split("/");
        if(splits.length != sanCheckPart){
            return null;
        }

        RollExpression success = parser.apply(splits[0]);
        RollExpression failed = parser.apply(splits[1]);

        if(success == null || failed == null){
            return null;
        }

        SanExpression se = new SanExpression();
        se.setSuccess(success);
        se.setFailed(failed);

        return se;
    }
}
