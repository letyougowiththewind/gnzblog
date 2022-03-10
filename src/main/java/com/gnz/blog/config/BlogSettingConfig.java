package com.gnz.blog.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.*;
import cn.hutool.setting.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import com.gnz.blog.model.comm.*;

import java.io.*;

@Slf4j
@Configuration
public class BlogSettingConfig {
    @Value("${setting.path}")
    private String settingFilePath;

    @Bean
    public Setting setting() {
        File file = new File(settingFilePath);
        Setting setting;
        if (!file.exists()) {
            FileUtil.touch(file);
            setting = new Setting(file, CharsetUtil.CHARSET_UTF_8, false);
            setting.set("title", "我本非凡");
            setting.set("desc", "我最帅");
            setting.set("covers", "https://tvax1.sinaimg.cn/mw1024/bfe05ea9ly1fxgu8jys3fj21hc0u0k0j.jpg,https://tvax1.sinaimg.cn/large/bfe05ea9ly1fxgunx09dtj21hc0u0q81.jpg,https://tvax1.sinaimg.cn/large/bfe05ea9ly1fxgv2t92yyj21hc0u0qb9.jpg");
            setting.set("avatar", "http://");
            setting.set("nickname", "让你随风而逝");
            setting.set("username", "xxx");
            setting.set("password", "xxx");
            setting.store(file.getAbsolutePath());
        }
        setting = new Setting(file, CharsetUtil.CHARSET_UTF_8, false);
        setting.autoLoad(true);
        log.info(BlogSetting.fromSetting(setting).toString());
        return setting;
    }
}
