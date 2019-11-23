package com.proshine.base.common.utils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.proshine.base.common.constant.CommonConstant;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * @author 陈江华
 */
public class DocToPdf {

    /**
     * OpenOffice window cmd
     */
    public static final String OFFICE_WINDOW_COMMAND = " -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard ";
    /**
     * OpenOffice linux cmd
     */
    public static final String OFFICE_LINUX_COMMAND = " --headless --accept=\"socket,host=127.0.0.1,port=8100;urp;\" --nofirststartwizard &";

    public static File createPdf(File docFile) throws IOException {
        File pdfFile = File.createTempFile("tempDoc", ".pdf");
        // connect to an OpenOffice.org instance running on port 8100
        String openOfficeHOME;
        String tempCommand;

        String ex = Objects.requireNonNull(CommonConstant.class.getClassLoader().getResource("/")).getPath();
        ex = URLDecoder.decode(ex, "UTF-8");
        File f = new File(ex);
        f = f.getParentFile().getParentFile();
        switch (OsCheck.getOperatingSystemType()) {
            case Linux:
                openOfficeHOME = f.getAbsolutePath()
                        + File.separatorChar + "tools"
                        + File.separatorChar + "openOffice"
                        + File.separatorChar + "linux_x86_x64"
                        + File.separatorChar + "program"
                        + File.separatorChar + "soffice";
                tempCommand = OFFICE_LINUX_COMMAND;
                break;
            case Windows:
                openOfficeHOME = f.getAbsolutePath()
                        + File.separatorChar + "tools"
                        + File.separatorChar + "openOffice"
                        + File.separatorChar + "win_x86_x64"
                        + File.separatorChar + "program"
                        + File.separatorChar + "soffice.exe";
                tempCommand = OFFICE_WINDOW_COMMAND;
                break;
            default:
                openOfficeHOME = f.getAbsolutePath()
                        + File.separatorChar + "tools"
                        + File.separatorChar + "openOffice"
                        + File.separatorChar + "win_x86_x64"
                        + File.separatorChar + "program"
                        + File.separatorChar + "soffice.exe";
                tempCommand = OFFICE_WINDOW_COMMAND;
        }

        // 启动OpenOffice的服务
        String command = openOfficeHOME + tempCommand;

        System.out.println(command);
        Process pro = Runtime.getRuntime().exec(command);

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();
        // convert
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(docFile, pdfFile);
        System.out.println(pdfFile.getCanonicalPath());
        connection.disconnect();
        pro.destroy();
        return pdfFile;
    }

}

