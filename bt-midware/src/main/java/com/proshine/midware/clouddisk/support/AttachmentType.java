package com.proshine.midware.clouddisk.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * 附件类型枚举类
 *
 * @author 高孙琼
 */
@JsonSerialize(using = AttachmentTypeSerializer.class)
@JsonDeserialize(using = AttachmentTypeDeserializer.class)
public enum AttachmentType {
    NULL(0, "文件夹"),
    IMAGE(1, "图片"),
    WORD(2, "文档"),
    AUDIO(3, "音频"),
    VIDEO(4, "视频"),
    UNKNOWN(99, "其他");

    // 成员变量
    private int id;
    private String desc;

    // 构造方法
    AttachmentType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    // 静态方法
    public static AttachmentType fromTypeId(final int id) {
        switch (id) {
            case 0:
                return NULL;
            case 1:
                return IMAGE;
            case 2:
                return WORD;
            case 3:
                return AUDIO;
            case 4:
                return VIDEO;
            default:
                return UNKNOWN;
        }
    }

    public int id() {
        return id;
    }

    public String desc() {
        return desc;
    }
}

class AttachmentTypeSerializer extends JsonSerializer<AttachmentType> {

    @Override
    public void serialize(AttachmentType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeNumber(value.id());
        generator.writeFieldName("desc");
        generator.writeString(value.desc());
        generator.writeEndObject();
    }
}

class AttachmentTypeDeserializer extends JsonDeserializer<AttachmentType> {

    @Override
    public AttachmentType deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        String id = null;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (StringUtils.equals(parser.getText(), "id")) {
                parser.nextToken();
                id = parser.getText();
            }
        }
        if (id != null) {
            return AttachmentType.fromTypeId(Integer.parseInt(id));
        }
        return null;
    }
}
