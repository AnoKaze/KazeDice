package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.entity.expression.SanExpression;
import anokaze.kazedice.service.DiceService;
import anokaze.kazedice.service.RoleService;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

/**
 * @author AnoKaze
 * @since 2023/2/16
 */
public class SanCheckCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        DiceService diceService = KazeDicePlugin.getServiceManager().getDiceService();

        SanExpression expression = (SanExpression) arguments[0];

        String userId = user.getId();
        String categoryId = "private";
        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            categoryId = category.getId();
        }

        RolePojo bindRole = roleService.findBoundRole(categoryId, userId);
        if(bindRole == null){
            message.reply(":x:当前分组未绑定角色！");
            return;
        }

        message.reply(diceService.sanCheck(bindRole, expression));
    }
}
