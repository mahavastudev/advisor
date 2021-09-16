package com.mahavastu.advisor.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class FileUtility
{
    public static void updateResponseWithFilePath(HttpServletResponse response, String filePath)
    {
        final String contentType = response.getContentType();

        final File file = new File(filePath);

        response.setContentType("application/pdf");

        if (file.length() <= Integer.MAX_VALUE)
        {
            response.setContentLength((int) file.length());
        }

        response.setHeader("Content-Disposition", filePath);

        FileInputStream in = null;

        try
        {
            in = new FileInputStream(file);

            final OutputStream os = response.getOutputStream();
            IOUtils.copyLarge(in, os);
            response.flushBuffer();
        }
        catch (Exception e)
        {
            response.setContentType(contentType);
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void updateResponseWithFileBytes(HttpServletResponse response, byte[] fileBytes)
    {
        final String contentType = response.getContentType();
        
        response.setContentType("application/pdf");

        response.setHeader("Content-Disposition", "Site-Owner-Patri.pdf");

        ByteArrayInputStream in = null;

        try
        {
            in = new ByteArrayInputStream(fileBytes);

            final OutputStream os = response.getOutputStream();
            IOUtils.copyLarge(in, os);
            response.flushBuffer();
        }
        catch (Exception e)
        {
            response.setContentType(contentType);
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
