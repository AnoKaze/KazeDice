package anokaze.kazedice.command;

import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

/**
 * @author AnoKaze
 * @since 2023/02/10
 */
@Slf4j
public class StateRemoveCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User user, Object[] arguments, Message message) {
        log.info("sub command!");
    }
}
