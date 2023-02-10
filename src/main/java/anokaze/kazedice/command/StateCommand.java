package anokaze.kazedice.command;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.entity.Role;
import anokaze.kazedice.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

import java.util.Map;
import java.util.Objects;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
@Slf4j
public class StateCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        String defaultName = "自定义角色";
        RoleMapper roleMapper = KazeDicePlugin.getMapperManager().getRoleMapper();

        boolean hasName = Objects.equals(arguments[1], "");
        String name = hasName ? (String)arguments[0] : defaultName;
        String stateStr = hasName ? (String)arguments[1] : (String)arguments[0];
    }
}
