import anokaze.kazedice.entity.DiceExpression;
import anokaze.kazedice.util.DiceUtil;

public class RollCommandTest {
    public static void main(String[] args) {
        String diceString = "50-2d6*5+4d4/2";
        DiceExpression diceExpression = DiceUtil.DiceExpressionParser(diceString);

        if(diceExpression == null){
            System.out.println("格式错误！");
        }
        else{
            System.out.println(diceExpression);
        }
    }
}
