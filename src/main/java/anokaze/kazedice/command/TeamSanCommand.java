package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.StateEnum;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.entity.expression.RollExpression;
import anokaze.kazedice.service.RoleService;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/24
 */
public class TeamSanCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();

        String here = "here";
        String all = "all";

        if(!(message instanceof TextChannelMessage)){
            message.reply(":x:team及其子指令只能在文字频道中使用！");
            return;
        }

        Category category = ((TextChannelMessage) message).getChannel().getParent();
        assert category != null;

        User targetUser = (User) arguments[0];
        RollExpression expression = (RollExpression) arguments[1];

        StringBuilder reply = new StringBuilder();
        if(all.equals(targetUser.getId()) || here.equals(targetUser.getId())){
            List<RolePojo> roles = roleService.findCategoryRoles(category.getId());
            for(RolePojo role: roles){
                Integer sanCurrent = role.getAttribute(StateEnum.SAN_CURRENT.getValue());
                Integer sanMax = role.getAttribute(StateEnum.SAN_MAX.getValue());
                expression.reset();
                Integer sanAfter = sanCurrent + expression.getValue() > sanMax ? sanMax : sanCurrent + expression.getValue();
                role.getStates().put(StateEnum.HP_CURRENT.getValue(), sanAfter);
                roleService.updateRole(role);
                reply.append("角色[").append(role.getName()).append("]的理智变化为").append(sanAfter).append(" / ").append(sanMax)
                     .append("(掷骰结果：").append(expression).append(")。\n");
            }
            message.reply(reply.toString());
            return;
        }

        RolePojo role = roleService.findBoundRole(category.getId(), targetUser.getId());
        if(role == null){
            message.reply(":x:目标用户未绑定角色！");
            return;
        }
        Integer sanCurrent = role.getAttribute(StateEnum.SAN_CURRENT.getValue());
        Integer sanMax = role.getAttribute(StateEnum.SAN_MAX.getValue());
        expression.reset();
        Integer sanAfter = sanCurrent + expression.getValue() > sanMax ? sanMax : sanCurrent + expression.getValue();
        role.getStates().put(StateEnum.HP_CURRENT.getValue(), sanAfter);
        roleService.updateRole(role);
        reply.append("角色[").append(role.getName()).append("]的理智变化为").append(sanAfter).append(" / ").append(sanMax)
             .append("(掷骰结果：").append(expression).append(")。");
        message.reply(reply.toString());
    }
}
