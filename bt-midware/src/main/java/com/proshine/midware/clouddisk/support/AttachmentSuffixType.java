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
 * 附件后缀枚举类
 *
 * @author 高孙琼
 */
@JsonSerialize(using = AttachmentSuffixTypeSerializer.class)
@JsonDeserialize(using = AttachmentSuffixTypeDeserializer.class)
public enum AttachmentSuffixType {

    // 图片
    IMG_JPEG(2000, "jpeg"),
    IMG_JPG(2001, "jpg"),
    IMG_GIF(2002, "gif"),
    IMG_BMP(2003, "bmp"),
    IMG_PNG(2004, "png"),
    IMG_TIF(2005, "tif"),

    // Office Word
    OFC_DOC(2100, "doc"),
    OFC_DOCX(2101, "docx"),

    // Office Excel
    OFC_XLS(2200, "xls"),
    OFC_XLSX(2201, "xlsx"),

    // Office Power Point
    OFC_PPT(2300, "ppt"),
    OFC_PPTX(2301, "pptx"),

    // PDF
    OTH_PDF(2400, "pdf"),

    // 音频
    ADU_WMA(2500, "wma"),
    ADU_MP3(2501, "mp3"),
    ADU_RA(2502, "ra"),
    ADU_AMR(2503, "amr"),
    ADU_FLAC(2504, "flac"),
    ADU_AC3(2505, "ac3"),
    ADU_WAV(2506, "wav"),


    // 视频
    VID_AVI(2600, "avi"),
    VID_MKV(2601, "mkv"),
    VID_WMV(2602, "wmv"),
    VID_MP4(2603, "mp4"),
    VID_RM(2604, "rm"),
    VID_RMVB(2605, "rmvb"),
    VID_3GP(2606, "3gp"),
    VID_MOV(2607, "mov"),
    VID_MPG(2608, "mpg"),
    VID_SWF(2609, "swf"),
    VID_TS(2610, "ts"),
    VID_TP(2611, "tp"),
    VID_ASF(2612, "asf"),
    ADU_FLV(2613, "flv"),


    // txt文件
    OTH_TXT(2700, "txt"),

    UNKNOWN(9999, "unknown");

    // 成员变量
    private int id;
    private String desc;

    // 构造方法
    AttachmentSuffixType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    // 静态方法
    public static AttachmentSuffixType fromTypeId(final int id) {
        switch (id) {
            // 图片
            case 2000:
                return IMG_JPEG;
            case 2001:
                return IMG_JPG;
            case 2002:
                return IMG_GIF;
            case 2003:
                return IMG_BMP;
            case 2004:
                return IMG_PNG;

            // Office Word
            case 2100:
                return OFC_DOC;
            case 2101:
                return OFC_DOCX;

            // Office Excel
            case 2200:
                return OFC_XLS;
            case 2201:
                return OFC_XLSX;

            // Office Power Point
            case 2300:
                return OFC_PPT;
            case 2301:
                return OFC_PPTX;

            // PDF
            case 2400:
                return OTH_PDF;

            // 音频
            case 2500:
                return ADU_WMA;
            case 2501:
                return ADU_MP3;
            case 2502:
                return ADU_RA;
            case 2503:
                return ADU_AMR;
            case 2504:
                return ADU_FLAC;
            case 2505:
                return ADU_AC3;
            case 2506:
                return ADU_WAV;


            // 视频
            case 2600:
                return VID_AVI;
            case 2601:
                return VID_MKV;
            case 2602:
                return VID_WMV;
            case 2603:
                return VID_MP4;
            case 2604:
                return VID_RM;
            case 2605:
                return VID_RMVB;
            case 2606:
                return VID_3GP;
            case 2607:
                return VID_MOV;
            case 2608:
                return VID_MPG;
            case 2609:
                return VID_SWF;
            case 2610:
                return VID_TS;
            case 2611:
                return VID_TP;
            case 2612:
                return VID_ASF;
            case 2613:
                return ADU_FLV;
            case 2700:
                return OTH_TXT;

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

    public void desc(String desc) {
        this.desc = desc;
    }
}

class AttachmentSuffixTypeSerializer extends JsonSerializer<AttachmentSuffixType> {

    @Override
    public void serialize(AttachmentSuffixType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeNumber(value.id());
        generator.writeFieldName("desc");
        generator.writeString(value.desc());
        generator.writeEndObject();
    }
}

class AttachmentSuffixTypeDeserializer extends JsonDeserializer<AttachmentSuffixType> {

    @Override
    public AttachmentSuffixType deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        String id = null;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            if (StringUtils.equals(parser.getText(), "id")) {
                parser.nextToken();
                id = parser.getText();
            }
        }
        if (id != null) {
            return AttachmentSuffixType.fromTypeId(Integer.parseInt(id));
        }
        return null;
    }
}