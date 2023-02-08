import anokaze.kazedice.exception.BotException;
import anokaze.kazedice.entity.DiceExpression;
import anokaze.kazedice.util.DiceUtil;

public class RollCommandTest {
    public static void main(String[] args) {
        String diceString = "3d6k2+-2d10k1+3d6/2*3";
        try {
            DiceExpression diceExpression = DiceUtil.diceExpressionParser(diceString);
            System.out.println(diceExpression);
        } catch (BotException e) {
            System.out.println(":x:参数`" + e.getData() + "`输入错误，请重新输入！");
        }
    }
}
