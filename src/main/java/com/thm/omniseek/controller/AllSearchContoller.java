package com.thm.omniseek.controller;

import com.thm.omniseek.common.BaseResponse;
import com.thm.omniseek.common.ResultUtils;
import com.thm.omniseek.manager.SearchFacade;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.vo.SearchVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/code")
public class AllSearchContoller {

    @Resource
    private SearchFacade searchFacade;
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest));
    }

}
