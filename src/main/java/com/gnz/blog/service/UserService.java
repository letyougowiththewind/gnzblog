package com.gnz.blog.service;

import cn.hutool.core.collection.*;
import cn.hutool.crypto.digest.*;
import cn.hutool.setting.*;
import com.gnz.blog.model.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.gnz.blog.exception.*;
import com.gnz.blog.model.dto.*;
import com.gnz.blog.utils.*;
import org.springframework.util.DigestUtils;

import java.util.*;

import static com.gnz.blog.model.enums.ErrorInfoEnum.*;

@Service
public class UserService {
    @Autowired
    private Setting setting;

    /**
     * 校验用户名和密码
     * @param userDTO 用户对象
     * @return 校验成功就返回token
     */
    public String checkUsernamePassword(UserDTO userDTO) {
        String username = setting.getStr("username");
        String password = setting.getStr("password");
        System.out.println("加密"+DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        if (Objects.equals(username, userDTO.getUsername()) &&
                Objects.equals(password, DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()))) {
            return JwtUtils.createToken(username, CollUtil.newArrayList(UserRoleEnum.ADMIN.getValue()));
        } else {
            throw new BlogException(USERNAME_PASSWORD_ERROR);
        }
    }
}
