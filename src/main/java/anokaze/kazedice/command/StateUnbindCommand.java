package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class StateUnbindCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        String userId = user.getId();
        String categoryId = "private";
        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            categoryId = category.getId();
        }

        RolePojo role = roleService.findBoundRole(categoryId, userId);
        if(role == null){
            message.reply("当前分组未绑定角色。");
            return;
        }

        roleService.unbindRoleFromCategory(role, categoryId);
        message.reply("已将角色[" + role.getName() + "]与当前分组解绑。");
    }
}
