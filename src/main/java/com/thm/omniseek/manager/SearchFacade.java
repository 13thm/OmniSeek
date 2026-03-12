package com.thm.omniseek.manager;


import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.vo.SearchVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class SearchFacade {
    public SearchVO searchAll(@RequestBody SearchRequest searchRequest){
        return new SearchVO();
    }
}
