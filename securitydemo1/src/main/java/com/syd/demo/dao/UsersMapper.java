package com.syd.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syd.demo.entity.Users;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersMapper extends BaseMapper<Users> {
}
