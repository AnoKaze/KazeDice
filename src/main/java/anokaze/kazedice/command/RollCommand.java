package anokaze.kazedice.command;

import anokaze.kazedice.entity.DiceExpression;
import anokaze.kazedice.util.DiceUtil;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

public class RollCommand implements UserCommandExecutor {

    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        String origin = (String) arguments[0];
        DiceExpression diceExpression = DiceUtil.DiceExpressionParser(origin);

        if(diceExpression == null){
            message.reply("表达式输入格式不正确，请检查后重新输入！");
            return;
        }
        message.reply(":game_die:掷骰结果：" + diceExpression);
    }

}
