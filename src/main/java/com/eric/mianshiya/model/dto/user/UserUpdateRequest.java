package com.eric.mianshiya.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户更新请求
 *
 * @author <a href="https://github.com/lieric">程序员eric</a>
 * @from <a href="https://eric.icu">编程导航知识星球</a>
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}