package anokaze.kazedice;

import anokaze.kazedice.command.*;
import anokaze.kazedice.constants.CommandDocEnum;
import anokaze.kazedice.mapper.MapperManager;
import anokaze.kazedice.service.ServiceManager;
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
    private static ServiceManager serviceManager;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        log.info("Plugin Load: KazeDice");
    }

    @Override
    public void onEnable(){
        mapperManager = new MapperManager();
        serviceManager = new ServiceManager();

        // region roll
        createCommand("r")
                .addAlias("roll")
                .addOptionalArgument(String.class, "1d100")
                .executesUser(new RollCommand())
                .setDescription(CommandDocEnum.ROLL_COMMAND_DOC.getDescription())
                .setHelpContent(CommandDocEnum.ROLL_COMMAND_DOC.getHelpContent())
                .register(getInstance());
        createCommand("ra")
                .addAlias("rollAssay")
                .addArgument(String.class)
                .addOptionalArgument(Integer.class, -1)
                .executesUser(new RollAssayCommand())
                .register(getInstance());
        createCommand("rav")
                .addAlias("rollAssayVersus")
                .addArgument(String.class)
                .addOptionalArgument(Integer.class, -1)
                .executesUser(new RollAssayVersusCommand())
                .register(getInstance());
        createCommand("sc")
                .addAlias("sanCheck")
                .addArgument(String.class)
                .executesUser(new SanCheckCommand())
                .register(getInstance());
        // endregion
        // region state commands
        createCommand("st")
                .addAlias("state")
                .addArgument(String.class)
                .addOptionalArgument(String.class, "")
                .executesUser(new StateCommand())
                .setDescription(CommandDocEnum.STATE_COMMAND_DOC.getDescription())
                .setHelpContent(CommandDocEnum.STATE_COMMAND_DOC.getHelpContent())
                .addSubcommand(
                        new JKookCommand("rm")
                                .addAlias("remove")
                                .addArgument(String.class)
                                .executesUser(new StateRemoveCommand())
                )
                .addSubcommand(
                        new JKookCommand("ls")
                                .addAlias("list")
                                .executesUser(new StateListCommand())
                )
                .addSubcommand(
                        new JKookCommand("rn")
                                .addAlias("rename")
                                .addArgument(String.class)
                                .executesUser(new StateRenameCommand())

                )
                .addSubcommand(
                        new JKookCommand("show")
                                .addOptionalArgument(String.class, "")
                                .executesUser(new StateShowCommand())
                )
                .register(getInstance());
        // endregion
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

    public static ServiceManager getServiceManager() {
        return serviceManager;
    }

    private JKookCommand createCommand(String rootName){
        return new JKookCommand(rootName).addPrefix(".").addPrefix("。");
    }
}
