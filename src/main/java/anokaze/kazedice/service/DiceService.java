package anokaze.kazedice.service;

import anokaze.kazedice.entity.Assay;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.entity.expression.SanExpression;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
public interface DiceService {
    /**
     * 使角色进行一次San check
     * @param role 目标角色
     * @param sanCheck san check表达式
     * @return 检定结果
     */
    String sanCheck(RolePojo role, SanExpression sanCheck);
}
