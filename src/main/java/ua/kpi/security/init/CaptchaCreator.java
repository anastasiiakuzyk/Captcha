package ua.kpi.security.init;

import lombok.Getter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CaptchaCreator {
    private static final String SET_OF_CHARACTERS = "ABDEFGHKLMNOPRSTUVXYQabdefhkmnoprtuvxy12345678";

    private static final String PNG_FORMAT = "png";

    private static final String FONT_NAME = "Segoe Script";

    @Getter
    private String code;

    @Getter
    private byte[] picture;

    public void create() throws IOException {
        int width = 210;
        int height = 80;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(getRandomColor());
        g2d.fillRect(0, 0, width, height);

        for (int i = 0; i < 2; i++) {
            g2d.setColor(getRandomColor());
            int randY1 = (int) (Math.random() * height);
            int randY2 = (int) (Math.random() * height);
            g2d.drawLine(0, randY1, width, randY2);
        }
        g2d.setColor(getRandomColor());
        g2d.drawLine((int)(Math.random() * width), 0, (int)(Math.random() * width), height);

        // Returns all available fonts
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        StringBuilder captchaCode = new StringBuilder();

        int xPos = 10;
        for (int i = 0; i < 6; i++) {
            int randomCharPos = (int) (Math.random() * SET_OF_CHARACTERS.length());
            String randomChar = Character.toString(SET_OF_CHARACTERS.charAt(randomCharPos));
            captchaCode.append(randomChar);

            int yPos = 40 + (int) (Math.random() * 25);
            g2d.setFont(new Font(FONT_NAME, Font.BOLD | Font.ITALIC, 35));
            g2d.setColor(new Color(130, 130, 130));
            g2d.drawString(randomChar, xPos + 2, yPos + 2);
            g2d.setColor(getRandomColor());
            g2d.drawString(randomChar, xPos, yPos);
            xPos += 30;
        }
        addNoise(bufferedImage);

        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose();

        code = captchaCode.toString();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, PNG_FORMAT, baos);
        picture = baos.toByteArray();
    }

    private void saveAsFile(BufferedImage bufferedImage, String fileName) throws IOException {
        File file = new File(fileName);
        ImageIO.write(bufferedImage, PNG_FORMAT, file);
    }

    private void addNoise(BufferedImage bufferedImage) {
        for (int x = 1; x < bufferedImage.getWidth(); x++) {
            for (int y = 1; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                int newRed = getNewColor(currentColor.getRed());
                int newGreen = getNewColor(currentColor.getGreen());
                int newBlue = getNewColor(currentColor.getBlue());
                bufferedImage.setRGB(x, y, new Color(newRed, newGreen, newBlue).getRGB());
            }
        }
    }

    private int getNewColor(int currentColor) {
        int radius = 35;
        int lowValue = currentColor - radius;
        int highValue = currentColor + radius;
        int newValue = lowValue + (int) (Math.random() * (highValue - currentColor));
        if (newValue < 0)
            newValue = 0;
        else if (newValue > 255)
            newValue = 255;
        return newValue;
    }

    private Color getRandomColor() {
        int randRed = (int) (Math.random() * 256);
        int randGreen = (int) (Math.random() * 256);
        int randBlue = (int) (Math.random() * 256);
        return new Color(randRed, randGreen, randBlue);
    }
}
