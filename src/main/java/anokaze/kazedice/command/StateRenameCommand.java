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
 * @since 2023/2/14
 */
public class StateRenameCommand implements UserCommandExecutor {
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
        String name = (String)arguments[0];

        RolePojo role = roleService.findBoundRole(categoryId, userId);
        if(role == null){
            message.reply(":x:当前分组未绑定角色，请绑定后再进行修改！");
            return;
        }

        String before = role.getName();
        role.setName(name);
        roleService.updateRole(role);
        message.reply("已将角色[" + before + "]的名称修改为[" + name + "]。");
    }
}
