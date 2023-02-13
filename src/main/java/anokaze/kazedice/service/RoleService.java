package anokaze.kazedice.service;

import anokaze.kazedice.entity.RoleBindPojo;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.mapper.RoleBindMapper;
import anokaze.kazedice.mapper.RoleMapper;
import org.bson.types.ObjectId;

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
        RoleBindPojo roleBind = new RoleBindPojo();
        roleBind.setCategoryId(categoryId);
        roleBind.setUserId(role.getUserId());
        roleBind.setRoleId(role.getId());
        roleBindMapper.insertRoleBind(roleBind);
    }

    public void deleteRole(ObjectId roleId){
        roleMapper.deleteRole(roleId);

        RoleBindPojo query = new RoleBindPojo();
        query.setRoleId(roleId);
        RoleBindPojo roleBind = roleBindMapper.findRoleBind(query);
        if(roleBind != null){
            roleBindMapper.deleteRoleBind(roleBind.getId());
        }
    }

    public void updateRole(ObjectId id, RolePojo role){
        roleMapper.updateRole(id, role);
    }

    public RolePojo findRoleById(ObjectId id){
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
        query2.setId(roleBind.getId());
        return roleMapper.findRole(query2);
    }

    public RoleBindPojo findBindByRole(ObjectId roleId){
        RoleBindPojo query = new RoleBindPojo();
        query.setRoleId(roleId);
        return roleBindMapper.findRoleBind(query);
    }
}
