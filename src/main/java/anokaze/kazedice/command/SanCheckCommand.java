package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.SuccessLevel;
import anokaze.kazedice.entity.Assay;
import anokaze.kazedice.entity.DiceExpression;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.exception.BotException;
import anokaze.kazedice.service.RoleService;
import anokaze.kazedice.util.DiceUtil;
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
        String sanKey = "理智";
        String sanMaxKey = "最大理智";

        String arg0 = (String) arguments[0];
        if(!arg0.contains("/")){
            message.reply(":x:参数`" + arg0 + "`输入错误，请重新输入！");
            return;
        }

        String[] splits = arg0.split("/");
        if(splits.length != 2 || "".equals(splits[0]) || "".equals(splits[1])){
            message.reply(":x:参数`" + arg0 + "`输入错误，请重新输入！");
            return;
        }

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

        StringBuilder reply = new StringBuilder();
        reply.append("[").append(bindRole.getName()).append("]");

        Integer currentSan = bindRole.getAttribute(sanKey);
        Integer maxSan = bindRole.getAttribute(sanMaxKey);
        Assay assay = new Assay(sanKey, currentSan);
        reply.append(assay.getAssayString()).append("\n");

        try {
            DiceExpression success = DiceUtil.diceExpressionParser(splits[0]);
            DiceExpression fail = DiceUtil.diceExpressionParser(splits[1]);

            DiceExpression chosen = assay.getAssayLevel().compareTo(SuccessLevel.REGULAR) <= 0 ? success : fail;

            int afterSan = currentSan - chosen.getValue();
            bindRole.getStates().put(sanKey, afterSan);
            roleService.updateRole(bindRole);
            reply.append("损失理智：").append(chosen).append("，当前剩余理智：")
                 .append(afterSan).append(" / ").append(maxSan).append("\n");

            if(afterSan <= 0){
                reply.append("陷入永久疯狂。");
            }
            else if(chosen.getValue() >= 5) {
                reply.append("陷入临时疯狂。");
            }

            message.reply(reply.toString());
        } catch (BotException e) {
            message.reply(":x:参数`" + e.getData() + "`输入错误，请重新输入！");
        }
    }
}
