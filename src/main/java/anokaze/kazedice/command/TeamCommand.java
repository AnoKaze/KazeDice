package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.StateEnum;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import anokaze.kazedice.util.MessageUtil;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.DividerModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class TeamCommand implements UserCommandExecutor {
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

        CardBuilder cardBuilder = new CardBuilder()
                .setTheme(Theme.INFO)
                .setSize(Size.LG)
                .addModule(new HeaderModule(new PlainTextElement("[" + category.getName() + "]队伍信息：")));

        for(RolePojo role: roles){
            StringBuilder roleStates = new StringBuilder();

            Integer hpCurrent = role.getStates().get(StateEnum.HP_CURRENT.getValue());
            Integer hpMax = role.getStates().get(StateEnum.HP_MAX.getValue());
            Integer sanCurrent = role.getStates().get(StateEnum.SAN_CURRENT.getValue());
            Integer sanMax = role.getStates().get(StateEnum.SAN_MAX.getValue());

            roleStates.append("**").append(role.getName()).append("**").append(" :\n");
            roleStates.append("伤害加值：").append(role.getDamageBonus()).append("\n");
            roleStates.append("生命：").append(getHpProgressBar(hpCurrent, hpMax)).append("\n");
            roleStates.append("理智：").append(getSanProgressBar(sanCurrent, sanMax));

            cardBuilder.addModule(DividerModule.INSTANCE);
            cardBuilder.addModule(new SectionModule(new MarkdownElement(roleStates.toString()), null, null));
        }

        message.reply(cardBuilder.build());
    }

    private String getHpProgressBar(Integer hpCurrent, Integer hpMax){
        return MessageUtil.getProgressBar(hpCurrent, hpMax, ":red_square:", ":white_large_square:");
    }

    private String getSanProgressBar(Integer sanCurrent, Integer sanMax) {
        return MessageUtil.getProgressBar(sanCurrent, sanMax, ":purple_square:", ":white_large_square:");
    }
}
