package com.ilzf.base.utils;

import java.io.*;
import java.util.UUID;

@SuppressWarnings("unused")
public class SuperKitUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUploadFilePath() {
        String res = System.getProperty("user.dir") + "\\files\\";
        File dir = new File(res);
        int count = 0;
        if (!dir.exists()) {
            while (count < 10 && !dir.mkdir()) {
                count++;
            }
        }
        return res;
    }

    public static void saveFileToDir(String name, InputStream is) {
        try {
            OutputStream os = new FileOutputStream(new File(getUploadFilePath() + name));
            byte[] bb = new byte[100];
            int count = 0;
            while ((count = is.read(bb)) > -1) {
                os.write(bb, 0, count);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(getUploadFilePath());
    }
}
