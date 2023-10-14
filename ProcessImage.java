import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ProcessImage {

    public static void main(String[] args) throws IOException {

        int imageWidth, imageHeight;
        int maskSize;

        Scanner scannerObj = new Scanner(System.in); 

        System.out.print("Insert picture file to be processed: ");
        String imageName = scannerObj.nextLine();

        System.out.print("Enter mask size: ");
        maskSize = scannerObj.nextInt();
        scannerObj.nextLine();

        //obtain pixel values and store them in array imagePixelValues[][]
        File file = new File(imageName);
        BufferedImage img = ImageIO.read(file);
        imageWidth = img.getWidth();
        imageHeight = img.getHeight();
        System.out.println(imageWidth + " " + imageHeight);
        Raster rasterObj = img.getData();

        int[][] imagePixelValues = new int[imageHeight][imageWidth];
        for (int i=0; i<imageWidth; i++) {
            for (int j=0; j<imageHeight; j++) {
                imagePixelValues[j][i] = rasterObj.getSample(i, j, 0);
            }
        }

        SmoothingFilter imageToSmooth = new SmoothingFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        MedianFilter imageToMedianed = new MedianFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        HistEqualization imageToEqualize = new HistEqualization(imagePixelValues, imageWidth, imageHeight, maskSize);
        Laplacian imageLaplacian = new Laplacian(imagePixelValues, imageWidth, imageHeight, maskSize);
        HighboostFilter imageHighboost = new HighboostFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        RemoveBitplanes imageBitPlane = new RemoveBitplanes(imagePixelValues, imageWidth, imageHeight, maskSize);


        // imageToSmooth.applyBoxFilter(imagePixelValues);

        // imageToMedianed.applyMedianFilter(imagePixelValues);

        // System.out.print("Enter 1 for global and 2 for local histogram equilization: ");
        // int globalOrLocal = scannerObj.nextInt();
        // scannerObj.nextLine();

        // if (globalOrLocal == 1)
            // imageToEqualize.globalEquilization(imagePixelValues);
        // else
            // imageToEqualize.localEqualization(imagePixelValues);

        // imageLaplacian.applyLaplacian();

        // imageBitPlane.removeBitplanes();
        

        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageHighboost.applyHighboostFilter());
        scaledImage.outputImageFile();
        


    }
    
}
