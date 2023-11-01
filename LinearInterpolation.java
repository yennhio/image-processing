public class LinearInterpolation {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public LinearInterpolation(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] linearInterpolation(int newWidth, int newHeight) {
        int[][] interpolatedImage = new int[newHeight][newWidth];
    
        double xScale = (double) imageWidth / newWidth;
        double yScale = (double) imageHeight / newHeight;
    
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                double xPosition = x * xScale;
                double yPosition = y * yScale;
    
                double x1 = Math.floor(xPosition);
                double x2 = Math.ceil(xPosition);

                x1--;
                x2+=2;

                double yFinal = Math.round(yPosition);
                if (yFinal < 0)
                    yFinal = 0;
                else if (yFinal >= imageHeight)
                    yFinal = imageHeight-1;

                if (x2 >= imageWidth) 
                    x2 = imageWidth - 1;
                else if (x1 < 0)
                    x1 = 0;
    
                double interpolatedValue = (x2-xPosition)/(x2-x1)*imagePixelValues[(int)yFinal][(int)x1]+(xPosition-x1)/(x2-x1)*imagePixelValues[(int)yFinal][(int)x2];
                interpolatedImage[y][x] = (int)interpolatedValue;

            }
        }
        return interpolatedImage;
    }
}
