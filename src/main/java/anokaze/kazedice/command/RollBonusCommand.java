package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.SuccessLevel;
import anokaze.kazedice.entity.Assay;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import anokaze.kazedice.util.DiceUtil;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

/**
 * @author AnoKaze
 * @since 2023/2/24
 */
public class RollBonusCommand implements UserCommandExecutor {
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

        Integer bonus = (Integer) arguments[0];
        String attributeName = (String) arguments[1];
        Integer attributeValue = (Integer) arguments[2];

        StringBuilder reply = new StringBuilder();
        RolePojo bindRole = null;
        if(attributeValue == -1){
            bindRole = roleService.findBoundRole(categoryId, userId);
            if(bindRole == null){
                attributeValue = 0;
            }
            else{
                attributeValue = bindRole.getAttribute(attributeName);
                reply.append("[").append(bindRole.getName()).append("]");
            }
        }

        Assay assay = DiceUtil.bonusAssay(attributeName, attributeValue, bonus);
        if(bindRole != null && assay.getAssayLevel().compareTo(SuccessLevel.REGULAR) <= 0){
            bindRole.addBonus(attributeName);
            roleService.updateRole(bindRole);
        }
        reply.append(assay.getAssayString());

        message.reply(reply.toString());
    }
}
