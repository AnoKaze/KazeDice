package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * 命令帮助内容枚举
 * @author AnoKaze
 * @date 2023/2/2
 */
@Getter
public enum CommandDocs {
    /**
     * 基本掷骰指令
     */
    ROLL_COMMAND_DOC("基本掷骰指令",
            ":wrench:用法：/roll [expression]\n" +
                    ":dart:示例：/roll 1d6+4, /roll 3d6k2, /roll d100\n" +
                    ":book:细节：\n" +
                    "在骰子表达式中的一组骰子可以分为骰子个数{N}、骰子面数{D}以及取用个数{K}三个部分，并组合成以下三种类型：" +
                    "1. NXX：仅有N部分，表示一个常数。示例：`1`，`5`。\n" +
                    "2. NDX: 有N和D部分，表示掷出N枚D面骰，省略N时代表N为1。示例：`d100`，`2d6`，`1d10`。\n" +
                    "3. NDK：有N、D和K三部分，表示掷出N枚D面骰，并取用最大的K枚。示例：`3d6k2`，`2d100k1`。\n" +
                    "多组骰子之间可以使用+-*/进行简单的四则运算。");

    private final String description;
    private final String helpContent;

    CommandDocs(String description, String helpContent){
        this.description = description;
        this.helpContent = helpContent;
    }
}
