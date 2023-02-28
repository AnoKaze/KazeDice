package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import anokaze.kazedice.util.DiceUtil;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.CardScopeElement;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.BaseElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.DividerModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;
import snw.jkook.message.component.card.structure.Paragraph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/27
 */
public class TeamEnhanceCommand implements UserCommandExecutor {
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
                .addModule(new HeaderModule(new PlainTextElement("幕间成长")))
                .addModule(DividerModule.INSTANCE);

        for(RolePojo role: roles){
            cardBuilder.addModule(new HeaderModule(new PlainTextElement(role.getName() + "：")))
                       .addModule(new SectionModule(getEnhanceParagraph(role), null, null));
            roleService.updateRole(role);
        }

        message.reply(cardBuilder.build());
    }

    private CardScopeElement getEnhanceParagraph(RolePojo role){
        if(role.getBonus().isEmpty()){
            return new MarkdownElement("无可成长的技能");
        }

        StringBuilder name = new StringBuilder("**技能名称**\n");
        StringBuilder roll = new StringBuilder("**掷骰结果**\n");
        StringBuilder result = new StringBuilder("**最终数值**\n");

        int paragraphColumns = 3;

        for(String skill: role.getBonus()){
            name.append(skill).append("\n");

            int d100 = DiceUtil.rollDie(100);
            int current = role.getAttribute(skill);
            roll.append(d100).append(" / ").append(current);

            int after = current;
            if(d100 > 95 || d100 > current){
                int d10 = DiceUtil.rollDie(10);
                after = current + d10;
                role.getSkills().put(skill, after);
                roll.append("[成功]\n");
            }
            else{
                roll.append("[失败]\n");
            }
            result.append(current).append(" -> ").append(after).append("\n");
        }

        role.getBonus().clear();

        List<BaseElement> columnElements = new ArrayList<>(paragraphColumns);
        columnElements.add(new MarkdownElement(name.toString()));
        columnElements.add(new MarkdownElement(roll.toString()));
        columnElements.add(new MarkdownElement(result.toString()));

        return new Paragraph(paragraphColumns, columnElements);
    }
}
