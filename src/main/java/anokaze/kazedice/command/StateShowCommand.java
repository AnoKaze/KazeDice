package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.CharacteristicEnum;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
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
import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/2/14
 */
public class StateShowCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        int paragraphColumns = 3;

        String userId = user.getId();
        String categoryId = "private";
        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            categoryId = category.getId();
        }

        String arg0 = (String)arguments[0];
        String attribute = "".equals(arg0) ? null : arg0;

        RolePojo role = roleService.findBoundRole(categoryId, userId);
        if(role == null){
            message.reply(":x:当前分组未绑定角色，请绑定后再进行修改！");
            return;
        }

        if(attribute != null){
            message.reply(attribute + " : " + role.getAttribute(attribute));
            return;
        }

        String name = role.getName();
        StringBuilder characteristic = new StringBuilder();
        int counter = 0;
        CharacteristicEnum[] characteristics = CharacteristicEnum.values();
        for(CharacteristicEnum item: characteristics){
            String key = item.getName();
            if("灵感".equals(key) || "知识".equals(key)) {
                continue;
            }
            characteristic.append(key).append("：").append(role.getCharacteristics().get(key)).append("\t");
            counter += 1;
            if(counter % 5 == 0){ characteristic.append("\n"); }
        }

        List<StringBuilder> skills = new ArrayList<>(paragraphColumns);
        for(counter = 0; counter < paragraphColumns; counter++){
            skills.add(new StringBuilder());
        }

        List<Map.Entry<String, Integer>> sortedSkills = new ArrayList<>(role.getSkills().entrySet());
        sortedSkills.sort((skill1, skill2) -> skill2.getValue() - skill1.getValue());

        counter = 0;
        for(Map.Entry<String, Integer> pair: sortedSkills){
            skills.get(counter).append(pair.getKey()).append("：").append(pair.getValue()).append("\n");
            counter += 1;
            if(counter % paragraphColumns == 0){ counter = 0; }
        }

        List<BaseElement> columnElements = new ArrayList<>(paragraphColumns);
        for(counter = 0; counter < paragraphColumns; counter++){
            StringBuilder skill = skills.get(counter);
            skill.deleteCharAt(skill.length() - 1);
            columnElements.add(new MarkdownElement(skill.toString()));
        }

        MultipleCardComponent card = new CardBuilder()
                .setTheme(Theme.INFO)
                .setSize(Size.LG)
                .addModule(new HeaderModule(new PlainTextElement(name)))
                .addModule(DividerModule.INSTANCE)
                .addModule(new SectionModule(new PlainTextElement(characteristic.toString()), null, null))
                .addModule(DividerModule.INSTANCE)
                .addModule(new SectionModule(new Paragraph(paragraphColumns, columnElements), null, null))
                .build();

        message.reply(card);
    }
}
