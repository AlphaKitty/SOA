package com.proshine.base.common.utils;

import com.proshine.base.common.constant.CommonConstant;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * 图片处理工具类
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ImageUtil {

    /**
     * 获取指定图片的尺寸
     *
     * @param image_path 图片文件路径
     * @return {@link Dimension} 尺寸
     */
    @SuppressWarnings("rawtypes")
    public static Dimension getPix(String image_path) throws IOException {

        File file = new File(image_path);

        if (file.exists() && file.isFile()) {

            try {
                BufferedImage ex = ImageIO.read(file);
                return new Dimension(ex.getWidth(), ex.getHeight());
            } catch (IIOException ex) {
                FileImageInputStream imageIputStream = null;

                Dimension dim;
                try {
                    imageIputStream = new FileImageInputStream(file);
                    ImageReader reader = null;
                    Iterator readers = ImageIO.getImageReaders(imageIputStream);

                    while (readers.hasNext()) {
                        ImageReader ird = (ImageReader) readers.next();
                        if (ird.canReadRaster()) {
                            reader = ird;
                            break;
                        }
                    }

                    if (reader == null) {
                        throw new IllegalStateException("invalid image file: " + image_path);
                    }

                    reader.setInput(imageIputStream);
                    dim = new Dimension(reader.getWidth(0), reader.getHeight(0));
                } catch (IOException exx) {
                    throw new IllegalStateException("read image[CMYK] failed: " + image_path, exx);
                } finally {
                    if (imageIputStream != null) {
                        imageIputStream.close();
                    }

                }

                return dim;
            } catch (IOException ex) {
                throw new IllegalStateException("read image failed: " + image_path, ex);
            }
        } else {
            throw new IllegalArgumentException("invalid image file path: " + image_path);
        }
    }

    /**
     * 转换图片尺寸
     *
     * @param src    源图片路径
     * @param dest   目标图片路径
     * @param width  目标图片尺寸宽度
     * @param height 目标图片尺寸高度
     * @return true: 转换成功
     * false: 转换失败
     */
    @SuppressWarnings("rawtypes")
    public static boolean scaledTo(String src, String dest, int width, int height) {
        if (width > 0 && height > 0) {
            File srcFile = new File(src);
            if (srcFile.exists() && srcFile.isFile()) {
                try {
                    BufferedImage bufferedImage;

                    try {
                        bufferedImage = ImageIO.read(srcFile);
                    } catch (IIOException ex) {

                        try (FileImageInputStream destImage = new FileImageInputStream(srcFile)) {
                            ImageReader reader = null;

                            for (Iterator destFile = ImageIO.getImageReaders(destImage); destFile.hasNext(); reader = null) {
                                reader = (ImageReader) destFile.next();
                                if (reader.canReadRaster()) {
                                    break;
                                }
                            }

                            if (reader == null) {
                                throw new IllegalStateException("get image reader failed: " + src);
                            }

                            reader.setInput(destImage);
                            bufferedImage = new BufferedImage(reader.getWidth(0), reader.getHeight(0), 2);
                            bufferedImage.getRaster().setRect(reader.readRaster(0, null));
                        } catch (IOException exx) {
                            throw new IllegalStateException("image reader io failed: " + src, exx);
                        }
                    }

//                    int colorType = bufferedImage.getType();
                    byte colorType1 = 1;
                    BufferedImage destImage = new BufferedImage(width, height, colorType1);
                    Graphics graphic = destImage.getGraphics();

                    try {
                        graphic.drawImage(bufferedImage, 0, 0, width, height,
                                0, 0, bufferedImage.getWidth(null), bufferedImage.getHeight(null), null);
                    } finally {
                        graphic.dispose();
                    }

                    File destFile1 = new File(dest);
                    destFile1.getParentFile().mkdirs();
                    if (!destFile1.exists()) {
                        destFile1.createNewFile();
                    }

                    try (FileOutputStream destFileStream = new FileOutputStream(destFile1)) {
                        Iterator<ImageWriter> iterator =
                                ImageIO.getImageWritersByFormatName("jpg");
                        ImageWriter imageWriter = iterator.next();
                        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
                        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        imageWriteParam.setCompressionQuality(1.0f);
                        ImageOutputStream imageOutputStream =
                                new MemoryCacheImageOutputStream(destFileStream);
                        imageWriter.setOutput(imageOutputStream);
                        IIOImage iioimage = new IIOImage(destImage, null, null);
                        imageWriter.write(null, iioimage, imageWriteParam);
                        imageOutputStream.flush();
                    }

                    return true;
                } catch (IOException ex) {
                    throw new IllegalStateException("convert image failed: " + src, ex);
                }
            } else {
                throw new IllegalArgumentException("invalid image file path: " + src);
            }
        } else {
            throw new IllegalArgumentException("invalid width[" + width + "] or height[" + height + "]");
        }
    }

    /**
     * 截取视频帧
     *
     * @param video  视频源地址
     * @param dest   图片保存路径
     * @param pos    帧位置
     * @param width  截取宽度
     * @param height 截取高度
     * @return true: 截取成功
     * false: 截取失败
     */
    public static boolean captureVideoImage(String video, String dest, int pos, int width, int height) {
        String ffmpeg = CommonConstant.getDefaultFfmpeg();
        if (width > 0 && height > 0) {
            assert ffmpeg != null;
            File ffmpegFile = new File(ffmpeg);
            if (ffmpegFile.exists() && ffmpegFile.isFile()) {
                File destFile = new File(dest);
                destFile.getParentFile().mkdirs();
                try {
                    Runtime ioex = Runtime.getRuntime();
                    Process process;

                    if (OsCheck.getOperatingSystemType().equals(OsCheck.OSType.Linux)) {
                        String commandLinux = ffmpeg + " -i " + video + " -y -f image2 -ss " + pos + " -t 0.001 " + dest;
                        process = ioex.exec(commandLinux);
                    } else {
                        String commandWindows = "\"" + ffmpeg + "\"" + " -i \"" + video + "\" -y -f image2 -ss " + pos + " -t 0.001 \"" + dest + "\"";
                        process = ioex.exec(commandWindows);
                    }
                    InputStream in = process.getInputStream();
                    InputStream err = process.getErrorStream();
                    int exitValue = -1;
                    int pass = 0;

                    while (pass <= 15000) {
                        try {
                            while (in.available() > 0) {
                                in.read();
                            }

                            while (err.available() > 0) {
                                err.read();
                            }

                            exitValue = process.exitValue();
                            break;
                        } catch (IllegalThreadStateException var20) {
                            try {
                                Thread.sleep(200L);
                                pass += 200;
                            } catch (InterruptedException var19) {
                                throw new IllegalStateException("image capture error: " + video, var19);
                            }
                        }
                    }

                    if (pass > 15000) {
                        process.destroy();
                    }

                    return exitValue == 0;
                } catch (IOException ex) {
                    throw new IllegalStateException("capture image failed: " + video, ex);
                }
            } else {
                throw new IllegalArgumentException("invalid ffmpeg: " + ffmpeg);
            }
        } else {
            throw new IllegalArgumentException("invalid width[" + width + "] or height[" + height + "]");
        }
    }

    /**
     * 改变图片的大小到宽为size，然后高随着宽等比例变化
     *
     * @param is     上传的图片的输入流
     * @param os     改变了图片的大小后，把图片的流输出到目标OutputStream
     * @param size   新图片的宽
     * @param format 新图片的格式
     * @throws IOException IOException
     */
    public static OutputStream resizeImage(InputStream is, OutputStream os, int size, String format) throws IOException {
        BufferedImage prevImage = ImageIO.read(is);
        double width = prevImage.getWidth();
        double height = prevImage.getHeight();
        int newWidth = (int) width;
        int newHeight = (int) height;
        if (size < width) {
            double percent = size / width;
            newWidth = (int) (width * percent);
            newHeight = (int) (height * percent);
        }
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
        ImageIO.write(image, format, os);
        os.flush();

        return os;
    }

}
