package anokaze.kazedice;

import anokaze.kazedice.command.RollCommand;
import anokaze.kazedice.constants.CommandDocs;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;

/**
 * @author AnoKaze
 */
@Slf4j
public class KazeDicePlugin extends BasePlugin {
    private static KazeDicePlugin instance;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        log.info("KazeDice loaded!");
    }

    @Override
    public void onEnable(){
        log.info("KazeDice connected!");
        createCommand("roll")
                .addAlias("r")
                .addOptionalArgument(String.class, "1d100")
                .executesUser(new RollCommand())
                .setDescription(CommandDocs.ROLL_COMMAND_DOC.getDescription())
                .setHelpContent(CommandDocs.ROLL_COMMAND_DOC.getHelpContent())
                .register(getInstance());

    }

    @Override
    public void onDisable() {
        log.info("KazeDice disconnected!");
    }

    public static KazeDicePlugin getInstance() {
        return instance;
    }

    private JKookCommand createCommand(String rootName){
        return new JKookCommand(rootName).addPrefix(".").addPrefix("。");
    }
}
