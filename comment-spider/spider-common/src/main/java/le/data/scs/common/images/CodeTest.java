package le.data.scs.common.images;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * Created by yangyong3 on 2017/3/9.
 */
public class CodeTest {
    public static void main(String[] args){
        String path = CodeTest.class.getResource("/").getPath();
        File imageFile = new File("D:\\le_eco\\customer-voice\\JavaVerify\\download\\result_91.tiff");
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("D:\\dev_tools\\图片识别\\Tesseract-OCR\\tessdata");
        try {
            String result = tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
