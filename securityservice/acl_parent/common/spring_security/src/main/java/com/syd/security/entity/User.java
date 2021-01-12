package com.syd.security.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@ApiModel(description = "用户实体类")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信id")
    private String username;

    @ApiModelProperty(value = "微信密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String salt;

    @ApiModelProperty(value = "用户签名")
    private String token;
}
