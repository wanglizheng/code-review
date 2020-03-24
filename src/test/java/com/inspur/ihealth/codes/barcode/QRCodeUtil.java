package com.inspur.ihealth.codes.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

        private static final String CHARSET     = "utf-8";
        private static final String FORMAT_NAME = "JPG";
        // 二维码尺寸
        private static final int    QRCODE_SIZE = 300;
        // LOGO宽度
        private static final int    WIDTH       = 60;
        // LOGO高度
        private static final int    HEIGHT      = 60;

    /**
     * 解析读取二维码
     *
     * @param qrCodePath 二维码图片路径
     * @return
     */
    public static String decodeQRcode(String qrCodePath) {
        BufferedImage image;
        String qrCodeText = null;
        try {
            image = ImageIO.read(new File(qrCodePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            // 对图像进行解码
            com.google.zxing.Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            qrCodeText = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return qrCodeText;
    }

    /**
     * 解析读取二维码
     *
     * @param base64Str 二维码Base64串
     * @return
     */
    public static String decodeBase64Str(String base64Str) {
        BufferedImage image;
        BufferedImage image1;
        String qrCodeText = null;
        try {
            image = ImageIO.read(Base64Utils.base64ToFile(base64Str));
            //方法二： 获取imge的方法，通过静态方法ImageIO.read(InputStream)
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodeImg = decoder.decodeBuffer(base64Str);
            ByteArrayInputStream decodeImgIs = new ByteArrayInputStream(decodeImg);
            image1 = ImageIO.read(decodeImgIs);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            // 对图像进行解码
            com.google.zxing.Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            qrCodeText = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return qrCodeText;
    }


        public static void main(String[] args) throws Exception{
            String base64 = "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";
            System.out.println(QRCodeUtil.decodeBase64Str(base64));
        }



}
