package com.px.modulars.miniusers.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.modulars.miniusers.entity.MiniUsers;
import com.px.modulars.miniusers.mapper.MiniUsersMapper;
import com.px.modulars.miniusers.service.MiniUsersService;
import org.springframework.stereotype.Service;

@Service
public class MiniUsersServiceImpl extends ServiceImpl<MiniUsersMapper,MiniUsers> implements MiniUsersService {
}
