package com.proshine.midware.clouddisk.support;

import com.proshine.base.common.constant.CommonConstant;
import com.proshine.base.common.utils.StringUtils;
import com.proshine.expo.midware.clouddisk.entity.Attachment;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传附件包装处理类
 *
 * @author 高孙琼
 */
public class MultipartFileWrapper {

    static final String SLANT = "/";
    static final String DOT = ".";

    private MultipartFile file;

    public MultipartFileWrapper(MultipartFile file) {
        this.file = file;
    }

    public Attachment toAttachment() {
        Attachment attachment = new Attachment();

        if (file.getOriginalFilename() == null || file.getOriginalFilename() == "") {
            String names[] = file.getName().split("\\.");
            if (names != null && names.length == 2) {
                attachment.setName(names[0]);
                attachment.setSuffix(names[1]);
            }
        } else {
            attachment.setName(this.getFileName());
            attachment.setSuffix(this.getFileSuffix());
        }
        attachment.setPath(this.getFileDir());
        attachment.setSize(this.getFileSize());
        attachment.setMd5(this.getFileVerifyCode());

        attachment.setType(AttachmentSuffixConvert.convert(attachment.getSuffix()).id());
        return attachment;
    }

    /**
     * 获取文件名称
     */
    public String getFileName() {
        return StringUtils.substringBeforeLast(this.file.getOriginalFilename(), DOT);
    }

    /**
     * 获取文件后缀
     */
    public String getFileSuffix() {
        return StringUtils.substringAfterLast(this.file.getOriginalFilename(), DOT);
    }

    /**
     * 获取文件存储目录，以斜杠开头
     */
    public String getFileDir() {
        return CommonConstant.MongoDBCollections.TOUCHSYS_CLOUD +
                SLANT +
                this.file.getOriginalFilename();
    }

    /**
     * 获取文件大小
     */
    public long getFileSize() {
        return this.file.getSize();
    }

    /**
     * 获取文件校验码, MD5
     */
    public String getFileVerifyCode() {
        try {
            return DigestUtils.md5DigestAsHex(this.file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("file verify code error.", e);
        }
    }

}
