import anokaze.kazedice.entity.DiceExpression;
import anokaze.kazedice.util.DiceUtil;

public class RollCommandTest {
    public static void main(String[] args) {
        String diceString = "3d6k2+-2d10k1+3d6/2*3";
        DiceExpression diceExpression = DiceUtil.diceExpressionParser(diceString);

        if(diceExpression == null){
            System.out.println("格式错误！");
        }
        else{
            System.out.println(diceExpression);
        }
    }
}
