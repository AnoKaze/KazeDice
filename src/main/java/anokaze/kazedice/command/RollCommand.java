package anokaze.kazedice.command;

import anokaze.kazedice.entity.expression.RollExpression;
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
        if(message == null){ return; }

        RollExpression expression = (RollExpression) arguments[0];
        expression.reset();

        message.reply(":game_die:掷骰结果：" + expression + "。" );
    }

}
