package anokaze.kazedice.mapper;

import anokaze.kazedice.pojo.Role;

public interface RoleMapper {
    boolean insert(Role role);
    Role selectById(Integer id);
}
