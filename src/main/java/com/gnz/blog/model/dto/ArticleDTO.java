package com.gnz.blog.model.dto;
import cn.hutool.core.bean.*;
import io.swagger.annotations.*;
import lombok.*;
import org.hibernate.validator.constraints.*;
import com.gnz.blog.model.entity.*;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "文章类", description = "前端传入的文章信息")
public class ArticleDTO {
    @NotEmpty(message = "文章作者不能为空")
    @ApiModelProperty(notes = "文章作者", example = "竹林笔墨")
    private String author;
    @NotEmpty(message = "文章标题不能为空")
    @ApiModelProperty(notes = "文章标题", example = "快速入门SpringBoot")
    private String title;
    @NotEmpty(message = "文章内容不能为空")
    @ApiModelProperty(notes = "文章内容")
    private String content;
    @NotEmpty(message = "文章标签不能为空")
    @ApiModelProperty(notes = "文章标签，用英文逗号隔开", example = "java,集合")
    private String tags;
    @Range(min = 0, max = 1, message = "文章类型必须为1或者0")
    @ApiModelProperty(notes = "文章类型，0表示转载，1表示原创", example = "1")
    private Integer type;
    @NotEmpty(message = "文章分类不能为空")
    @ApiModelProperty(notes = "文章分类", example = "设计模式")
    private String category;
    @NotEmpty(message = "文章简介不能为空")
    @ApiModelProperty(notes = "文章摘要")
    private String tabloid;

    /**
     * 将 DTO 转换为 PO
     *
     * @param isUpdate   此对象是否为更新对象
     * @return 转换结果
     */
    public ArticlePO toArticlePO(boolean isUpdate) {
        ArticlePO po = new Converter().convertToPO(this);
        po.setViews(isUpdate ? null : 0);
        po.setGmtCreate(isUpdate ? null : po.getGmtUpdate());
        return po;
    }

    private static class Converter implements IConverter<ArticleDTO, ArticlePO> {
        @Override
        public ArticlePO convertToPO(ArticleDTO articleDTO) {
            ArticlePO po = new ArticlePO();
            po.setGmtUpdate(System.currentTimeMillis());
            BeanUtil.copyProperties(articleDTO, po);
            return po;
        }
    }
}
