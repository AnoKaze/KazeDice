package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/11
 */
@Getter
public enum SkillEnum {
    // 技能
    SKILL_ACCOUNTING("会计", 5),
    SKILL_ANTHROPOLOGY("人类学", 1),
    SKILL_APPRAISE("估价", 5),
    SKILL_ARCHAEOLOGY("考古学", 1),
    SKILL_CHARM("取悦", 15),
    SKILL_CLIMB("攀爬", 20),
    SKILL_COMPUTER_USE("计算机使用", 5),
    SKILL_CREDIT_RATING("信用评级", 0),
    SKILL_CTHULHU_MYTHOS("克苏鲁神话", 0),
    SKILL_DISGUISE("乔装", 5),
    // SKILL_DODGE("闪避", 0),
    SKILL_DRIVE_AUTO("汽车驾驶", 20),
    SKILL_ELECTRICAL_REPAIR("电气维修", 10),
    SKILL_ELECTRONICS("电子学", 1),
    SKILL_FAST_TALK("话术", 5),
    SKILL_FIGHTING_BRAWL("斗殴", 25),
    SKILL_FIREARMS_HANDGUN("射击", 20),
    SKILL_FIRST_AID("急救", 30),
    SKILL_HISTORY("历史", 5),
    SKILL_INTIMIDATE("恐吓", 15),
    SKILL_JUMP("跳跃", 20),
    // SKILL_LANGUAGE("母语", 0),
    SKILL_LAW("法律", 5),
    SKILL_LIBRARY_USE("图书馆使用", 20),
    SKILL_LISTEN("聆听", 20),
    SKILL_LOCKSMITH("锁匠", 1),
    SKILL_MECHANICAL_REPAIR("机械维修", 10),
    SKILL_MEDICINE("医学", 1),
    SKILL_NATURAL_WORLD("博物学", 10),
    SKILL_NAVIGATE("领航", 10),
    SKILL_OCCULT("神秘学", 5),
    SKILL_OPERATE_HEAVY_MACHINERY("操作重型机械", 1),
    SKILL_PERSUADE("说服", 10),
    SKILL_PSYCHOANALYSIS("精神分析", 1),
    SKILL_PSYCHOLOGY("心理学", 10),
    SKILL_RIDE("骑术", 5),
    SKILL_SLEIGHT_OF_HAND("妙手", 10),
    SKILL_SPOT_HIDDEN("侦查", 25),
    SKILL_STEALTH("潜行", 20),
    SKILL_SWIM("游泳", 20),
    SKILL_THROW("投掷", 20),
    SKILL_TRACK("追踪", 10),
    SKILL_BEAST_TRAINING("驯兽", 5),
    SKILL_DIVING("潜水", 1),
    SKILL_DEMOLITIONS("爆破", 1),
    SKILL_READ_LIPS("读唇", 1),
    SKILL_HYPNOSIS("催眠", 1),
    SKILL_ARTILLERY("炮术", 1);

    private final String name;
    private final Integer defaultValue;

    SkillEnum(String name, Integer base){
        this.name = name;
        this.defaultValue = base;
    }
}
