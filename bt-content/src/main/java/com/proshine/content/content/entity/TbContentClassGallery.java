package com.proshine.content.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_content_class_gallery")
public class TbContentClassGallery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cstmId;

    private String galleryName;

    private String galleryDesc;

    private String createUserName;

    private String createUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String galleryFolderId;


}
