import java.util.List;
import java.util.ArrayList;

public class BitplaneRLE {
    
    int imageWidth, imageHeight;
    int[][] imagePixelValues;
    
    public BitplaneRLE(int[][] imagePixelValues, int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] applyRLE() {
        List<String> encoded = new ArrayList<>();
        String[][] binaryArr = new String[imageHeight][imageWidth];
        binaryArr = convertToBinary();

        int count = 1;
        

        long start = System.currentTimeMillis();
        int bitIndex = 0;

        while (bitIndex < 8) {
            String bitVal = String.valueOf(binaryArr[0][0].charAt(bitIndex));

            //first bit count in every layer is for 0
            for (int i=0; i<imageHeight; i++) {
                for (int j=0; j<imageWidth; j++) {

                    //if first bit is not 0 (base case)
                    if (i==0 && j==0 &&  String.valueOf(binaryArr[i][j].charAt(bitIndex)).equals("0")) {
                        encoded.add("0");
                    } 
                    
                    if (i!=0 && j != 0 && String.valueOf(binaryArr[i][j].charAt(bitIndex)).equals(bitVal)) {
                        count++;
                    } else if (i!=0 && j != 0 && !String.valueOf(binaryArr[i][j].charAt(bitIndex)).equals(bitVal)) {
                        encoded.add(String.valueOf(count));
                        count = 1;
                        bitVal = String.valueOf(binaryArr[i][j].charAt(bitIndex));
                    }
                }
            }

            bitIndex++;
        }

        encoded.add(String.valueOf(count));

        long end = System.currentTimeMillis();
        long elapsed = end - start;

        // for (String number : encoded) {
        //     System.out.print(number + " ");
        // }
        
        System.out.println("Compression ratio: " + (double)(imageHeight*imageWidth) / (double)encoded.size() + ":1");
        System.out.println("Encoding time: " + elapsed + " milliseconds.");

        String[][] decompressedImg = new String[imageHeight][imageWidth];

        bitIndex = 0;

        int currentListIndex=0;
        // System.out.println(imageHeight + " " + imageWidth);

        start = System.currentTimeMillis();

        //decompression loop
        while (bitIndex < 8) {
            int currentPosInLayer = 0;
            int i=0, j=0;
            String bit = "0";

            for (int k=currentListIndex; k < encoded.size(); k++) {
                String bitLength = encoded.get(k);
                currentPosInLayer += Integer.parseInt(bitLength);
                count = 0;
                currentListIndex++;

                if (k==0 && bitLength.equals("0")) {
                    bit = "1";
                } else if (k != 0 && bit.equals("0")) {
                    bit = "1";
                } else if (k != 0 && bit.equals("1")) {
                    bit = "0";
                }

                while (count < Integer.parseInt(bitLength)) {

                    String currentValue = decompressedImg[i][j];
                    StringBuilder strBuilder;
                    
                    if (currentValue == null) {
                        // Initialize a new StringBuilder if the current value is null
                        strBuilder = new StringBuilder();
                    } else {
                        strBuilder = new StringBuilder(currentValue);
                    }

                    strBuilder.append(bit);
                    decompressedImg[i][j] = strBuilder.toString();
                    count++;
                    
                    if (j >= imageWidth - 1) {
                        j = 0;
                        if (i < imageHeight - 1) {
                            i++;
                        } else {
                            // If i is already at the last row, break out of the loop
                            break;
                        }
                    } else {
                        j++;
                    }
                }
                
                //if current layer is finished decompressing, move on the next bit plane
                if (currentPosInLayer >= imageHeight*imageWidth-1)
                    break;
                }

            bitIndex++;
        }

        end = System.currentTimeMillis();
        elapsed = end - start;
        System.out.println("Decoding time: " + elapsed + " milliseconds.");


        // for (int i = 0; i < imageHeight; i++) {
        //     for (int j = 0; j < imageWidth; j++) {
        //         System.out.print(decompressedImg[i][j] + " ");
        //     }
        //     System.out.println();
        

        return convertBinaryArrayToIntArray(decompressedImg);


}


    public String[][] convertToBinary() {
        String[][] binaryValues = new String[imageHeight][imageWidth];
    
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int number = imagePixelValues[y][x];
                String binaryString = Integer.toBinaryString(number);
    
                // Pad with leading zeros to make it 8 characters long
                while (binaryString.length() < 8) {
                    binaryString = "0" + binaryString;
                }
    
                binaryValues[y][x] = binaryString;
            }
        }
        return binaryValues;
    }  

    public int[][] convertBinaryArrayToIntArray(String[][] binaryArray) {
        int height = binaryArray.length;
        int width = binaryArray[0].length;
    
        int[][] intArray = new int[height][width];
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                intArray[y][x] = Integer.parseInt(binaryArray[y][x], 2);
            }
        }
    
        return intArray;
    }

   
}