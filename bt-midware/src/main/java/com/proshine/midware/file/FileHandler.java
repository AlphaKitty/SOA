package com.proshine.midware.file;

import com.alibaba.dubbo.config.annotation.Service;
import com.proshine.expo.midware.file.FileExpo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 磁盘文件管理服务实现类
 *
 * @author 高孙琼
 */
@Log4j2
@Component
@Service(group = "file")
public class FileHandler implements FileExpo {

    public static void main(String[] args) {
        String filePath = "F:/temp/a/b";
        File file = new File(filePath);
        try {
            System.out.println("file.exists()? " + file.exists());
            FileUtils.forceMkdir(file);
            System.out.println("getPathNoEndSeparator:" + FilenameUtils.getPathNoEndSeparator(filePath + "/c.txt"));
            System.out.println("getPath:" + FilenameUtils.getPath(filePath + "/c.txt"));
            System.out.println("getBaseName:" + FilenameUtils.getBaseName(filePath + "/c.txt"));
            System.out.println("getExtension:" + FilenameUtils.getExtension(filePath + "/c.txt"));
            System.out.println("getFullPath:" + FilenameUtils.getFullPath(filePath + "/c.txt"));
            System.out.println("getFullPathNoEndSeparator:" + FilenameUtils.getFullPathNoEndSeparator(filePath + "/c.txt"));
            System.out.println("getName:" + FilenameUtils.getName(filePath + "/c.txt"));
            System.out.println("getPrefix:" + FilenameUtils.getPrefix(filePath + "/c.txt"));
            System.out.println("getPrefixLength:" + FilenameUtils.getPrefixLength(filePath + "/c.txt"));
            System.out.println("normalize:" + FilenameUtils.normalize(filePath + "/c.txt"));
            System.out.println("normalizeNoEndSeparator:" + FilenameUtils.normalizeNoEndSeparator(filePath + "/c.txt"));
            System.out.println("removeExtension:" + FilenameUtils.removeExtension(filePath + "/c.txt"));
            System.out.println("separatorsToSystem:" + FilenameUtils.separatorsToSystem(filePath + "/c.txt"));
            System.out.println("separatorsToUnix:" + FilenameUtils.separatorsToUnix(filePath + "/c.txt"));
            System.out.println("separatorsToWindows:" + FilenameUtils.separatorsToWindows(filePath + "/c.txt"));
            System.out.println("file.exists()? " + file.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件流
     *
     * @param collection 文件存储目录：2017/9
     * @param filename   文件名称：文件名.jpg
     * @param id         文件id
     */
    @Override
    public InputStream getOne(String collection, String filename, ObjectId id) {
        try {
            log.debug("正在读取磁盘文件：" + "/" + collection + "/" + filename);
            return new FileInputStream(new File("/" + collection + "/" + filename));
        } catch (FileNotFoundException e) {
            log.error("读取磁盘文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 写入文件流
     *
     * @param collection  文件存储目录：2017/9
     * @param filename    文件名称：文件名.jpg
     * @param id          文件id
     * @param inputStream 文件流
     */
    @Override
    public String addOne(String collection, String filename, ObjectId id, InputStream inputStream) {
        try {
            log.debug("正在写入磁盘文件：" + "/" + collection + "/" + filename);
            // 检查目录是否存在, 不存在则创建
            FileUtils.forceMkdir(new File(collection));

            //将输入流写入磁盘
            OutputStream outputStream = new FileOutputStream(new File("/" + collection + "/" + filename));
            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            return collection;
        } catch (IOException e) {
            log.error("写入磁盘文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 删除文件
     *
     * @param collection 文件存储目录：2017/9
     * @param filename   文件名称：文件名.jpg
     * @param id         文件id
     * @return true:删除成功，false:删除失败
     */
    @Override
    public boolean deleteOne(String collection, String filename, ObjectId id) {
        File file = new File("/" + collection + "/" + filename);
        if (file.exists()) {
            log.debug("删除磁盘文件：" + "/" + collection + "/" + filename);
            return file.delete();
        } else {
            log.error("删除磁盘文件失败...");
            throw new RuntimeException("删除磁盘文件失败...");
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param collection 文件存储目录或集合：2017/9或touch_cloud
     * @param filename   文件名称：文件名.jpg
     * @param id         文件id
     */
    @Override
    public boolean exists(String collection, String filename, ObjectId id) {
        return false;
    }

}
