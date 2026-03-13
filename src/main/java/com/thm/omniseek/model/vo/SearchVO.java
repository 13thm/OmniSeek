package com.thm.omniseek.model.vo;



import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.model.entity.Photo;
import com.thm.omniseek.model.entity.User;
import lombok.Data;


import java.io.Serializable;
import java.util.List;
@Data
public class SearchVO implements Serializable {
    private List<User> userList;

    private List<Code> codeList;

    private List<Photo> pictureList;

    private static final long serialVersionUID = 1L;
}
