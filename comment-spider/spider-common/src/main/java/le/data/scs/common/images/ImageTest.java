package le.data.scs.common.images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yangyong3 on 2017/3/10.
 */
public class ImageTest {

    private int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 500) {
            return 1;
        }
        return 0;
    }

    private int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 500) {
            return 1;
        }
        return 0;
    }


    public BufferedImage removeBackground(String imgPath) throws IOException{
        BufferedImage image = ImageIO.read(new File(imgPath));
        int width = image.getWidth();
        int height = image.getHeight();
        int first = 0;
        Color backend = null;
        Color front = null;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(x == 0 && y == 0){
                    if(isWhite(image.getRGB(x,y)) == 1){
                          //大于500 说明背景是白的
                        backend = Color.BLACK;
                        front = Color.WHITE;
                    }else{
                        backend = Color.WHITE;
                        front = Color.BLACK;
                    }
                }
                if (isWhite(image.getRGB(x, y)) == 1) {
                    image.setRGB(x, y, front.getRGB());
                }
                else {
                    image.setRGB(x, y, backend.getRGB());
                }
            }
        }
        return image;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\le_eco\\customer-voice\\JavaVerify\\download");
        File[] files = file.listFiles();
        ImageTest imageUtil = new ImageTest();
        for(File f:files) {
            BufferedImage result = imageUtil.removeBackground(f.getPath());
            FileOutputStream out = new FileOutputStream(new File(file,"result_"+f.getName()));
            boolean flag = ImageIO.write(result, "jpg", out);
            out.close();
        }

    }
}
