package anokaze.kazedice.service;

import anokaze.kazedice.entity.RoleBindPojo;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.mapper.RoleBindMapper;
import anokaze.kazedice.mapper.RoleMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/11
 */
public class RoleService {
    private final RoleMapper roleMapper;
    private final RoleBindMapper roleBindMapper;

    public RoleService(RoleMapper roleMapper, RoleBindMapper roleBindMapper){
        this.roleMapper = roleMapper;
        this.roleBindMapper = roleBindMapper;
    }

    public void insertRole(RolePojo role){
        roleMapper.insertRole(role);
    }

    public void bindRoleToCategory(RolePojo role, String categoryId){
        RoleBindPojo query = new RoleBindPojo();
        query.setCategoryId(categoryId);
        query.setUserId(role.getUserId());
        RoleBindPojo exist = roleBindMapper.findRoleBind(query);
        if(exist != null){
            roleBindMapper.deleteRoleBind(exist.getId());
        }
        RoleBindPojo roleBind = new RoleBindPojo(categoryId, role.getUserId(), role.getId());
        roleBindMapper.insertRoleBind(roleBind);
    }

    public void unbindRoleFromCategory(RolePojo role, String categoryId){
        RoleBindPojo query = new RoleBindPojo();
        query.setCategoryId(categoryId);
        query.setUserId(role.getUserId());
        query.setRoleId(role.getId());
        RoleBindPojo exist = roleBindMapper.findRoleBind(query);

        if(exist != null) {
            roleBindMapper.deleteRoleBind(exist.getId());
        }
    }

    public void deleteRole(String roleId){
        roleMapper.deleteRole(roleId);

        RoleBindPojo query = new RoleBindPojo();
        query.setRoleId(roleId);
        RoleBindPojo roleBind = roleBindMapper.findRoleBind(query);
        if(roleBind != null){
            roleBindMapper.deleteRoleBind(roleBind.getId());
        }
    }

    public void updateRole(RolePojo role){
        roleMapper.updateRole(role);
    }

    public RolePojo findRoleById(String id){
        RolePojo query = new RolePojo();
        query.setId(id);
        return roleMapper.findRole(query);
    }

    public RolePojo findRoleByName(String userId, String name){
        RolePojo query = new RolePojo();
        query.setUserId(userId);
        query.setName(name);
        return roleMapper.findRole(query);
    }

    public List<RolePojo> findUserRoles(String userId){
        RolePojo query = new RolePojo();
        query.setUserId(userId);
        return roleMapper.findRoles(query);
    }

    public RolePojo findBoundRole(String categoryId, String userId){
        RoleBindPojo query1 = new RoleBindPojo();
        query1.setCategoryId(categoryId);
        query1.setUserId(userId);
        RoleBindPojo roleBind = roleBindMapper.findRoleBind(query1);
        if(roleBind == null){ return null; }

        RolePojo query2 = new RolePojo();
        query2.setId(roleBind.getRoleId());
        return roleMapper.findRole(query2);
    }

    public List<RolePojo> findCategoryRoles(String categoryId){
        RoleBindPojo query = new RoleBindPojo();
        query.setCategoryId(categoryId);
        List<RoleBindPojo> binds = roleBindMapper.findRoleBinds(query);

        List<RolePojo> result = new ArrayList<>(binds.size());
        for(RoleBindPojo bind: binds){
            RolePojo query0 = new RolePojo();
            query.setUserId(bind.getUserId());
            RolePojo role = roleMapper.findRole(query0);

            if(role != null){
                result.add(role);
            }
        }

        return result;
    }

    public RoleBindPojo findBindByRole(String roleId){
        RoleBindPojo query = new RoleBindPojo();
        query.setRoleId(roleId);
        return roleBindMapper.findRoleBind(query);
    }
}
