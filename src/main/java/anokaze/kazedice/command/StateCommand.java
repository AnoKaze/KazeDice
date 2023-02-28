package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.CharacteristicEnum;
import anokaze.kazedice.constants.SkillEnum;
import anokaze.kazedice.constants.StateEnum;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.entity.expression.StateExpression;
import anokaze.kazedice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

import java.util.Map;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
@Slf4j
public class StateCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        String defaultName = "自定义角色";

        String categoryId = "private";
        String categoryName = "私聊窗口";
        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            categoryId = category.getId();
            categoryName = category.getName();
        }

        StateExpression expression = (StateExpression) arguments[0];

        if(expression.getAttributes() == null){
            RolePojo exist = roleService.findRoleByName(user.getId(), expression.getRoleName());
            if(exist == null){
                message.reply(":x:角色["+ expression.getRoleName() +"]不存在，绑定失败！");
                return;
            }
            roleService.bindRoleToCategory(exist, categoryId);
            message.reply("已将角色[" + expression.getRoleName() + "]与分组[" + categoryName + "]绑定。");
            return;
        }

        RolePojo bound = roleService.findBoundRole(categoryId, user.getId());
        if(expression.getRoleName() == null){
            if(bound != null){
                applyAttributes(bound, expression.getAttributes());
                roleService.updateRole(bound);
                message.reply("已更新角色[" + bound.getName() + "]的信息。");
                return;
            }

            RolePojo exist = roleService.findRoleByName(user.getId(), defaultName);
            if(exist == null){
                exist = new RolePojo(defaultName, user.getId());
                applyAttributes(exist, expression.getAttributes());
                roleService.insertRole(exist);
                roleService.bindRoleToCategory(exist, categoryId);
                message.reply("已创建角色[" + exist.getName() + "]，并与分组[" + categoryName + "]绑定。");
                return;
            }

            applyAttributes(exist, expression.getAttributes());
            roleService.updateRole(exist);
            roleService.bindRoleToCategory(exist, categoryId);
            message.reply("已更新角色[" + exist.getName() + "]的信息，并与分组[" + categoryName + "]绑定。");
        }
        else {
            RolePojo exist = roleService.findRoleByName(user.getId(), expression.getRoleName());
            if(exist == null){
                exist = new RolePojo(expression.getRoleName(), user.getId());
                applyAttributes(exist, expression.getAttributes());
                roleService.insertRole(exist);
                roleService.bindRoleToCategory(exist, categoryId);
                message.reply("已创建角色[" + exist.getName() + "]，并与分组[" + categoryName + "]绑定。");
                return;
            }

            applyAttributes(exist, expression.getAttributes());
            roleService.updateRole(exist);
            if(bound != null && bound.getName().equals(expression.getRoleName())){
                message.reply("已更新角色[" + exist.getName() + "]的信息。");
                return;
            }
            roleService.bindRoleToCategory(exist, categoryId);
            message.reply("已更新角色[" + exist.getName() + "]的信息，并与分组[" + categoryName + "]绑定。");
        }
    }

    private void applyAttributes(RolePojo role, Map<String, Integer> attributes){
        Integer currentEdu = role.getCharacteristics().getOrDefault(CharacteristicEnum.CHARACTERISTIC_EDU.getName(), 0);
        Integer currentDex = role.getCharacteristics().getOrDefault(CharacteristicEnum.CHARACTERISTIC_DEX.getName(), 0);
        Integer currentLanguage = role.getSkills().get(SkillEnum.SKILL_LANGUAGE.getName());
        Integer currentDodge = role.getSkills().get(SkillEnum.SKILL_DODGE.getName());
        int languageBonus = currentLanguage - currentEdu;
        int dodgeBonus = currentDodge - currentDex / 2;

        for(Map.Entry<String, Integer> attribute: attributes.entrySet()){
            String key = attribute.getKey();
            Integer value = attribute.getValue();
            if(role.getCharacteristics().containsKey(key)){
                role.getCharacteristics().put(key, value);
            }
            else {
                role.getSkills().put(key, value);
            }
        }
        Integer con = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_CON.getName());
        Integer siz = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_SIZ.getName());
        Integer dex = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_DEX.getName());
        Integer intA = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_INT.getName());
        Integer pow = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_POW.getName());
        Integer edu = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_EDU.getName());
        Integer luck = role.getCharacteristics().get(CharacteristicEnum.CHARACTERISTIC_LUCK.getName());

        role.getCharacteristics().put(CharacteristicEnum.CHARACTERISTIC_KNOW.getName(), edu);
        role.getCharacteristics().put(CharacteristicEnum.CHARACTERISTIC_IDEA.getName(), intA);

        if(attributes.containsKey(CharacteristicEnum.CHARACTERISTIC_EDU.getName()) &&
                !attributes.containsKey(SkillEnum.SKILL_LANGUAGE.getName())){
            role.getSkills().put(SkillEnum.SKILL_LANGUAGE.getName(), edu + languageBonus);
        }
        if(attributes.containsKey(CharacteristicEnum.CHARACTERISTIC_DEX.getName()) &&
                !attributes.containsKey(SkillEnum.SKILL_DODGE.getName())){
            role.getSkills().put(SkillEnum.SKILL_DODGE.getName(), dex / 2 + dodgeBonus);
        }

        role.getStates().put(StateEnum.HP_CURRENT.getValue(), (con + siz) / 10);
        role.getStates().put(StateEnum.HP_MAX.getValue(), (con + siz) / 10);
        role.getStates().put(StateEnum.SAN_CURRENT.getValue(), pow);
        role.getStates().put(StateEnum.SAN_MAX.getValue(), 99);
        role.getStates().put(StateEnum.LUCK_CURRENT.getValue(), luck);
        role.getStates().put(StateEnum.LUCK_MAX.getValue(), 99);
        role.getStates().put(StateEnum.MP_CURRENT.getValue(), pow / 5);
        role.getStates().put(StateEnum.MP_MAX.getValue(), 24);
    }
}
