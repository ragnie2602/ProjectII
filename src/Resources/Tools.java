package Resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.regex.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Tools {
    public static ImageIcon BytesToImage(byte[] bytes) {
        return new ImageIcon(bytes);
    }

    public static boolean checkEmail(String email) {
        if (email.isBlank()) {
            return true;
        } else {
            return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
        }
    }

    public static boolean checkPhone(String phoneNumber) {
        if (phoneNumber.isBlank()) {
            return true;
        } else {
            return Pattern.compile("^0\\d{9}$").matcher(phoneNumber).matches();
        }
    }

    public static byte[] imageToBytes(ImageIcon image) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(), image.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();

        image.paintIcon(null, g, 0, 0);
        g.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        return imageInByte;
    }

    public static ImageIcon pickImage() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tệp hình ảnh", ImageIO.getReaderFileSuffixes());
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        fileChooser.setFileFilter(filter);

        if (result == JFileChooser.APPROVE_OPTION) {
            return new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
        }

        return null;
    }

    public static ImageIcon pickImage(int height, int width) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tệp hình ảnh", ImageIO.getReaderFileSuffixes());
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        fileChooser.setFileFilter(filter);

        if (result == JFileChooser.APPROVE_OPTION) {
            return resize(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()), height, width);
        }

        return null;
    }

    public static ImageIcon resize(ImageIcon img, int height, int width) {
        BufferedImage resizeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = resizeImage.getGraphics();
        Image image = img.getImage();

        g.drawImage(image, 0, 0, width, height, null);

        return new ImageIcon(resizeImage);
    }

    public static java.sql.Date toSqlDate(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        try {
            java.util.Date utilDate = inputFormat.parse(date);

            return new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toTime(int second) {
        String str = "" + second / 3600;
        second %= 3600;
        str += ":" + second / 60;
        second %= 60;
        str += ":" + second;

        return str;
    }
}
