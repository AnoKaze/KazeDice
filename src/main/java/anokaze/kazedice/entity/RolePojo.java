package anokaze.kazedice.entity;

import anokaze.kazedice.constants.CharacteristicEnum;
import anokaze.kazedice.constants.DamageBonusEnum;
import anokaze.kazedice.constants.SkillEnum;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.*;

/**
 * @author AnoKaze
 * @since 2023/02/08
 */
@Data
public class RolePojo {
    @SerializedName("_id")
    private String id;
    private String name;
    private String userId;
    private Map<String, Integer> characteristics;
    private Map<String, Integer> skills;
    private Map<String, Integer> states;
    private Set<String> bonus;

    public RolePojo(){}

    public RolePojo(String name, String userId){
        this.id = userId + System.currentTimeMillis();
        this.name = name;
        this.userId = userId;

        characteristics = new HashMap<>(11);
        CharacteristicEnum[] defaultCharacteristics = CharacteristicEnum.values();
        for(CharacteristicEnum characteristic: defaultCharacteristics){
            characteristics.put(characteristic.getName(), characteristic.getDefaultValue());
        }

        skills = new HashMap<>(60);
        SkillEnum[] defaultSkills = SkillEnum.values();
        for(SkillEnum skill: defaultSkills){
            skills.put(skill.getName(), skill.getDefaultValue());
        }

        states = new HashMap<>(8);
        bonus = new HashSet<>();
    }

    public Integer getAttribute(String name){
        if(states.containsKey(name)){
            return states.get(name);
        }
        if(characteristics.containsKey(name)){
            return characteristics.get(name);
        }
        if(skills.containsKey(name)){
            return skills.get(name);
        }
        return 0;
    }

    public String getDamageBonus(){
        Integer con = characteristics.get(CharacteristicEnum.CHARACTERISTIC_CON.getName());
        Integer siz = characteristics.get(CharacteristicEnum.CHARACTERISTIC_SIZ.getName());

        DamageBonusEnum[] dbs = DamageBonusEnum.values();
        for(DamageBonusEnum db: dbs){
            if(con + siz < db.getThreshold()){
                return db.getValue();
            }
        }
        return DamageBonusEnum.TIER_6.getValue();
    }

    public void addBonus(String name){
        if(skills.containsKey(name) && !name.equals(SkillEnum.SKILL_CTHULHU_MYTHOS.getName())
                && !name.equals(SkillEnum.SKILL_CREDIT_RATING.getName())){
            bonus.add(name);
        }
    }
}
