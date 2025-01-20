package com.px.apis.user.vo;

import com.px.basic.alone.security.dto.AbsAppUser;
import com.px.modulars.miniusers.entity.MiniUsers;
import lombok.Data;

@Data
public class LoginResult {
    private AbsAppUser absAppUser;

    private MiniUsers miniUser;

}
