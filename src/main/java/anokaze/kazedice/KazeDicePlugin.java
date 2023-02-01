package anokaze.kazedice;

import anokaze.kazedice.command.RollCommand;
import anokaze.kazedice.util.MybatisUtil;
import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;

public class KazeDicePlugin extends BasePlugin {
    private static KazeDicePlugin instance;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        getLogger().info("KazeDice loaded!");
    }

    @Override
    public void onEnable(){
        getLogger().info("KazeDice connected!");
        new JKookCommand("roll")
                .addPrefix(".")
                .addPrefix("。")
                .addAlias("r")
                .addOptionalArgument(String.class, "1d100")
                .executesUser(new RollCommand())
                .setDescription("使用组合表达式进行骰子的投掷")
                .setHelpContent("使用组合表达式进行骰子的投掷，支持三种类型的骰子表达式，表达式之间使用加号进行连接：\n" +
                        "1. **数字1**d/D**数字2**，表明掷出**数字1**枚**数字2**面的骰子，如`2d6`；\n" +
                        "2. d/D**数字**，表明掷出1枚*数字*面的骰子，如`d100`；\n" +
                        "3. **数字**，表明直接使用某数字，如`1`。\n")
                .register(getInstance());

    }

    @Override
    public void onDisable() {
        getLogger().info("KazeDice disconnected!");
    }

    public static KazeDicePlugin getInstance() {
        return instance;
    }
}
