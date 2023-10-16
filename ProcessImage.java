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
        MedianFilter imageToApplyMedianFilter = new MedianFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        HistEqualization imageToEqualize = new HistEqualization(imagePixelValues, imageWidth, imageHeight, maskSize);
        Laplacian imageToApplyLaplacian = new Laplacian(imagePixelValues, imageWidth, imageHeight, maskSize);
        HighboostFilter imageToHighboost = new HighboostFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        RemoveBitplanes imageToRemoveBitPlaneFrom = new RemoveBitplanes(imagePixelValues, imageWidth, imageHeight, maskSize);
        ArithmeticMeanFilter imageToApplyMeanFilter = new ArithmeticMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        GeometricMeanFilter imageToApplyGeometricMeanFilter = new GeometricMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        HarmonicMeanFilter imageToApplyHarmonicMeanFilter = new HarmonicMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ContraharmonicMeanFilter imageToApplyContraharmonicMeanFilter = new ContraharmonicMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        MaxFilter imageToApplyMaxFilter = new MaxFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        MinFilter imageToApplyMinFilter = new MinFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        MidpointFilter imageToApplyMidpointFilter = new MidpointFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        AlphaTrimmedFilter imageToApplyAlphaTrimmedFilter = new AlphaTrimmedFilter(imagePixelValues, imageWidth, imageHeight, maskSize);


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
        
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToEqualize.globalEquilization());

        //ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToEqualize.localEqualization());
//         ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyLaplacian.applyLaplacian());
// ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToSmooth.applyBoxFilter());
//         ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMedianFilter.applyMedianFilter());
// ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToHighboost.applyHighboostFilter());
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToRemoveBitPlaneFrom.removeBitplanes());

        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMeanFilter.applyMeanFilter());

        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyGeometricMeanFilter.applyGeometricMeanFilter());
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyHarmonicMeanFilter.applyHarmonicMeanFilter());
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyContraharmonicMeanFilter.applyContraharmonicMeanFilter());
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMaxFilter.applyMaxFilter());
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMinFilter.applyMinFilter());
        // ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMidpointFilter.applyMidpointFilter());
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyAlphaTrimmedFilter.applyAlphaTrimmedMeanFilter());

        scaledImage.outputImageFile();



    }
    
}
