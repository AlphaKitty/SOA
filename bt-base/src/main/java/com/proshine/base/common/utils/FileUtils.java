package com.proshine.base.common.utils;


import com.proshine.base.common.constant.CommonConstant;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.ContextLoader;

import java.io.*;
import java.util.Objects;
import java.util.Random;

/**
 * 文件处理工具类.
 *
 * @author gaosq
 */
public class FileUtils {

    /**
     * 关闭IO
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filename 文件路径
     */
    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }

    /**
     * 删除目录下的子文件
     */
    public static void deleteFileByDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                file.delete();
            }
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    @SuppressWarnings("unused")
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     */
    public static boolean isFileExist(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 写入文件
     *
     * @param filename 文件名称
     * @param content  文件内容
     * @param append   boolean if <code>true</code>, then data will be written
     *                 to the end of the file rather than the beginning.
     */
    public static boolean writeFile(String filename, String content, boolean append) {
        boolean isSuccess = false;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filename, append));
            bufferedWriter.write(content);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bufferedWriter);
        }
        return isSuccess;
    }

    /**
     * 写入文件
     *
     * @param inputStream 文件流
     */
    public static boolean writeFile(String filename, InputStream inputStream) {
        boolean isSuccess = false;
        try {
            //将输入流写入磁盘
            OutputStream outputStream = new FileOutputStream(new File(filename));
            IOUtils.copy(inputStream, outputStream);
            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 读取文件
     *
     * @param filename 文件路径
     */
    public static String readFile(String filename) {
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        String str = null;
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(filename));
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bufferedReader);
        }
        return str;
    }

    /**
     * 获取项目路径
     *
     * @return 路径
     */
    public static String getBasePath() {
        String basePath = Objects.requireNonNull(Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext()).getServletContext()).getRealPath("");
        if (!basePath.endsWith(File.separator)) {
            basePath += File.separator;
        }
        return basePath;
    }

    /**
     * 将视频或者MP3转为流媒体播放文件
     *
     * @param video 文件位置
     * @param dest  保存位置
     */
    public static boolean transformTs(String video, String dest) {
        dest = dest + "\\temp.ts";
        String ffmpeg = CommonConstant.getDefaultFfmpeg();
        File ffmpegFile = null;
        if (ffmpeg != null) {
            ffmpegFile = new File(ffmpeg);
        }
        if (ffmpegFile != null) {
            if (ffmpegFile.exists() && ffmpegFile.isFile()) {
                File destFile = new File(dest);
                destFile.getParentFile().mkdirs();

                String commandWindows = ffmpeg + " -i " + video + "-f mpegts -acodec libmp3lame -ar 48000 -ab 128k -vcodec libx264 -b 96k" +
                        " -flags +loop -cmp +chroma -partitions +parti4x4+partp8x8+partb8x8 -subq 5 -trellis 1 -refs 1 -coder 0" +
                        " -me_range 16 -keyint_min 25 -sc_threshold 40 -me_range 16 -keyint_min 25 -sc_threshold 40 " +
                        "-i_qfactor 0.71 -bt 200k -maxrate 96k -bufsize 96k -rc_eq 'blurCplx^(1-qComp)'" +
                        " -qcomp 0.6 -qmin 10 -qmax 51 -qdiff 4 -level 30 -aspect 320:240 -g 30 -async 2 " + dest;

                try {
                    Runtime ioex = Runtime.getRuntime();
                    Process process;
                    if (OsCheck.getOperatingSystemType().equals(OsCheck.OSType.Linux)) {
                        String commandLinux = "";
                        process = ioex.exec(commandLinux);
                    } else {
                        // String commandWindows = "\"" + ffmpeg + "\"" + " -i \"" + video + "\" -y -f image2 -ss " + pos + " -t 0.001 \"" + dest + "\"";
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
                    //释放进程
                    process.getOutputStream().close();
                    process.getInputStream().close();
                    process.getErrorStream().close();
                    return exitValue == 0;
                } catch (IOException ex) {
                    throw new IllegalStateException("capture image failed: " + video, ex);
                }
            } else {
                throw new IllegalArgumentException("invalid ffmpeg: " + ffmpeg);
            }
        }
        return false;
    }

    /**
     * 生成文件名.
     */
    public static String getRandomFilename(String prefix) {
        StringBuilder randomFilename = new StringBuilder();

        if (StringUtils.isEmpty(prefix)) {
            prefix = "BT";
        }

        Long currentTimeMillis = System.currentTimeMillis();
        randomFilename = new StringBuilder(prefix + currentTimeMillis);

        // 生成5位数的随机数
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            randomFilename.append(random.nextInt(10));
        }

        return randomFilename.toString();
    }

    public static void main(String[] args) {


    }

}