package com.example.look.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by mac on 15/11/3.
 */
public class QRCodeUtils {

    /**
     * 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createQRCodeImage(String content, int width, int height, String margin) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }


            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = Color.BLACK;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = Color.WHITE;// 白色色块像素设置
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }
}
