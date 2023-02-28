package anokaze.kazedice.constants;

import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
@Getter
public enum DamageBonusEnum {
    // Damage Bonus Tiers
    TIER_N2(65, "-2"),
    TIER_N1(85, "-1"),
    TIER_0(125, "0"),
    TIER_1(165, "1d4"),
    TIER_2(205, "1d6"),
    TIER_3(285, "2d6"),
    TIER_4(365, "3d6"),
    TIER_5(445, "4d6"),
    TIER_6(525, "5d6");

    private final Integer threshold;
    private final String value;

    DamageBonusEnum(Integer threshold, String value){
        this.threshold = threshold;
        this.value = value;
    }
}
