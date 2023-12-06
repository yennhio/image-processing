import java.util.*;

class Node implements Comparable<Node> {
    int grayscaleValue;
    int frequency;
    Node left, right;

    public Node(int grayscaleValue, int frequency) {
        this.grayscaleValue = grayscaleValue;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }
}

public class Huffman {

    private int imageWidth, imageHeight;
    private int[][] imagePixelValues;
    private Map<Integer, String> huffmanCodes; 

    public Huffman(int[][] imagePixelValues, int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
        this.huffmanCodes = new HashMap<>();
    }

    public String[][] compressImage() {
        String[][] compressedImg = new String[imageHeight][imageWidth];

        long originalSize = calculateOriginalSize();
        long startTime = System.currentTimeMillis();

        Map<Integer, Integer> frequencyMap = calculateFrequency();
        PriorityQueue<Node> priorityQueue = buildPriorityQueue(frequencyMap);

        Node root = buildHuffmanTree(priorityQueue);

        generateHuffmanCodes(root, "");

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int pixelValue = imagePixelValues[i][j];
                compressedImg[i][j] = huffmanCodes.get(pixelValue);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Encoding time: " + (endTime - startTime) + " milliseconds.");
        long compressedSize = calculateCompressedSize(compressedImg);

        double compressionRatio = (double) originalSize / compressedSize;
        System.out.println("Compression ratio: " + compressionRatio + ":1");

        return compressedImg;
    }

    public int[][] decompressImage(String[][] compressedImg) {
        int[][] decompressedImg = new int[imageHeight][imageWidth];
        long startTime = System.currentTimeMillis();

        Map<String, Integer> reverseHuffmanCodes = reverseHuffmanCodes();

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                String huffmanCode = compressedImg[i][j];
                int pixelValue = reverseHuffmanCodes.get(huffmanCode);
                decompressedImg[i][j] = pixelValue;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Decoding time: " + (endTime - startTime) + " milliseconds.");

        return decompressedImg;
    }

    private Map<Integer, Integer> calculateFrequency() {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int pixelValue = imagePixelValues[i][j];
                frequencyMap.put(pixelValue, frequencyMap.getOrDefault(pixelValue, 0) + 1);
            }
        }

        return frequencyMap;
    }

    private PriorityQueue<Node> buildPriorityQueue(Map<Integer, Integer> frequencyMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        return priorityQueue;
    }

    private Node buildHuffmanTree(PriorityQueue<Node> priorityQueue) {
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            Node parent = new Node(-1, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            priorityQueue.add(parent);
        }

        return priorityQueue.poll();
    }

    private void generateHuffmanCodes(Node node, String code) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                huffmanCodes.put(node.grayscaleValue, code);
            }

            generateHuffmanCodes(node.left, code + "0");
            generateHuffmanCodes(node.right, code + "1");
        }
    }

    private Map<String, Integer> reverseHuffmanCodes() {
        Map<String, Integer> reverseHuffmanCodes = new HashMap<>();

        for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
            reverseHuffmanCodes.put(entry.getValue(), entry.getKey());
        }

        return reverseHuffmanCodes;
    }

    private long calculateOriginalSize() {
        return (long) imageHeight * imageWidth * Integer.BYTES;
    }

    private long calculateCompressedSize(String[][] compressedImg) {
        return Arrays.stream(compressedImg)
                .mapToLong(row -> Arrays.stream(row).mapToInt(String::length).sum())
                .sum() * 2;
    }
}
