package com.vooft.pilot.common;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 30.05.13
 * Time: 2:08
 * To change this template use File | Settings | File Templates.
 */
public class ImageHelper {
    public static boolean hasThumbnail(int image_id, int width) {
        File f = new File(getCarImagePath(image_id, width));
        return f.exists();
    }

    public static Resource getImageResource(int image_id, int width) {
        File f = new File(getCarImagePath(image_id, width));
        if(f.exists()==false)
            return createStubResource();

        return new FileResource(f);
    }

    public static Resource getImageResource(int image_id, int width, byte img[]) {
        File f = new File(getCarImagePath(image_id, width));
        if(f.exists())
            return getImageResource(image_id, width);

        f.getParentFile().mkdirs();

        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(new ByteArrayInputStream(img));

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));

            Dimension d = getScaledDimension(image.getWidth(), image.getHeight(), width, width);

            Image scaled = image.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);

            BufferedImage resizedImage = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(scaled, 0, 0, d.width, d.height, null);
            g.dispose();

            resizedImage = makeRoundedCorner(resizedImage, 20);

            ImageIO.write(resizedImage, "png", f);

            return getImageResource(image_id, width);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createStubResource();
    }

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    public static String createUrlPath(int image_id, int width) {
        return String.format("car_%d_%dpx.png", image_id, width);
    }

    public static String getCarImagePath(int image_id, int width) {
        return new StringBuilder()
                .append("cache").append(File.separator)
                .append("car_img").append(File.separator)
                .append(String.format("%d-%dpx", image_id, width))
                .append(".png").toString();
    }

    public static Resource createStubResource() {
        return new ThemeResource("img/stub.png");
    }

    public static Dimension getScaledDimension(int original_width, int original_height, int bound_width, int bound_height) {
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
