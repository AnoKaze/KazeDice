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

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class TeamSanCheckCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        DiceService diceService = KazeDicePlugin.getServiceManager().getDiceService();

        if(!(message instanceof TextChannelMessage)){
            message.reply(":x:team及其子指令只能在文字频道中使用！");
            return;
        }

        Category category = ((TextChannelMessage) message).getChannel().getParent();
        assert category != null;

        SanExpression expression = (SanExpression) arguments[0];

        List<RolePojo> roles = roleService.findCategoryRoles(category.getId());
        if(roles.isEmpty()){
            message.reply(":x:当前分组未绑定角色！");
            return;
        }

        StringBuilder reply = new StringBuilder();

        for(RolePojo role: roles){
            reply.append(diceService.sanCheck(role, expression)).append("\n");
        }

        message.reply(reply.toString());
    }
}
