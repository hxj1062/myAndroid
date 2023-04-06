package com.example.look.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *@description: 使用字节流复制文件
 */
public class ChangeFileName {


    public static void main(String[] args) throws Exception {

        File file = new File("e:/aaHostFriends");
        File[] subFiles = file.listFiles();
        InputStream is = null;
        byte[] buffer = null;

        for (int i = 0; i < subFiles.length; i++) {
            try {
                is = new FileInputStream(subFiles[i]);
                int length = is.available();
                buffer = new byte[length];
                is.read(buffer);

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            // 新文件名为newName
            String newName = "";

            if (i < 9) {
                newName = "TheBigBangTheory.1stE0" + (i + 1) + ".mp4";
            } else {
                newName = "TheBigBangTheory.1stE" + (i + 1) + ".mp4";
            }

            System.out.println(newName);
            File file2 = new File("e:/TheBigBangTheoryS01/" + newName);
            OutputStream os = null;
            try {
                os = new FileOutputStream(file2);
                os.write(buffer);
                is.close();
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 将之前的文件删除
            subFiles[i].delete();
        }

    }
}
