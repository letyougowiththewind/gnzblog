package com.gnz.blog.model.comm;

import cn.hutool.setting.*;
import lombok.*;

import java.util.*;

@Data
public class BlogSetting {
    private String title;
    private String desc;
    private List<String> covers;
    private String avatar;
    private String nickname;

    public static BlogSetting fromSetting(Setting setting){
        return (BlogSetting) setting.toBean(new BlogSetting());
    }
}
