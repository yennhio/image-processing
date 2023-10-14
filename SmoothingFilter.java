

public class SmoothingFilter {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];

    public SmoothingFilter(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }
    
    public int[][] applyBoxFilter() {

        int[][] smoothedImageValues = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                int sum=0;
                int numberOfPixels=0;


                if(topRow < 0)
                    topRow = 0;                    
                
                if (bottomRow >= imageHeight)
                    bottomRow = imageHeight-1;
                
                if (leftMostColumn < 0)
                    leftMostColumn = 0;

                if (rightMostColumn >= imageWidth)
                    rightMostColumn = imageWidth-1;

                for (int j=topRow; j<=bottomRow; j++) {
                    for (int i=leftMostColumn; i<= rightMostColumn; i++) {
                        sum += imagePixelValues[j][i];
                        numberOfPixels++;
                    }
                }

                smoothedImageValues[y][x] = sum / numberOfPixels;
            }
        }

        return smoothedImageValues;
    }
}
