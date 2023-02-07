package com.ilzf.config.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilzf.config.entity.ConfigEntity;
import com.ilzf.config.mapper.ConfigMapper;
import com.ilzf.config.service.ConfigService;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, ConfigEntity> implements ConfigService {
}
