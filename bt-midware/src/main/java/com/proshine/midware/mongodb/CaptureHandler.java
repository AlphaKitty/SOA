package com.proshine.midware.mongodb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.proshine.base.common.constant.CommonConstant;
import com.proshine.base.common.exception.IllegalRequestException;
import com.proshine.expo.midware.file.CaptureExpo;
import com.proshine.expo.midware.file.FileExpo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@Service
@Component
public class CaptureHandler implements CaptureExpo {

    /**
     * 用MongoDB的文件系统实现的截屏 所以被归类到MongoDB模块
     */
    @Reference(group = "mongo")
    private FileExpo fileExpo;

    @Override
    public void uploadCapture(String terminalId, String cstmId, MultipartFile file) {
        String fs = CommonConstant.MongoDBCollections.TOUCHSYS_TERMINAL;
        ObjectId key = new ObjectId(terminalId);
        String filename = key + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        if (fileExpo.exists(fs, filename, key)) {
            // log.error(key + "文件存在，删除原文件...");
            fileExpo.deleteOne(fs, filename, key);
        }

        try {
            // log.error(key + "开始上传文件...");
            fileExpo.addOne(fs, filename, key, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalRequestException("文件无法获取输入流");
        }
    }

    @Override
    public Object read(String terminalId, String cstmId) {
        String fs = CommonConstant.MongoDBCollections.TOUCHSYS_TERMINAL;
        ObjectId key = new ObjectId(terminalId);
        return fileExpo.getOne(fs, null, key);
    }

    @Override
    public void deleteById(String id) {
        ObjectId key = new ObjectId(id);
        if (fileExpo.exists(CommonConstant.MongoDBCollections.TOUCHSYS_TERMINAL, null, key)) {
            fileExpo.deleteOne(CommonConstant.MongoDBCollections.TOUCHSYS_TERMINAL, null, key);
        }
    }
}
