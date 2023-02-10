package anokaze.kazedice;

import anokaze.kazedice.command.RollCommand;
import anokaze.kazedice.command.StateCommand;
import anokaze.kazedice.command.StateRemoveCommand;
import anokaze.kazedice.constants.CommandDocs;
import anokaze.kazedice.mapper.MapperManager;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;

/**
 * @author AnoKaze
 * @since 2023/02/01
 */
@Slf4j
public class KazeDicePlugin extends BasePlugin {
    private static KazeDicePlugin instance;
    private static MapperManager mapperManager;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        log.info("Plugin Load: KazeDice");
    }

    @Override
    public void onEnable(){
        mapperManager = new MapperManager();

        createCommand("r")
                .addAlias("roll")
                .addOptionalArgument(String.class, "1d100")
                .executesUser(new RollCommand())
                .setDescription(CommandDocs.ROLL_COMMAND_DOC.getDescription())
                .setHelpContent(CommandDocs.ROLL_COMMAND_DOC.getHelpContent())
                .register(getInstance());

        createCommand("st")
                .addAlias("state")
                .addArgument(String.class)
                .addOptionalArgument(String.class, "")
                .executesUser(new StateCommand())
                .addSubcommand(
                        new JKookCommand("rm")
                                .addAlias("remove")
                                .addArgument(String.class)
                                .executesUser(new StateRemoveCommand())
                )
                .register(getInstance());
    }

    @Override
    public void onDisable() {
        mapperManager.disconnect();
        log.info("Plugin Unload: KazeDice");
    }

    public static KazeDicePlugin getInstance() {
        return instance;
    }

    public static MapperManager getMapperManager(){
        return mapperManager;
    }

    private JKookCommand createCommand(String rootName){
        return new JKookCommand(rootName).addPrefix(".").addPrefix("。");
    }
}
