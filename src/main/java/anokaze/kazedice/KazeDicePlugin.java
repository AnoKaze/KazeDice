package anokaze.kazedice;

import anokaze.kazedice.command.*;
import anokaze.kazedice.constants.CommandDocEnum;
import anokaze.kazedice.entity.expression.RollExpression;
import anokaze.kazedice.entity.expression.DiceGroup;
import anokaze.kazedice.entity.expression.SanExpression;
import anokaze.kazedice.entity.expression.StateExpression;
import anokaze.kazedice.mapper.MapperManager;
import anokaze.kazedice.parser.DiceGroupParser;
import anokaze.kazedice.parser.RollExpressionParser;
import anokaze.kazedice.parser.SanExpressionParser;
import anokaze.kazedice.parser.StateExpressionParser;
import anokaze.kazedice.service.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.CommandManager;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
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

        CommandManager commandManager = getCore().getCommandManager();
        commandManager.registerArgumentParser(DiceGroup.class, new DiceGroupParser());
        commandManager.registerArgumentParser(RollExpression.class, new RollExpressionParser());
        commandManager.registerArgumentParser(SanExpression.class, new SanExpressionParser());
        commandManager.registerArgumentParser(StateExpression.class, new StateExpressionParser());

        // region roll
        createCommand("r")
                .addAlias("roll")
                .addOptionalArgument(RollExpression.class, new RollExpression("1d100"))
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
        createCommand("rp")
                .addAlias("rollPunish")
                .addArgument(Integer.class)
                .addArgument(String.class)
                .addOptionalArgument(Integer.class, -1)
                .executesUser(new RollPunishCommand())
                .register(getInstance());
        createCommand("rb")
                .addAlias("rollBonus")
                .addArgument(Integer.class)
                .addArgument(String.class)
                .addOptionalArgument(Integer.class, -1)
                .executesUser(new RollBonusCommand())
                .register(getInstance());
        createCommand("rav")
                .addAlias("rollAssayVersus")
                .addArgument(String.class)
                .addOptionalArgument(Integer.class, -1)
                .executesUser(new RollAssayVersusCommand())
                .register(getInstance());
        createCommand("sc")
                .addAlias("sanCheck")
                .addArgument(SanExpression.class)
                .executesUser(new SanCheckCommand())
                .register(getInstance());
        // endregion
        // region state
        createCommand("st")
                .addAlias("state")
                .addArgument(StateExpression.class)
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
                .addSubcommand(
                        new JKookCommand("ub")
                                .addAlias("unbind")
                                .executesUser(new StateUnbindCommand())
                )
                .register(getInstance());
        // endregion
        // region team
        createCommand("team")
                .executesUser(new TeamCommand())
                .addSubcommand(
                        new JKookCommand("sc")
                                .addAlias("sanCheck")
                                .addArgument(SanExpression.class)
                                .executesUser(new TeamSanCheckCommand())
                )
                .addSubcommand(
                        new JKookCommand("ra")
                                .addAlias("rollAssay")
                                .addArgument(String.class)
                                .executesUser(new TeamRollAssayCommand())
                )
                .addSubcommand(
                        new JKookCommand("hp")
                                .addArgument(User.class)
                                .addArgument(RollExpression.class)
                                .executesUser(new TeamHpCommand())
                )
                .addSubcommand(
                        new JKookCommand("san")
                                .addArgument(User.class)
                                .addArgument(RollExpression.class)
                                .executesUser(new TeamSanCommand())
                )
                .addSubcommand(
                        new JKookCommand("en")
                                .executesUser(new TeamEnhanceCommand())
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
