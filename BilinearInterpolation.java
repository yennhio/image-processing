public class BilinearInterpolation {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public BilinearInterpolation(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] bilinearInterpolation(int newWidth, int newHeight) {
        int[][] interpolatedImage = new int[newHeight][newWidth];
    
        double xScale = (double) imageWidth / newWidth;
        double yScale = (double) imageHeight / newHeight;
    
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                double xPosition = x * xScale;
                double yPosition = y * yScale;
    
                double x1 = Math.floor(xPosition);
                double x2 = Math.ceil(xPosition);
                double y2 = Math.floor(yPosition);
                double y1 = Math.ceil(yPosition);

                x1--;
                x2+=2;
                y1+=2;
                y2--;

                if (x2 >= imageWidth) 
                    x2 = imageWidth - 1;
                else if (x1 < 0)
                    x1 = 0;
    
                if (y1 >= imageHeight)
                    y1 = imageHeight - 1;
                else if (y2 < 0)
                    y2 = 0;

                double r1 = (x2-xPosition)/(x2-x1)*imagePixelValues[Math.round((int)y1)][Math.round((int)x1)]+(xPosition-x1)/(x2-x1)*imagePixelValues[Math.round((int)y1)][Math.round((int)x2)];
                double r2 = (x2-xPosition)/(x2-x1)*imagePixelValues[Math.round((int)y2)][Math.round((int)x1)]+(xPosition-x1)/(x2-x1)*imagePixelValues[Math.round((int)y2)][Math.round((int)x2)];
                double p = (y2-yPosition)/(y2-y1)*r1 + (yPosition-y1)/(y2-y1)*r2;

                interpolatedImage[y][x] = Math.abs((int)p);

            }
        }
        return interpolatedImage;
    }
}
