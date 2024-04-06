package com.mercury.search.service.impl;

import com.mercury.search.mapper.UserTagMapper;
import com.mercury.search.model.entity.UserTag;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercury.search.service.UserTagService;
import org.springframework.stereotype.Service;

/**
* @author mercury
* @description 针对表【user_tag】的数据库操作Service实现
* @createDate 2023-05-21 13:36:25
*/
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag>
    implements UserTagService{

}




