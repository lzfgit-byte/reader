package com.ilzf.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilzf.user.entity.UserEntity;
import com.ilzf.user.mapper.UserMapper;
import com.ilzf.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
}
