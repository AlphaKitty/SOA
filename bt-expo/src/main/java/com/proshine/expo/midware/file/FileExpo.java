package com.proshine.expo.midware.file;

import org.bson.types.ObjectId;

import java.io.InputStream;

/**
 * 文件管理服务接口
 *
 * @author 高孙琼
 */

public interface FileExpo {

    /**
     * 读取文件流
     *
     * @param collection 文件存储目录或集合：2017/9或touch_cloud
     * @param filename   文件名称：文件名.jpg
     * @param id         文件id
     */
    Object getOne(String collection, String filename, ObjectId id);

    /**
     * 写入文件流
     *
     * @param collection  文件存储目录或集合：2017/9或touch_cloud
     * @param filename    文件名称：文件名.jpg
     * @param id          文件id
     * @param inputStream 文件流
     */
    String addOne(String collection, String filename, ObjectId id, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param collection 文件存储目录或集合：2017/9或touch_cloud
     * @param filename   文件名称：文件名.jpg
     * @param id         文件id
     * @return true:删除成功，false:删除失败
     */
    boolean deleteOne(String collection, String filename, ObjectId id);

    /**
     * 判断文件是否存在
     *
     * @param collection 文件存储目录或集合：2017/9或touch_cloud
     * @param filename   文件名称：文件名.jpg
     * @param id         文件id
     */
    boolean exists(String collection, String filename, ObjectId id);

}
