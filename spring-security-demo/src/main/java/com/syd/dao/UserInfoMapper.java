package com.syd.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syd.entity.Menu;
import com.syd.entity.Role;
import com.syd.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserInfoMapper<T> extends BaseMapper<T> {
    /**
     * 根据用户 Id 查询用户角色
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 根据用户 Id 查询菜单
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Long userId);
}
