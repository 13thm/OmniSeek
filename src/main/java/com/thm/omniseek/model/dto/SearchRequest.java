package com.thm.omniseek.model.dto;


import com.thm.omniseek.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchRequest extends PageRequest implements Serializable {
    private String keyword;
    private List<String> type;

    private static final long serialVersionUID = 1L;
}
