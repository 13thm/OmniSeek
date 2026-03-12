package com.thm.omniseek.datasource;

import com.thm.omniseek.common.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源注册器
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Component
public class DataSourceRegistry {

    @Resource
    private PhotoDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private CodeDataSource codeDataSource;

    private Map<String, DataSource<T>> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        System.out.println(1);
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.CODE.getValue(), codeDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.PHOTO.getValue(),postDataSource);
        }};
    }

    public DataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
