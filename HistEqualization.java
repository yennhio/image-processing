

public class HistEqualization {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];

    public HistEqualization(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] globalEquilization() {
        int[][] equalizedArray = new int[imageHeight][imageWidth];
        int[] histogram = new int[256];
        int[] equalizedHistogram = new int[256];

        double MN = imageHeight*imageWidth;

        //count number of pixels with a certain value
        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                histogram[imagePixelValues[y][x]]++;
            }
        }

        //perform equalization
        int sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += histogram[i];
            equalizedHistogram[i] = (int) Math.round(255.0 * (double) sum / MN);
        }

        //apply equalized values to old image array
            for (int y=0; y<imageHeight; y++) {
                for (int x=0; x<imageWidth; x++) {
                    int grayscaleVal = imagePixelValues[y][x];
                    equalizedArray[y][x] = equalizedHistogram[grayscaleVal];
                }
            }
        for (int i=200; i<220; i++) {
            System.out.print(equalizedHistogram[i] + " ");
        }        

        return equalizedArray;
    }

    public int[][] localEqualization() {
        int[][] equalizedArray = new int[imageHeight][imageWidth];
        int[] neighborhood = new int[maskSize*maskSize];
        int[] equalizedNeighborhood = new int[256];

        int firstX=0, firstY=0, lastX=firstX+maskSize-1, lastY= firstY+maskSize-1;

        while (lastY < imageHeight){
            firstX=0;
            lastX=firstX+maskSize-1;

            while (lastX < imageWidth) {
                int neighborhoodIndex=0;

                //store values of neighborhood in separate array for local processing
                for (int y=firstY; y<=lastY; y++) {
                    for (int x=firstX; x<=lastX; x++) {
                        neighborhood[neighborhoodIndex] = imagePixelValues[y][x];
                        neighborhoodIndex++;
                    }
                }

                int centralY = maskSize/2+firstY;
                int centralX = maskSize/2+firstX;

                equalizedNeighborhood = equalize(neighborhood);
                int centralVal = imagePixelValues[centralY][centralX];
                equalizedArray[centralY][centralX] = equalizedNeighborhood[centralVal]; 

                firstX++;
                lastX=firstX+maskSize-1;
            }
            firstY++;
            lastY= firstY+maskSize-1;
        }        

        return equalizedArray;
    }

    public int[] equalize(int[] neighborhoodValues) {
        int[] equalizedHistogram = new int[256];
        int[] histogram =  new int[256];
        float MN = maskSize*maskSize;

        //count number of pixels with a certain value
        for (int y=0; y<maskSize; y++) {
            histogram[neighborhoodValues[y]]++;
        }
        
        //perform equalization
        int sum=0;
        for (int i=0; i<256; i++) {
            sum += histogram[i];
            equalizedHistogram[i] = (int) Math.ceil(255.0*(float)sum/MN+0.5);
        }

        return equalizedHistogram;
    }
        
}