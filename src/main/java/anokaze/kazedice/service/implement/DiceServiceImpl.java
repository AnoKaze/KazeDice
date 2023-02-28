package anokaze.kazedice.service.implement;

import anokaze.kazedice.constants.StateEnum;
import anokaze.kazedice.constants.SuccessLevel;
import anokaze.kazedice.entity.Assay;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.entity.expression.RollExpression;
import anokaze.kazedice.entity.expression.SanExpression;
import anokaze.kazedice.mapper.RoleMapper;
import anokaze.kazedice.service.DiceService;
import anokaze.kazedice.util.DiceUtil;


/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public class DiceServiceImpl implements DiceService {
    private final RoleMapper roleMapper;

    public DiceServiceImpl(RoleMapper roleMapper){
        this.roleMapper = roleMapper;
    }

    @Override
    public String sanCheck(RolePojo role, SanExpression expression) {
        StringBuilder result = new StringBuilder();

        Integer sanCurrent = role.getAttribute(StateEnum.SAN_CURRENT.getValue());
        Integer sanMax = role.getAttribute(StateEnum.SAN_MAX.getValue());
        Assay assay = DiceUtil.normalAssay(StateEnum.SAN_CURRENT.getValue(), sanCurrent);
        result.append("[").append(role.getName()).append("]").append(assay.getAssayString()).append("\n");

        expression.reset();
        RollExpression chosen = assay.getAssayLevel().compareTo(SuccessLevel.REGULAR) <= 0 ? expression.getSuccess() : expression.getFailed();
        int sanAfter = sanCurrent - chosen.getValue() > sanMax ? sanMax : sanCurrent - chosen.getValue();
        role.getStates().put(StateEnum.SAN_CURRENT.getValue(), sanAfter);
        roleMapper.updateRole(role);
        result.append("损失理智：").append(chosen).append("，当前剩余理智：")
             .append(sanAfter).append(" / ").append(sanMax).append("\n");

        if(sanAfter <= 0){
            result.append("陷入永久疯狂。");
        }
        else if(chosen.getValue() >= 5) {
            result.append("陷入临时疯狂。");
        }

        return result.toString();
    }
}
