package com.tianyufighter.function;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public  class ValidCode{
    private int w = 70;
    private int h = 25;
    private Random r = new Random();
    private String[] fontNames = {"黑体", "宋体", "Courier", "Arial",
            "Verdana", "Times", "Tahoma", "Georgia",
            "Atlantic Inline"};
    private String codes = "23456789abcdefghjkmnpqrstuvwxyzABCEFGHIJKLMNPQRSTUVWXYZ";
    private Color bjColor = new Color(255, 255, 255);
    private String text;
    private Color randomColor() {
        int red = r.nextInt(200);
        int green = r.nextInt(200);
        int blue = r.nextInt(200);
        return new Color(red, green, blue);
    }
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt();
        int size = r.nextInt(15) + 15;
        return new Font(fontName, style, size);
    }
    private void drawLine(BufferedImage image) {
        int num = r.nextInt(15);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, w, h);
        for(int i = 0; i < num; i++) {
            graphics.setColor(randomColor());
            graphics.drawLine(r.nextInt(w), r.nextInt(h), r.nextInt(w), r.nextInt(h));
        }
    }
    private char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bjColor);
        g2.fillRect(0, 0, w, h);
        return image;
    }
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        drawLine(image);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            String s = randomChar() + "";
            sb.append(s);
            float x = i * 1.0F * w / 4;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, x, h - 5);
        }
        this.text = sb.toString();
//        drawLine(image);
        return image;
    }
    // 获取生成的验证码的内容
    public String getText() {
        return this.text;
    }
    public static void output (BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }

}
