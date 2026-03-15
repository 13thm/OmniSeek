package com.thm.omniseek.manager;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.common.SearchTypeEnum;
import com.thm.omniseek.datasource.CodeDataSource;
import com.thm.omniseek.datasource.NovelDataSource;
import com.thm.omniseek.datasource.PhotoDataSource;
import com.thm.omniseek.datasource.UserDataSource;
import com.thm.omniseek.exception.BusinessException;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.model.entity.Novel;
import com.thm.omniseek.model.entity.Photo;
import com.thm.omniseek.model.entity.User;
import com.thm.omniseek.model.vo.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
public class SearchFacade {
    @Resource
    private CodeDataSource codeDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PhotoDataSource photoDataSource;

    @Resource
    private NovelDataSource novelDataSource;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest) {
        String searchText = searchRequest.getKeyword();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        SearchVO searchVO = new SearchVO();
        if (ObjectUtil.isNotEmpty(searchRequest.getType())) {
            List<SearchTypeEnum> listEnum = searchRequest.getType()
                    .stream()
                    .map(SearchTypeEnum::getEnumByValue)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            // Map：Key=枚举类型，Value=对应的异步任务
            Map<SearchTypeEnum, CompletableFuture<?>> futureMap = new HashMap<>();
            // 创建任务并放入Map
            for (SearchTypeEnum typeEnum : listEnum) {
                switch (typeEnum) {
                    case CODE:
                        CompletableFuture<Page<Code>> codeTask = CompletableFuture.supplyAsync(() -> {
                            SearchRequest query = new SearchRequest();
                            query.setKeyword(searchText);
                            return codeDataSource.doSearch(searchText, current, pageSize);
                        });
                        futureMap.put(SearchTypeEnum.CODE, codeTask);
                        break;
                    case USER:
                        CompletableFuture<Page<User>> userTask = CompletableFuture.supplyAsync(() -> {
                            SearchRequest query = new SearchRequest();
                            query.setKeyword(searchText);
                            return userDataSource.doSearch(searchText, current, pageSize);
                        });
                        futureMap.put(SearchTypeEnum.USER, userTask);
                        break;
                    case PHOTO:
                        CompletableFuture<Page<Photo>> photoTask = CompletableFuture.supplyAsync(() -> {
                            SearchRequest query = new SearchRequest();
                            query.setKeyword(searchText);
                            return photoDataSource.doSearch(searchText, current, pageSize);
                        });
                        futureMap.put(SearchTypeEnum.PHOTO, photoTask);
                        break;
                    case NOVEL:
                        CompletableFuture<Page<Novel>> novelTask = CompletableFuture.supplyAsync(() -> {
                            SearchRequest query = new SearchRequest();
                            query.setKeyword(searchText);
                            return novelDataSource.doSearch(searchText, current, pageSize);
                        });
                        futureMap.put(SearchTypeEnum.NOVEL,novelTask);
                        break;
                    default:
                        CompletableFuture<Void> emptyTask = CompletableFuture.completedFuture(null);
                        futureMap.put(typeEnum, emptyTask);
                        break;
                }
            }
            // 把Map的values转成数组，传给allOf
            CompletableFuture<Void> allTasksFuture = CompletableFuture.allOf(
                    futureMap.values().toArray(new CompletableFuture[0]));
            // 阻塞等待所有任务完成
            try {
                allTasksFuture.get(); // 等待所有任务执行完毕
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                futureMap.values().forEach(future -> future.cancel(true));
            } catch (ExecutionException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "任务执行失败" + e.getCause().getMessage());
            }
            try {
                // 准获取每个枚举对应的任务结果
                if (futureMap.containsKey(SearchTypeEnum.CODE)) {
                    CompletableFuture<Page<Code>> codeFuture = (CompletableFuture<Page<Code>>) futureMap.get(SearchTypeEnum.CODE);
                    Page<Code> codePage = codeFuture.get();
                    searchVO.setCodeList(codePage.getRecords());
                }
                if (futureMap.containsKey(SearchTypeEnum.USER)) {
                    CompletableFuture<Page<User>> userFuture = (CompletableFuture<Page<User>>) futureMap.get(SearchTypeEnum.USER);
                    Page<User> userPage = userFuture.get();
                    searchVO.setUserList(userPage.getRecords());
                }
                if (futureMap.containsKey(SearchTypeEnum.PHOTO)) {
                    CompletableFuture<Page<Photo>> photoFuture = (CompletableFuture<Page<Photo>>) futureMap.get(SearchTypeEnum.PHOTO);
                    Page<Photo> photoPage = photoFuture.get();
                    searchVO.setPictureList(photoPage.getRecords());
                }
                if (futureMap.containsKey(SearchTypeEnum.NOVEL)) {
                    CompletableFuture<Page<Novel>> novelFuture = (CompletableFuture<Page<Novel>>) futureMap.get(SearchTypeEnum.NOVEL);
                    Page<Novel> novelPage = novelFuture.get();
                    searchVO.setNovelList(novelPage.getRecords());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "任务执行失败：" + e.getCause().getMessage());
            }
        }
        return searchVO;
    }
}
