

public class Laplacian {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues;

    public Laplacian(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] generateMask() {
        int[][] laplacianMask = new int[maskSize][maskSize];

        for (int y = 0; y < maskSize; y++) {
            for (int x = 0; x < maskSize; x++) {
                laplacianMask[y][x] = -1;
            }
        }
        laplacianMask[maskSize / 2][maskSize / 2] = maskSize * maskSize - 1;

        return laplacianMask;
    }

    public int[][] applyLaplacian() {
        int[][] laplacianMask = generateMask();
        int[][] laplacianMaskedValues = new int[imageHeight][imageWidth];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
    
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int sum = 0;
                int count = 0;
    
                for (int i = -maskSize / 2; i <= maskSize / 2; i++) {
                    for (int j = -maskSize / 2; j <= maskSize / 2; j++) {
                        int newX = x + j;
                        int newY = y + i;
    
                        if (newX >= 0 && newX < imageWidth && newY >= 0 && newY < imageHeight) {
                            sum += imagePixelValues[newY][newX] * laplacianMask[i + maskSize / 2][j + maskSize / 2];
                            count++;
                        }
                    }
                }
    
                int result = (count > 0) ? Math.max(0, Math.min(255, sum / count)) : 0;
                laplacianMaskedValues[y][x] = result;
                min = Math.min(min, result);
                max = Math.max(max, result);
            }
        }

        return laplacianMaskedValues;
    }
    
}
