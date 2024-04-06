package com.mercury.search.service.impl;

import com.mercury.search.mapper.UserMapper;
import com.mercury.search.model.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercury.search.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author mercury
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-21 13:36:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




