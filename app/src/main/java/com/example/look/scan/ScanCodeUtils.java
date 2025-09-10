package com.example.look.scan;

import android.graphics.Bitmap;
import android.graphics.Color;

import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class ScanCodeUtils {

    /**
     * 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createQRCodeImage(String content, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            int w = matrix.getWidth();
            int h = matrix.getHeight();
            int[] rawData = new int[w * h];
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int color = Color.WHITE;
                    if (matrix.get(i, j)) {
                        color = Color.BLACK;
                    }
                    rawData[i + (j * w)] = color;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            bitmap.setPixels(rawData, 0, w, 0, 0, w, h);

            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            String character_set, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        QRCodeWriter writer = new QRCodeWriter();
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }


            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);


            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


}
