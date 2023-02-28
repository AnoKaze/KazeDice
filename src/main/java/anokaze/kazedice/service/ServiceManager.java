package anokaze.kazedice.service;

import anokaze.kazedice.KazeDicePlugin;
import anokaze.kazedice.mapper.MapperManager;
import anokaze.kazedice.service.implement.DiceServiceImpl;
import anokaze.kazedice.service.implement.RoleServiceImpl;
import lombok.Getter;

/**
 * @author AnoKaze
 * @since 2023/2/11
 */
@Getter
public class ServiceManager {
    private final RoleService roleService;
    private final DiceService diceService;

    public ServiceManager(){
        MapperManager mappers = KazeDicePlugin.getMapperManager();

        roleService = new RoleServiceImpl(mappers.getRoleMapper(), mappers.getRoleBindMapper());
        diceService = new DiceServiceImpl(mappers.getRoleMapper());
    }
}
