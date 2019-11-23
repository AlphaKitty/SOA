package com.proshine.expo.midware.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 终端截屏接口 API
 */
public interface CaptureExpo {

    /**
     * 上传屏幕截屏
     *
     * @param terminalId 终端id
     * @param cstmId     客户域id
     * @param file       文件
     */
    void uploadCapture(String terminalId, String cstmId, MultipartFile file);

    /**
     * 读取屏幕截屏
     *
     * @param terminalId 终端id
     * @param cstmId     客户域id
     * @return Object
     */
    Object read(String terminalId, String cstmId);

    /**
     * 根据主键terminal主键删除截屏(删除客户域使用)
     */
    void deleteById(String id);
}
