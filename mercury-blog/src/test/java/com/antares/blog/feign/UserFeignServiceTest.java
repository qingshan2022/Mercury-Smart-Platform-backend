package com.mercury.blog.feign;

import com.mercury.common.exception.BusinessException;
import com.mercury.common.model.enums.AppHttpCodeEnum;
import com.mercury.common.model.response.R;
import com.mercury.common.model.vo.UserInfoVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserFeignServiceTest {
    @Resource
    private UserFeignService userFeignService;

    @Test
    void testFeign(){
        R<UserInfoVo> response = userFeignService.info(2L);
        if (response.getCode() == AppHttpCodeEnum.SUCCESS.getCode()) {
            UserInfoVo author = response.getData();
            System.out.println(author);
        } else {
            throw new BusinessException(AppHttpCodeEnum.getEnumByCode(response.getCode()));
        }
    }
}
