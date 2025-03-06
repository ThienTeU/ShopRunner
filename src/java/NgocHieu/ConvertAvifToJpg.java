package NgocHieu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ConvertAvifToJpg {

    private String inputPath;
    private String outputPath;

    public ConvertAvifToJpg() {
    }

    public String convert(String inputPath, String outputPath) {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg", "-i", inputPath, outputPath
        );
        try {
            Process process = processBuilder.start();
            process.waitFor();
            System.out.println("Conversion completed successfully!");
            return outputPath;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return inputPath;
        }
    }

    public String encodeImageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            fis.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        String inputPath = "C:/Users/admin/ShopRunner/web/Image2/productID_1/colorID_4/image_1.avif";
        String outputPath = "C:/Users/admin/ShopRunner/web/Image2/productID_1/colorID_4/image_1.jpg";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg", "-i", inputPath, outputPath
        );

        try {
            Process process = processBuilder.start();
            process.waitFor();
            System.out.println("Conversion completed successfully!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
