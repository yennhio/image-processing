import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

public class Gui extends JFrame {
    private JTextField imagePathInput;
    private JComboBox<String> algorithmSelector;
    private JLabel originalImageLabel;
    private JLabel processedImageLabel;
    String imageName;
    int imageWidth, imageHeight;
    int maskSize = 3;
    int[][] imagePixelValues;

    public Gui() throws IOException {

        setTitle("Image Processing App");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel();
        originalImageLabel = new JLabel();
        leftPanel.add(originalImageLabel);

        JPanel rightPanel = new JPanel();
        processedImageLabel = new JLabel();
        rightPanel.add(processedImageLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        imagePathInput = new JTextField(30);
        JButton loadButton = new JButton("Load Image");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAndDisplayImage();
            }
        });

        algorithmSelector = new JComboBox<>(new String[]{"Select an Algorithm", "Arithmetic Mean", "Geometric Mean", 
                                                            "Harmonic Mean", "Contraharmonic Mean", "Max Filter", 
                                                            "Min Filter", "Midpoint Filter", "Alpha Trimmed"});
        algorithmSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
                if ("Arithmetic Mean".equals(selectedAlgorithm)) {
                    arithmeticMean();
                } else if ("Geometric Mean".equals(selectedAlgorithm)) {
                    geometricMean();
                } else if ("Harmonic Mean".equals(selectedAlgorithm)) {
                    harmonicMean();
                }else if ("Contraharmonic Mean".equals(selectedAlgorithm)) {
                    contraharmonicMean();
                }else if ("Max Filter".equals(selectedAlgorithm)) {
                    maxFilter();
                }else if ("Min Filter".equals(selectedAlgorithm)) {
                    minFilter();
                }else if ("Midpoint Filter".equals(selectedAlgorithm)) {
                    midpointFilter();
                }else if ("Alpha Trimmed".equals(selectedAlgorithm)) {
                    alphaTrimmed();
                }
            }
        });
        algorithmSelector.setSelectedIndex(0);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Image File Path:"));
        controlPanel.add(imagePathInput);
        controlPanel.add(loadButton);
        controlPanel.add(algorithmSelector);

        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void obtainImageData() throws IOException {
        File file = new File(imageName);
        BufferedImage img = ImageIO.read(file);
        imageWidth = img.getWidth();
        imageHeight = img.getHeight();
        System.out.println(imageWidth + " " + imageHeight);
        Raster rasterObj = img.getData();

        imagePixelValues = new int[imageHeight][imageWidth];
        for (int i=0; i<imageWidth; i++) {
            for (int j=0; j<imageHeight; j++) {
                imagePixelValues[j][i] = rasterObj.getSample(i, j, 0);
            }
        }

    }

    private void arithmeticMean() {
        ArithmeticMeanFilter imageToApplyMeanFilter = new ArithmeticMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMeanFilter.applyMeanFilter());

        scaledImage.outputImageFile();
    }

    private void geometricMean() {
        GeometricMeanFilter imageToApplyGeometricMeanFilter = new GeometricMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyGeometricMeanFilter.applyGeometricMeanFilter());

        scaledImage.outputImageFile();

    }

    private void harmonicMean() {
        HarmonicMeanFilter imageToApplyHarmonicMeanFilter = new HarmonicMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyHarmonicMeanFilter.applyHarmonicMeanFilter());

        scaledImage.outputImageFile();

    }

    private void contraharmonicMean() {
        ContraharmonicMeanFilter imageToApplyContraharmonicMeanFilter = new ContraharmonicMeanFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyContraharmonicMeanFilter.applyContraharmonicMeanFilter());

        scaledImage.outputImageFile();

    }

    private void maxFilter() {
        MaxFilter imageToApplyMaxFilter = new MaxFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMaxFilter.applyMaxFilter());

        scaledImage.outputImageFile();

    }

    private void minFilter() {
        MinFilter imageToApplyMinFilter = new MinFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMinFilter.applyMinFilter());

        scaledImage.outputImageFile();

    }

    private void midpointFilter() {
        MidpointFilter imageToApplyMidpointFilter = new MidpointFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyMidpointFilter.applyMidpointFilter());

        scaledImage.outputImageFile();

    }

    private void alphaTrimmed() {
        AlphaTrimmedFilter imageToApplyAlphaTrimmedFilter = new AlphaTrimmedFilter(imagePixelValues, imageWidth, imageHeight, maskSize);
        ScaleProcessedImage scaledImage = new ScaleProcessedImage(imagePixelValues, imageWidth, imageHeight, imageToApplyAlphaTrimmedFilter.applyAlphaTrimmedMeanFilter());

        scaledImage.outputImageFile();

    }


    private void loadOutputImage() {
        try {
            File file = new File("output.jpg");
            BufferedImage img = ImageIO.read(file);

            processedImageLabel.setIcon(new ImageIcon(img));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAndDisplayImage() {
        try {
            imageName = imagePathInput.getText();
            File file = new File(imageName);
            BufferedImage img = ImageIO.read(file);

            originalImageLabel.setIcon(new ImageIcon(img));
            obtainImageData();

            String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
            if ("Arithmetic Mean".equals(selectedAlgorithm)) {
                loadOutputImage();
            } else {
                loadOutputImage();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Gui();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
