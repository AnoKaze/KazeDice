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

import java.util.Map;
import java.util.Objects;
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
        String defaultName = "自定义角色";
        RoleService roleService = KazeDicePlugin.getServiceManager().getRoleService();

        boolean hasName = !Objects.equals(arguments[1], "");

        String userId = user.getId();
        String name = hasName ? (String)arguments[0] : defaultName;
        String attributeStr = hasName ? (String)arguments[1] : (String)arguments[0];
        if(!Pattern.matches(RegexConstant.STATES_REGEX, attributeStr)){
            message.reply(":x:参数`" + attributeStr + "`输入错误，请重新输入！");
            return;
        }

        StringBuilder reply = new StringBuilder();

        if(message instanceof TextChannelMessage){
            Category category = ((TextChannelMessage) message).getChannel().getParent();
            assert category != null;
            String categoryId = category.getId();

            RolePojo bindRole = roleService.findBindRole(categoryId, userId);
            RolePojo existRole = roleService.findRoleByName(userId, name);
            RolePojo role;

            if(hasName){
                if(existRole == null){
                    role = new RolePojo();
                    role.setName(name);
                    role.setUserId(userId);
                    role.initAttributes();
                    appendAttributes(role.getAttributes(), attributeStr);
                    roleService.insertRole(role);
                    reply.append("已创建新角色[").append(name).append("]");

                    if(bindRole == null){
                        roleService.bindRoleToCategory(role, categoryId);
                        reply.append("，并设置为当前分组的默认角色。");
                    }
                    else{
                        reply.append("。");
                    }
                }
                else{
                    role = existRole;
                    appendAttributes(role.getAttributes(), attributeStr);
                    roleService.updateRole(role.getId(), role);
                    reply.append("已修改角色[").append(role.getName()).append("]的信息。");
                }
            }
            else{
                if(bindRole == null){
                    if(existRole != null) {
                        role = existRole;
                    }
                    else {
                        role = new RolePojo();
                        role.setName(name);
                        role.setUserId(userId);
                        role.initAttributes();
                    }
                    appendAttributes(role.getAttributes(), attributeStr);
                    roleService.insertRole(role);
                    roleService.bindRoleToCategory(role, categoryId);
                    reply.append("已创建新角色[").append(name).append("]，并设置为当前分组的默认角色。");
                }
                else{
                    role = bindRole;
                    appendAttributes(role.getAttributes(), attributeStr);
                    roleService.updateRole(role.getId(), role);
                    reply.append("已修改角色[").append(role.getName()).append("]的信息。");
                }
            }
        }
        else{
            RolePojo existRole = roleService.findRoleByName(userId, name);
            RolePojo role;

            if(hasName){
                if(existRole == null) {
                    role = new RolePojo();
                    role.setName(name);
                    role.setUserId(userId);
                    role.initAttributes();
                    appendAttributes(role.getAttributes(), attributeStr);
                    roleService.insertRole(role);
                    reply.append("已创建新角色[").append(name).append("]。");
                }
                else {
                    role = existRole;
                    appendAttributes(role.getAttributes(), attributeStr);
                    roleService.updateRole(role.getId(), role);
                    reply.append("已修改角色[").append(role.getName()).append("]的信息。");
                }
            }
            else{
                if(existRole != null) {
                    role = existRole;
                }
                else {
                    role = new RolePojo();
                    role.setName(name);
                    role.setUserId(userId);
                    role.initAttributes();
                }
                appendAttributes(role.getAttributes(), attributeStr);
                roleService.insertRole(role);
                reply.append("已创建新角色[").append(name).append("]，并设置为当前分组的默认角色。");
            }
        }

        message.reply(reply.toString());
    }

    private void appendAttributes(Map<String, Integer> attributes, String attributeStr){
        Matcher m = Pattern.compile(RegexConstant.STATE_REGEX).matcher(attributeStr);
        while(m.find()){
            attributes.put(m.group(1), Integer.parseInt(m.group(2)));
        }
        Integer dexterity = attributes.get("敏捷");
        Integer intelligence = attributes.get("智力");
        Integer education = attributes.get("教育");

        attributes.put("闪避", dexterity / 2);
        attributes.put("灵感", intelligence);
        attributes.put("知识", education);
        attributes.put("母语", education);
    }
}
