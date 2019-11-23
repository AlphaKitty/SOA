package com.proshine.midware.clouddisk.support;


import static com.proshine.midware.clouddisk.support.AttachmentSuffixType.*;

/**
 * 根据后缀名转换为对应的类型
 *
 * @author 高孙琼
 */
public class AttachmentSuffixConvert {

    private AttachmentSuffixConvert() {
    }

    public static AttachmentType convert(String suffix) {

        AttachmentType result;
        //都小写化
        suffix = suffix.toLowerCase();
        // 图片
        if (IMG_JPEG.desc().equals(suffix)
                || IMG_JPG.desc().equals(suffix)
                || IMG_GIF.desc().equals(suffix)
                || IMG_BMP.desc().equals(suffix)
                || IMG_PNG.desc().equals(suffix)
                || IMG_TIF.desc().equals(suffix)) {
            result = AttachmentType.IMAGE;
        }
        // Office Word
        else if (OFC_DOC.desc().equals(suffix)
                || OFC_DOCX.desc().equals(suffix)) {
            result = AttachmentType.WORD;
        }
        // Office Excel
        else if (OFC_XLS.desc().equals(suffix)
                || OFC_XLSX.desc().equals(suffix)) {
            result = AttachmentType.WORD;
        }
        // Office Power Point
        else if (OFC_PPT.desc().equals(suffix)
                || OFC_PPTX.desc().equals(suffix)) {
            result = AttachmentType.WORD;
        }
        // PDF
        else if (OTH_PDF.desc().equals(suffix)) {
            result = AttachmentType.WORD;
        }
        // 音频
        else if (ADU_WMA.desc().equals(suffix)
                || ADU_MP3.desc().equals(suffix)
                || ADU_RA.desc().equals(suffix)
                || ADU_AMR.desc().equals(suffix)
                || ADU_FLAC.desc().equals(suffix)
                || ADU_AC3.desc().equals(suffix)
                || ADU_WAV.desc().equals(suffix)
        ) {
            result = AttachmentType.AUDIO;
        }
        // txt文件
        else if (OTH_TXT.desc().equals(suffix)) {
            result = AttachmentType.WORD;
        }
        // 视频
        else if (VID_AVI.desc().equals(suffix)
                || VID_MKV.desc().equals(suffix)
                || VID_WMV.desc().equals(suffix)
                || VID_MP4.desc().equals(suffix)
                || VID_RM.desc().equals(suffix)
                || VID_RMVB.desc().equals(suffix)
                || VID_3GP.desc().equals(suffix)
                || VID_MOV.desc().equals(suffix)
                || VID_MPG.desc().equals(suffix)
                || VID_SWF.desc().equals(suffix)
                || VID_TS.desc().equals(suffix)
                || VID_TP.desc().equals(suffix)
                || VID_ASF.desc().equals(suffix)
                || ADU_FLV.desc().equals(suffix)) {
            result = AttachmentType.VIDEO;
        }
        // 其它文件
        else {
            result = AttachmentType.UNKNOWN;
        }
        return result;
    }
}
