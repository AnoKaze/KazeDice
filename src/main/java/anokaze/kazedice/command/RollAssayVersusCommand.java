package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.SuccessLevel;
import anokaze.kazedice.entity.Assay;
import anokaze.kazedice.entity.RavRecord;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import anokaze.kazedice.util.DiceUtil;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.PrivateMessage;
import snw.jkook.message.TextChannelMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/2/15
 */
public class RollAssayVersusCommand implements UserCommandExecutor {
    private static final Map<String, RavRecord> RECORDS = new HashMap<>();

    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();

        if(message instanceof PrivateMessage){
            message.reply(":x:无法在私聊频道发起对抗！");
            return;
        }

        String userId = user.getId();
        Category category = ((TextChannelMessage) message).getChannel().getParent();
        assert category != null;
        String categoryId = category.getId();

        String attributeName = (String) arguments[0];
        Integer attributeValue = (Integer) arguments[1];

        StringBuilder reply = new StringBuilder();
        RolePojo bindRole = roleService.findBoundRole(categoryId, userId);
        if(attributeValue == -1){
            if(bindRole == null){
                attributeValue = 0;
            }
            else{
                attributeValue = bindRole.getAttribute(attributeName);
                reply.append("[").append(bindRole.getName()).append("]");
            }
        }

        Assay assay = DiceUtil.normalAssay(attributeName, attributeValue);
        if(bindRole != null && assay.getAssayLevel().compareTo(SuccessLevel.REGULAR) <= 0){
            bindRole.addBonus(attributeName);
            roleService.updateRole(bindRole);
        }

        RavRecord record = RECORDS.get(categoryId);
        if(record == null){
            reply.append("发起了一次对抗：\n").append(assay.getAssayString());
            record = new RavRecord();
            record.setOffensive(assay);
            RECORDS.put(categoryId, record);
            message.reply(reply.toString());
        }
        else {
            reply.append("接受了一次对抗：\n").append(assay.getAssayString());
            record.setDefensive(assay);
            message.reply(reply.toString());

            String result = record.getVersusResult();
            RECORDS.remove(categoryId);

            if(result == null){
                message.sendToSource(":x:内部错误，对抗数据缺失！");
                return;
            }
            message.sendToSource(record.getVersusResult());
        }
    }
}
