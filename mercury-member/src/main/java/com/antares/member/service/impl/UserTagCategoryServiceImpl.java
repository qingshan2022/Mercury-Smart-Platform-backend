package com.mercury.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercury.member.mapper.UserTagCategoryMapper;
import com.mercury.member.model.entity.UserTagCategory;
import com.mercury.member.service.UserTagCategoryService;
import org.springframework.stereotype.Service;

/**
* @author mercury
* @description 针对表【user_tag_category】的数据库操作Service实现
* @createDate 2023-03-05 22:01:36
*/
@Service
public class UserTagCategoryServiceImpl extends ServiceImpl<UserTagCategoryMapper, UserTagCategory>
    implements UserTagCategoryService{

}




