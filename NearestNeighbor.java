public class NearestNeighbor {
    
    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public NearestNeighbor(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] nearestNeighborInterpolation(int newHeight, int newWidth) {
        int[][] interpolatedImage = new int[newHeight][newWidth];

        double xScale = (double)imageWidth/newWidth;
        double yScale = (double)imageHeight/newHeight;

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                double xPosition = x * xScale;
                double yPosition = y * yScale;
    
                double x1 = Math.round(xPosition);
                double y1 = Math.round(yPosition);

                if (y1 >= imageHeight) {
                    y1 = imageHeight-1;
                }
                else if (y1 < 0) {
                    y1=0;
                }

                if (x1 >= imageWidth) 
                    x1 = imageWidth - 1;
                else if (x1 < 0)
                    x1 = 0;
    
                interpolatedImage[y][x] = imagePixelValues[(int)y1][(int)x1];

            }
        }

        return interpolatedImage;
    }
}
