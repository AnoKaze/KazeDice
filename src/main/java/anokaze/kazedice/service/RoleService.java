package anokaze.kazedice.service;
import anokaze.kazedice.entity.RolePojo;

import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/23
 */
 public interface RoleService {
    /**
     * 向数据库中插入角色
     * @param role 角色对象
     */
    void insertRole(RolePojo role);

    /**
     * 将角色与分组绑定
     * @param role 角色对象
     * @param categoryId 绑定的分组id
     */
    void bindRoleToCategory(RolePojo role, String categoryId);

    /**
     * 将角色与分组解绑
     * @param role 角色对象
     * @param categoryId 解绑的分组id
     */
    void unbindRoleFromCategory(RolePojo role, String categoryId);

    /**
     * 从数据库中删除角色
     * @param roleId 角色id
     */
     void deleteRole(String roleId);

    /**
     * 更新数据库中的角色信息
     * @param role 角色对象
     */
     void updateRole(RolePojo role);

    /**
     * 根据角色id获取角色对象
     * @param id 角色id
     * @return 角色对象，未查到时为null
     */
     RolePojo findRoleById(String id);

    /**
     * 根据用户和角色名获取角色对象
     * @param userId 用户id
     * @param name 角色名
     * @return 角色对象，未查到时为null
     */
     RolePojo findRoleByName(String userId, String name);

    /**
     * 获取一名用户的所有角色
     * @param userId 用户id
     * @return 角色对象列表
     */
     List<RolePojo> findUserRoles(String userId);

    /**
     * 获取用户与某一分组绑定的角色
     * @param categoryId 分组id
     * @param userId 用户id
     * @return 绑定的角色
     */
     RolePojo findBoundRole(String categoryId, String userId);

    /**
     * 获取某一分组内所有绑定的角色
     * @param categoryId 分组id
     * @return 绑定的角色列表
     */
     List<RolePojo> findCategoryRoles(String categoryId);
}
