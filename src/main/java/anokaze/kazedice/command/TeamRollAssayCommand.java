package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.Assay;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import anokaze.kazedice.util.DiceUtil;
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
public class TeamRollAssayCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();

        if(!(message instanceof TextChannelMessage)){
            message.reply(":x:team及其子指令只能在文字频道中使用！");
            return;
        }

        Category category = ((TextChannelMessage) message).getChannel().getParent();
        assert category != null;

        List<RolePojo> roles = roleService.findCategoryRoles(category.getId());
        if(roles.isEmpty()){
            message.reply(":x:当前分组未绑定角色！");
            return;
        }

        String attributeName = (String) arguments[0];
        StringBuilder reply = new StringBuilder();
        for(RolePojo role: roles){
            Assay assay = DiceUtil.normalAssay(attributeName, role.getAttribute(attributeName));
            reply.append(assay.getAssayString()).append("\n");
        }
        message.reply(reply.toString());
    }
}
