package anokaze.kazedice.service.implement;

import anokaze.kazedice.constants.CharacteristicEnum;
import anokaze.kazedice.entity.RoleBindPojo;
import anokaze.kazedice.entity.RolePojo;
import anokaze.kazedice.mapper.RoleBindMapper;
import anokaze.kazedice.mapper.RoleMapper;
import anokaze.kazedice.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnoKaze
 * @since 2023/2/11
 */
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleBindMapper roleBindMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleBindMapper roleBindMapper){
        this.roleMapper = roleMapper;
        this.roleBindMapper = roleBindMapper;
    }

    @Override
    public void insertRole(RolePojo role){
        roleMapper.insertRole(role);
    }

    @Override
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

    @Override
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

    @Override
    public void deleteRole(String roleId){
        roleMapper.deleteRole(roleId);

        RoleBindPojo query = new RoleBindPojo();
        query.setRoleId(roleId);
        RoleBindPojo roleBind = roleBindMapper.findRoleBind(query);
        if(roleBind != null){
            roleBindMapper.deleteRoleBind(roleBind.getId());
        }
    }

    @Override
    public void updateRole(RolePojo role){
        roleMapper.updateRole(role);
    }

    @Override
    public RolePojo findRoleById(String id){
        RolePojo query = new RolePojo();
        query.setId(id);
        return roleMapper.findRole(query);
    }

    @Override
    public RolePojo findRoleByName(String userId, String name){
        RolePojo query = new RolePojo();
        query.setUserId(userId);
        query.setName(name);
        return roleMapper.findRole(query);
    }

    @Override
    public List<RolePojo> findUserRoles(String userId){
        RolePojo query = new RolePojo();
        query.setUserId(userId);
        return roleMapper.findRoles(query);
    }

    @Override
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

    @Override
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

        result.sort(((o1, o2) -> {
            Integer dex1 = o1.getAttribute(CharacteristicEnum.CHARACTERISTIC_DEX.getName());
            Integer dex2 = o2.getAttribute(CharacteristicEnum.CHARACTERISTIC_DEX.getName());
            return dex1 - dex2;
        }));

        return result;
    }
}
