package com.mercury.search.manager;

import com.mercury.search.datasource.DataSource;
import com.mercury.search.datasource.DataSourceRegistry;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.mercury.common.exception.BusinessException;
import com.mercury.common.model.enums.AppHttpCodeEnum;
import com.mercury.search.model.dto.SearchRequest;
import com.mercury.search.model.enums.SearchTypeEnum;
import com.mercury.search.model.vo.SearchVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
//定义了一个公开的类 SearchFacade，这个类用来处理搜索相关的功能
public class SearchFacade {
    //使用 @Resource 注解注入了一个名为 dataSourceRegistry 的 DataSourceRegistry 类型的数据源注册对象。
    @Resource
    private DataSourceRegistry dataSourceRegistry;
    //定义了一个公开的方法 search，接收一个类型为 SearchRequest 的请求参数，并返回一个 SearchVo 对象。
    public SearchVo search(@RequestBody SearchRequest searchRequest) {
        //获取请求参数中的搜索类型
        String type = searchRequest.getType();
        //根据搜索类型获取相应的枚举类型 SearchTypeEnum 对象
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        //获取请求参数中的搜索关键词
        String searchText = searchRequest.getKeyword();
        //获取请求参数中的页码
        int pageNum = searchRequest.getPageNum();
        //获取请求参数中的每页显示条数
        int pageSize = searchRequest.getPageSize();
        //获取请求参数中的标签列表
        List<String> tags = searchRequest.getTags();
        //如果搜索类型为空（即未指定查询类型），则抛出一个业务异常并返回相应错误信息
        if (searchTypeEnum == null) {
            throw new BusinessException(AppHttpCodeEnum.PARAMS_ERROR, "未指定查询类型");
        } else {
            //创建一个 SearchVo 对象用来存储搜索结果
            SearchVo searchVO = new SearchVo();
            //根据搜索类型从数据源注册对象中获取相应的数据源对象
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            //调用数据源对象的 doSearch 方法执行搜索操作，
            // 传入搜索关键词、页码、每页显示条数、标签信息，返回一个 Page 类型的结果对象
            Page<?> page = dataSource.doSearch(searchText, pageNum, pageSize, tags);
            //将搜索结果设置到 searchVO 对象中
            searchVO.setPageData(page);
            //返回包含搜索结果的 searchVO 对象
            return searchVO;
        }
    }
}
