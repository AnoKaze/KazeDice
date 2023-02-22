package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.RoleBindPojo;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
@Slf4j
public class StateRemoveCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();

        String userId = user.getId();
        String name = (String) arguments[0];

        RolePojo role = roleService.findRoleByName(userId, name);

        if(role == null){
            message.reply(":x:指定角色[" + name + "]不存在，请输入正确的名称！");
            return;
        }

        roleService.deleteRole(role.getId());
        message.reply("已删除角色[" + name + "]。");
    }
}
