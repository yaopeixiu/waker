package com.spring.cloud.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureUtils {

    private static Graphics2D g = null;

    /**
     * 导入本地图片到缓冲区
     */
    public  BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * 传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的
     *
     *            用户头像地址
     * @return
     * @throws IOException
     */
    public  BufferedImage convertCircular(BufferedImage bi1) throws IOException {

//		BufferedImage bi1 = ImageIO.read(new File(url));

        // 这种是黑色底的
//		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
//        BufferedImage.TYPE_INT_RGB);

        // 透明底的图片
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        // 设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }
    public  BufferedImage getHead(String imageURL) {
        BufferedImage convertImage=null;
        try {
            // 是头像地址
            // 获取图片的流
            BufferedImage url =
                    getRemoteBufferedImage(imageURL);
            // 处理图片将其压缩成正方形的小图
//            convertImage = scaleByPercentage(url, 210, 210);
            // 裁剪成圆形 （传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的）
            convertImage = convertCircular(url);
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertImage;
    }
    /**
     * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
     *
     * @param inputImage
     * @throws IOException
     *             return
     */
    public  BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int
            newHeight) throws Exception {
        // 获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        // 开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // 使用高质量压缩
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 获取远程网络图片信息
     * @param imageURL
     * @return
     */
    public  BufferedImage getRemoteBufferedImage(String imageURL) {
        URL url = null;
        InputStream is = null;
        BufferedImage bufferedImage = null;
        try {
            url = new URL(imageURL);
            is = url.openStream();
            bufferedImage = ImageIO.read(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("imageURL: " + imageURL + ",无效!");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("imageURL: " + imageURL + ",读取失败!");
            return null;
        } finally {
            try {
                if(is!=null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("imageURL: " + imageURL + ",流关闭异常!");
                return null;
            }
        }
        return bufferedImage;
    }


    public  BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d,Integer x,Integer y) {

        try {
            int w = b.getWidth()*5;
            int h = b.getHeight()*5;
            g = d.createGraphics();
            g.drawImage(b, x, y, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return d;
    }

    /**
     * 生成新图片到本地
     */
    public  void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "png", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
//        BufferedImage d = PictureUtils.loadImageLocal("D:/yuantu.png");
//        BufferedImage b = PictureUtils.getHead("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLhqrmN8H15hTaz4S16L0Mmia4P3XdibYibrtmagQyoTv6EXGkFSZepFCD0rT0JOe2KicWUgvQTsDqOXw/132");
//        PictureUtils.writeImageLocal("D:/yuantu.png", PictureUtils.modifyImagetogeter(b, d,308,619));
//        System.out.println("success");
    }

    /**
     * 小程序合成海报
     * @param filePath
     * @param url
     * @param type
     */
    public  void composeImg(String filePath,String url,Integer type){
        BufferedImage d = null;
        PictureUtils pictureUtils= new PictureUtils();
        d = pictureUtils.loadImageLocal("图片地址");

        BufferedImage c = pictureUtils.getHead(url);
        BufferedImage b = pictureUtils.loadImageLocal(filePath);
        //小程序合成
        pictureUtils.modifyImagetogeter(b, d,250,900);
        //头像hecheng
        pictureUtils.writeImageLocal(filePath, pictureUtils.modifyImagetogeter(c, d,308,619));
    }

    /**
     * app合成海报
     * @param content
     */
    public static   InputStream composeImgApp(String content,String imgurl)   {
        BufferedImage d = null;
        PictureUtils pictureUtils= new PictureUtils();
            d = pictureUtils.getRemoteBufferedImage(imgurl);
        BufferedImage urlImg= null;
        try {
            urlImg = QrCodeUtils.encode(content, null,true);
            System.out.println(urlImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //合成
        BufferedImage c =pictureUtils.modifyImagetogeter(urlImg, d,178,625);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(c, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream input = new ByteArrayInputStream(os.toByteArray());
      return input;
    }






}
