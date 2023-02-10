package anokaze.kazedice.command;

import anokaze.kazedice.exception.BotException;
import anokaze.kazedice.entity.DiceExpression;
import anokaze.kazedice.util.DiceUtil;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

/**
 * @author AnoKaze
 * @since 2023/02/01
 */
public class RollCommand implements UserCommandExecutor {

    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        if(message == null){
            return;
        }

        String origin = (String) arguments[0];
        try {
            DiceExpression diceExpression = DiceUtil.diceExpressionParser(origin);
            message.reply(":game_die:掷骰结果：" + diceExpression);
        } catch (BotException e){
            message.reply(":x:参数`" + e.getData() + "`输入错误，请重新输入！");
        }
    }

}
