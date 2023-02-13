package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * 命令帮助内容枚举
 * @author AnoKaze
 * @since 2023/02/02
 */
@Getter
public enum CommandDocEnum {
    /**
     * 基本掷骰指令
     */
    ROLL_COMMAND_DOC("计算若干骰子和常数进行四则运算后的数值",
            ":wrench:用法：/r [expression]\n" +
                    ":dart:示例：/r 1d6+4, /roll 3d6k2, /roll d100\n" +
                    ":open_book:细节：\n" +
                    "在骰子表达式中的一组骰子可以分为骰子个数{N}、骰子面数{D}以及取用个数{K}三个部分，并组合成以下三种类型：\n" +
                    "1. NXX：仅有N部分，表示一个常数。示例：`1`，`5`。\n" +
                    "2. NDX: 有N和D部分，表示掷出N枚D面骰，省略N时代表N为1。示例：`d100`，`2d6`，`1d10`。\n" +
                    "3. NDK：有N、D和K三部分，表示掷出N枚D面骰，并取用最大的K枚。示例：`3d6k2`，`2d100k1`。\n" +
                    "多组骰子之间可以使用+-*/进行简单的四则运算。"),
    STATE_COMMAND_DOC("能够创建和修改角色，并将角色与分组绑定",
            ":wrench:用法: /st (role_name) [attributes]\n" +
                    ":dart:示例：/st 力量50敏捷60体质40，/st 克里斯蒂娜 力量55敏捷80意志40体质65外貌80教育65体型75智力70\n" +
                    ":open_book:细节：\n" +
                    "1. 当输入name和attributes时，bot会试图获取名为name的角色，若该角色不存在，则新建一个名为name的角色。之后将attributes赋给角色。\n" +
                    "2. 当仅输入attributes时，bot会试图私聊或分组内绑定的角色。若尚未绑定角色，则会试图获取名为[自定义角色]的角色，若该角色不存在，则新建该角色。之后将attributes赋给角色，并将自定义角色与私聊或分组进行绑定。\n" +
                    "3. 当仅输入name时，bot会试图将角色与当前的分组进行绑定。");

    private final String description;
    private final String helpContent;

    CommandDocEnum(String description, String helpContent){
        this.description = description;
        this.helpContent = helpContent;
    }
}
