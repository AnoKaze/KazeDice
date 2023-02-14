package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/14
 */
public class StateListCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        StringBuilder reply = new StringBuilder();

        String userId = user.getId();
        String categoryId = "private";
        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            categoryId = category.getId();
        }

        List<RolePojo> roles = roleService.findUserRoles(userId);
        RolePojo bindRole = roleService.findBoundRole(categoryId, userId);
        roles.removeIf(role -> role.equals(bindRole));

        reply.append("当前分组角色：\n");
        if(bindRole == null){ reply.append("无\n"); }
        else{ reply.append(bindRole.getName()).append("\n"); }

        reply.append("其余可用角色：\n");
        if(roles.isEmpty()){
            reply.append("无");
        }

        for(int i = 0; i < roles.size(); i++){
            reply.append(roles.get(i).getName());
            if(i != roles.size() - 1){
                reply.append("\n");
            }
        }

        message.reply(reply.toString());
    }
}
