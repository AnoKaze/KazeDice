package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.constants.RegexConstant;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.Category;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
@Slf4j
public class StateCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();
        String userId = user.getId();
        String categoryId = "private";
        String categoryName = "私聊窗口";
        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            categoryId = category.getId();
            categoryName = category.getName();
        }
        String arg0 = (String) arguments[0];
        String arg1 = (String) arguments[1];

        boolean isSingleArg = "".equals(arg1);
        if(isSingleArg && !Pattern.matches(RegexConstant.STATES_REGEX, arg0)){
            RolePojo exist = roleService.findRoleByName(userId, arg0);
            if(exist == null){
                message.reply(":x:角色["+ arg0 +"]不存在，绑定失败！");
                return;
            }
            roleService.bindRoleToCategory(exist, categoryId);
            message.reply("已将角色[" + arg0 + "]与分组[" + categoryName + "]绑定。");
            return;
        }
        if(!isSingleArg && !Pattern.matches(RegexConstant.STATES_REGEX, arg1)){
            message.reply(":x:参数`" + arg1 + "`输入错误，请重新输入！");
            return;
        }
        String name = isSingleArg ? "自定义角色" : arg0;
        String attributes = isSingleArg ? arg0 : arg1;

        RolePojo exist = roleService.findRoleByName(userId, name);
        RolePojo bound = roleService.findBoundRole(categoryId, userId);
        if(isSingleArg){
            if(bound != null){
                applyAttributes(bound, attributes);
                roleService.updateRole(bound);
                message.reply("已修改角色[" + bound.getName() + "]的数据。");
                return;
            }
            if(exist != null){
                applyAttributes(exist, attributes);
                roleService.updateRole(exist);
                roleService.bindRoleToCategory(exist, categoryId);
                message.reply("已修改角色[" + exist.getName() + "]的数据, 并与分组[" + categoryName +"]绑定。");
                return;
            }
            RolePojo role = new RolePojo(name, userId);
            applyAttributes(role, attributes);
            roleService.insertRole(role);
            roleService.bindRoleToCategory(role, categoryId);
            message.reply("已新建角色[" + role.getName() + "]的数据, 并与分组[" + categoryName +"]绑定。");
        }
        else{
            if(exist != null){
                applyAttributes(exist, attributes);
                roleService.updateRole(exist);
                if(bound == null){
                    roleService.bindRoleToCategory(exist, categoryId);
                    message.reply("已修改角色[" + exist.getName() + "]的数据, 并与分组[" + categoryName +"]绑定。");
                    return;
                }
                message.reply("已修改角色[" + exist.getName() + "]的数据。");
            }
            else{
                RolePojo role = new RolePojo(name, userId);
                applyAttributes(role, attributes);
                roleService.insertRole(role);
                if(bound == null){
                    roleService.bindRoleToCategory(role, categoryId);
                    message.reply("已新建角色[" + role.getName() + "]的数据, 并与分组[" + categoryName +"]绑定。");
                    return;
                }
                message.reply("已新建角色[" + role.getName() + "]的数据。");
            }
        }
    }

    private void applyAttributes(RolePojo role, String attributes){
        Matcher m = Pattern.compile(RegexConstant.STATE_REGEX).matcher(attributes);
        while(m.find()){
            String key = m.group(1);
            Integer value = Integer.parseInt(m.group(2));
            if(role.getCharacteristics().containsKey(key)){
                role.getCharacteristics().put(key, value);
            }
            else {
                role.getSkills().put(key, value);
            }
        }
        Integer con = role.getCharacteristics().get("体质");
        Integer siz = role.getCharacteristics().get("体型");
        Integer dex = role.getCharacteristics().get("敏捷");
        Integer intA = role.getCharacteristics().get("智力");
        Integer pow = role.getCharacteristics().get("意志");
        Integer edu = role.getCharacteristics().get("教育");
        Integer luck = role.getCharacteristics().get("幸运");

        role.getCharacteristics().put("知识", edu);
        role.getCharacteristics().put("灵感", intA);

        role.getSkills().put("闪避", dex / 2);
        role.getSkills().put("母语", edu);

        role.getStates().put("生命", con + siz);
        role.getStates().put("最大生命", con + siz);
        role.getStates().put("理智", pow);
        role.getStates().put("最大理智", 99);
        role.getStates().put("幸运", luck);
        role.getStates().put("最大幸运", 99);
        role.getStates().put("魔法", pow / 5);
        role.getStates().put("最大魔法", 24);
    }
}
