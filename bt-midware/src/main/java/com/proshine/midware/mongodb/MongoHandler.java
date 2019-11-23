package com.proshine.midware.mongodb;

import com.alibaba.dubbo.config.annotation.Service;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.proshine.expo.midware.file.FileExpo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * MongoDB文件管理服务实现类
 *
 * @author 高孙琼
 */
@Log4j2
@Component
@Service(group = "mongo")
public class MongoHandler implements FileExpo {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 读取文件
     *
     * @param collection 集合：touch_cloud
     * @param filename   文件名称(允许为空)：文件名.jpg
     * @param id         文件id
     * @return document
     */
    @Override
    public Object getOne(String collection, String filename, ObjectId id) {
        try {
            log.debug("正在读取mongodb文件：collection=" + collection + ",filename=" + filename + ",id=" + id);
            MongoDatabase db = mongoTemplate.getDb();
            return db.getCollection(collection).find(new Document("_id", id)).first();
        } catch (Exception e) {
            log.error("读取mongodb文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 写入文件流
     *
     * @param collection  集合：touch_cloud
     * @param filename    文件名称：文件名.jpg
     * @param id          文件id
     * @param inputStream 文件流
     * @return 没什么用 不知道为啥要加返回值
     */
    @Override
    public String addOne(String collection, String filename, ObjectId id, InputStream inputStream) {
        try {
            if (exists(collection, filename, id)) {
                log.info("文件[" + filename + "/" + id + "]已在" + collection + "中存在 无需再次上传");
                return collection;
            }
            log.debug("正在写入mongodb文件：collection=" + collection + ",filename=" + filename + ",md5=" + id);
            String contentType = FilenameUtils.getExtension(filename);

            //spring的gridFsTemplate操作模板并不支持自定义ID，所以采用以下驱动API接口写入
            MongoDatabase db = mongoTemplate.getDb();
            Map<String, Object> map = new HashMap<>();
            map.put("_id", id);
            map.put("filename", filename);
            map.put("contentType", contentType);
            db.getCollection(collection).insertOne(new Document(map));
            return collection;
        } catch (Exception e) {
            log.error("写入mongodb文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 删除文件
     *
     * @param collection 集合：touch_cloud
     * @param filename   文件名称(允许为空)：文件名.jpg
     * @param id         文件id
     * @return true:删除成功，false:删除失败
     */
    @Override
    public boolean deleteOne(String collection, String filename, ObjectId id) {
        try {
            log.debug("删除mongodb文件：collection=" + collection + ",filename=" + filename + ",id=" + id);
            MongoDatabase db = mongoTemplate.getDb();
            DeleteResult result = db.getCollection(collection).deleteOne(new Document("_id", id));
            return result.getDeletedCount() == 1;
        } catch (Exception e) {
            log.error("删除mongodb文件失败...", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param collection 集合：touch_cloud
     * @param filename   文件名称(允许为空)：文件名.jpg
     * @param id         文件id
     * @return true表示存在，false表示不存在
     */
    @Override
    public boolean exists(String collection, String filename, ObjectId id) {
        MongoDatabase db = mongoTemplate.getDb();
        long count = db.getCollection(collection).countDocuments(new Document("_id", id));
        return count != 0;
    }

}
