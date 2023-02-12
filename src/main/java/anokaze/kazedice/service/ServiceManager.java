package anokaze.kazedice.service;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.mapper.MapperManager;

/**
 * @author AnoKaze
 * @since 2023/2/11
 */
public class ServiceManager {
    private final RoleService roleService;

    public ServiceManager(){
        MapperManager mappers = KazeDicePlugin.getMapperManager();

        roleService = new RoleService(mappers.getRoleMapper(), mappers.getRoleBindMapper());
    }

    public RoleService getRoleService(){
        return roleService;
    }
}
