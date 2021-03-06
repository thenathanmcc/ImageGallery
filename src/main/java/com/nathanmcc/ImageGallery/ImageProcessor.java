/**
 * @author Nathan McCulloch
 */

import java.awt.*;
import java.awt.image.BufferedImage;

/** Class to apply image filters to an image*/
public class ImageProcessor {

    public ImageProcessor() {}


    /**
     * Applies a greyscale filter to the image
     * @param original_image original image
     * @return greyscale image
     */
    public BufferedImage applyGreyscaleFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        // RGB values of the current pixel
        int red_val;
        int green_val;
        int blue_val;
        int greyscale_val;
        Color c;
        for (int x = 0; x < transformed_image.getWidth(); x++){
            for (int y = 0; y < transformed_image.getHeight(); y++){
                // Fetch current pixel's RGB values and calculate greyscale value
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                blue_val = c.getBlue();
                green_val = c.getGreen();
                greyscale_val = (int) (0.3 * red_val + 0.6 * green_val + 0.1 * blue_val);
                c = new Color(greyscale_val, greyscale_val, greyscale_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies a Negative filter to the original image i.e inverts
     * the RGB values for each pixel
     * @param original_image original image
     * @return negative
     */
    public BufferedImage applyNegativeFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int blue_val;
        int green_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = 255 - c.getRed();
                green_val = 255 - c.getGreen();
                blue_val = 255 - c.getBlue();
                c = new Color(red_val, green_val, blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies a sepia filter to the original image
     * @param original_image original image
     * @return sepia image
     */
    public BufferedImage applySepiaFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());

        int red_val;
        int green_val;
        int blue_val;
        int new_red_val;
        int new_blue_val;
        int new_green_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();

                //Calculate new RGB values and check bounds
                new_red_val = (int)(0.393 * red_val + 0.769 * green_val + 0.189 * blue_val);
                if (new_red_val > 255)
                    new_red_val = 255;
                new_green_val = (int)(0.349 * red_val + 0.686 * green_val + 0.168 * blue_val);
                if (new_green_val > 255)
                    new_green_val = 255;
                new_blue_val = (int)(0.272 * red_val + 0.534 * green_val + 0.131 * blue_val);
                if (new_blue_val > 255)
                    new_blue_val = 255;

                c = new Color(new_red_val, new_green_val, new_blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies a cartoon-esque filter to the image by quantizing the RGB values of each pixel
     * @param original_image original image
     * @return cartoon-esque image
     */
    public BufferedImage applyCartoonFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = quantizeRGBValue(c.getRed());
                green_val = quantizeRGBValue(c.getGreen());
                blue_val = quantizeRGBValue(c.getBlue());
                c = new Color(red_val, green_val, blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Quantizes a RGB value as an integer into discrete values
     * @param n RGB value to be quantized
     * @return quantized RGB value
     */
    private int quantizeRGBValue (int n) {
        if (n < 85) {
            n = 0;
        } else if (n < 170) {
            n = 85;
        } else if (n < 255) {
            n = 170;
        } else {
            n = 255;
        }
        return n;
    }


    /**
     * Increase the contrast of the image
     * Contrast is increased by calculating the average value of each pixel and increasing its brightness if
     * above a certain threshold (127) and decreasing it if it is below
     * @param original_image original image
     * @return contrast enhanced Image
     */
    public BufferedImage applyContrastEnhancement(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        int average_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();
                average_val = (red_val + green_val + blue_val)/3;

                //Check threshold value and increase/decrease pixel brightness accordingly
                if (average_val > 127){
                    red_val = red_val + 50;
                    if (red_val > 255)
                        red_val = 255;
                    green_val = green_val + 50;
                    if (green_val > 255)
                        green_val = 255;
                    blue_val = blue_val  + 50;
                    if (blue_val > 255)
                        blue_val = 255;
                } else {
                    red_val = red_val - 50;
                    if (red_val < 0)
                        red_val = 0;
                    green_val = green_val - 50;
                    if (green_val < 0)
                        green_val = 0;
                    blue_val = blue_val  - 50;
                    if (blue_val < 0)
                        blue_val = 0;
                }
                c = new Color(red_val, green_val, blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Converts the image into a black and white image
     * @param original_image original image
     * @return black and white image
     */
    public BufferedImage thresholdImage(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        int greyscale_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();
                greyscale_val = (int)(red_val * 0.3 + green_val * 0.6 + blue_val * 0.1);
                if (greyscale_val >= 127) {
                    greyscale_val = 255;
                } else {
                    greyscale_val = 0;
                }
                c = new Color(greyscale_val, greyscale_val, greyscale_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Applies a box blur to the original image. Uses a kernel size of 5
     * @param original_image original image
     * @return box blurred image
     */
    public BufferedImage applyBoxBlur(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int kernel_size = 5;
        //Kernel rgb sums used to calculate average value of the kernel
        int kernel_sum_red;
        int kernel_sum_green;
        int kernel_sum_blue;
        for ( int x = 0; x < original_image.getWidth(); x++ ) {
            for ( int y = 0; y < original_image.getHeight(); y++ ) {
                kernel_sum_red = 0;
                kernel_sum_green = 0;
                kernel_sum_blue = 0;
                for (int kernel_x = x - (kernel_size - 1)/2; kernel_x <= x + (kernel_size - 1)/2; kernel_x++ ) {
                    for (int kernel_y = y - (kernel_size - 1)/2; kernel_y <= y + (kernel_size - 1)/2; kernel_y++ ) {

                        //Check kernel coordinate bounds
                        if (kernel_x >= 0 && kernel_x < original_image.getWidth() && kernel_y >= 0 && kernel_y < original_image.getHeight()) {
                            c = new Color ( original_image.getRGB(kernel_x, kernel_y) );
                            kernel_sum_red += c.getRed();
                            kernel_sum_green += c.getGreen();
                            kernel_sum_blue += c.getBlue();
                        }
                    }
                }

                c = new Color(kernel_sum_red/(kernel_size*kernel_size),
                        kernel_sum_green/(kernel_size*kernel_size),
                        kernel_sum_blue/(kernel_size*kernel_size));
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Applies a gaussian blur to the original image.
     * @param original_image original image
     * @return gaussian blurred image
     */
    public BufferedImage applyGaussianBlur(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int kernel_size = 5; //Kernel size must be odd
        int gaussian_factor = 256; // value to divide gaussian kernel by
        int[][] gaussian_kernel = { {1, 4, 6, 4, 1}, {4, 16, 24, 16, 4}, {6, 24, 36, 24, 6}, {4, 16, 24, 16, 4}, {1, 4, 6, 4, 1} };
        //Kernel rgb sums used to calculate new pixel values
        int kernel_sum_red;
        int kernel_sum_green;
        int kernel_sum_blue;

        //Kernel position on the image
        int kernel_x;
        int kernel_y;
        for (int x = 0; x < original_image.getWidth(); x++ ) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                //Initialize sums
                kernel_sum_red = 0;
                kernel_sum_green = 0;
                kernel_sum_blue = 0;
                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        kernel_x = x - (kernel_size - 1)/2 + i;
                        kernel_y = y - (kernel_size - 1)/2 + j;
                        if (kernel_x >= 0 && kernel_x < original_image.getWidth() && kernel_y >= 0 && kernel_y < original_image.getHeight()) {
                            c = new Color ( original_image.getRGB(kernel_x, kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_red += gaussian_kernel[i][j] * c.getRed();
                            kernel_sum_green += gaussian_kernel[i][j] * c.getGreen();
                            kernel_sum_blue += gaussian_kernel[i][j] * c.getBlue();
                        }
                    }
                }
                c = new Color(kernel_sum_red/(gaussian_factor),
                        kernel_sum_green/(gaussian_factor),
                        kernel_sum_blue/(gaussian_factor));
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies the prewitt edge-detection operator
     * @param original_image original image
     * @return edge-detected image
     */
    public BufferedImage applyPrewittOperator(BufferedImage original_image) {
        BufferedImage greyscale_image = this.applyGreyscaleFilter(original_image); // Convert image to greyscale
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;

        int[][] prewitt_kernel_x = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
        int[][] prewitt_kernel_y = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
        int kernel_size = 3;
        //Kernel sums
        int kernel_sum_x = 0;
        int kernel_sum_y = 0;
        //Current pixel in the kernel
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;
        int final_pixel_value;

        for (int x = 0; x < greyscale_image.getWidth(); x++) {
            for (int y = 0; y < greyscale_image.getHeight(); y++) {
                kernel_sum_x = 0;
                kernel_sum_y = 0;

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1)/2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1)/2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < greyscale_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < greyscale_image.getHeight()) {

                            //Get pixel's greyscale value
                            c = new Color ( greyscale_image.getRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_x += prewitt_kernel_x[i][j] * c.getRed();
                            kernel_sum_y += prewitt_kernel_y[i][j] * c.getRed();
                        }
                    }
                }
                final_pixel_value = (int) (Math.sqrt(kernel_sum_x * kernel_sum_x + kernel_sum_y * kernel_sum_y));
                final_pixel_value = map(final_pixel_value); // Ensure the pixel value is in the range [0 -255]

                c = new Color(final_pixel_value, final_pixel_value, final_pixel_value);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Applies the Sobel operator to the original image for edge detection
     * @param original_image original image
     * @return edge-detected image
     */
    public BufferedImage applySobelOperator(BufferedImage original_image) {
        BufferedImage greyscale_image = this.applyGreyscaleFilter(original_image); // Convert image to greyscale
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;

        int[][] sobel_kernel_x = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobel_kernel_y = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        int kernel_size = 3;
        //Kernel sums
        int kernel_sum_x = 0;
        int kernel_sum_y = 0;
        //Current pixel in the kernel
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;
        int final_pixel_value;

        for (int x = 0; x < greyscale_image.getWidth(); x++) {
            for (int y = 0; y < greyscale_image.getHeight(); y++) {
                kernel_sum_x = 0;
                kernel_sum_y = 0;

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1)/2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1)/2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < greyscale_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < greyscale_image.getHeight()) {

                            //Get pixel's greyscale value
                            c = new Color ( greyscale_image.getRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_x += sobel_kernel_x[i][j] * c.getRed();
                            kernel_sum_y += sobel_kernel_y[i][j] * c.getRed();
                        }
                    }
                }
                final_pixel_value = (int) (Math.sqrt(kernel_sum_x * kernel_sum_x + kernel_sum_y * kernel_sum_y));
                final_pixel_value = map(final_pixel_value); // Ensure the pixel value is in the range [0 -255]

                c = new Color(final_pixel_value, final_pixel_value, final_pixel_value);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Ensures an integer is in the range 0 - 255
     * @param n number to be mapped
     * @return number in [0, 255]
     */
    private int map(int n){
        if (n > 255) {
            n = 255;
        }else if (n < 0) {
            n = 0;
        }
        return n;
    }

    /**
     * Pixelates an image by setting all the pixels in the kernel to the same RGB value
     * as the pixel in the centre of the kernel.
     * @param original_image
     * @return
     */
    public BufferedImage pixelate(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int kernel_size = 5; //Kernel size should always be an odd number
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;

        for ( int x = (kernel_size - 1)/2 ; x < original_image.getWidth() - (kernel_size - 1)/2; x += kernel_size) {
            for ( int y = (kernel_size - 1)/2; y < original_image.getHeight() - (kernel_size - 1)/2; y += kernel_size) {

                c = new Color( original_image.getRGB(x, y));

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1) / 2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1) / 2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < original_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < original_image.getHeight()) {

                            transformed_image.setRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y, c.getRGB());
                        }
                    }
                }
            }
        }

        return transformed_image;
    }

    /**
     * Sharpens the original image
     * @param original_image image to be sharpened
     * @return sharpened Image
     */
    public BufferedImage sharpen(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int[][] sharpen_kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        int kernel_size = 3;
        //Running sum in the kernel
        int new_red_value;
        int new_green_value;
        int new_blue_value;

        //Current pixel in the kernel
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;

        for (int x = 0; x < original_image.getWidth(); x++) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                new_red_value = 0;
                new_green_value = 0;
                new_blue_value = 0;

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1)/2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1)/2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < original_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < original_image.getHeight()) {

                            c = new Color ( original_image.getRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            new_red_value += sharpen_kernel[i][j] * c.getRed();
                            new_green_value += sharpen_kernel[i][j] * c.getGreen();
                            new_blue_value += sharpen_kernel[i][j] * c.getBlue();
                        }
                    }
                }
                // Ensure the pixels RGB values are in the range [0 -255]
                new_red_value = map(new_red_value);
                new_green_value = map(new_green_value);
                new_blue_value = map(new_blue_value);

                c = new Color(new_red_value, new_green_value, new_blue_value);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Calculates the partial derivative in the x direction using the sobel kernel
     * @param original_image original geryscale image
     * @return the image's partial derivate in the x direction
     */
    private BufferedImage calculateXPartialDerivative(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;

        int[][] sobel_kernel_x = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int kernel_size = sobel_kernel_x.length;;
        //Kernel sum
        int kernel_sum_x = 0;
        //Current pixel in the kernel
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;
        int final_pixel_value;

        for (int x = 0; x < original_image.getWidth(); x++) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                kernel_sum_x = 0;

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1)/2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1)/2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < original_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < original_image.getHeight()) {

                            //Get pixel's greyscale value
                            c = new Color ( original_image.getRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_x += sobel_kernel_x[i][j] * c.getRed();
                        }
                    }
                }
                final_pixel_value = map(kernel_sum_x); // Ensure the pixel value is in the range [0 -255]

                c = new Color(final_pixel_value, final_pixel_value, final_pixel_value);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Calculates the partial derivative in the y direction using the sobel kernel
     * @param original_image original greyscale image
     * @return the image's partial derivate in the y direction
     */
    private BufferedImage calculateYPartialDerivative(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;

        int[][] sobel_kernel_y = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        int kernel_size = sobel_kernel_y.length;;
        //Kernel sum
        int kernel_sum_y = 0;
        //Current pixel in the kernel
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;
        int final_pixel_value;

        for (int x = 0; x < original_image.getWidth(); x++) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                kernel_sum_y = 0;

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1)/2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1)/2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < original_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < original_image.getHeight()) {

                            //Get pixel's greyscale value
                            c = new Color ( original_image.getRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_y += sobel_kernel_y[i][j] * c.getRed();
                        }
                    }
                }
                final_pixel_value = map(kernel_sum_y); // Ensure the pixel value is in the range [0 -255]

                c = new Color(final_pixel_value, final_pixel_value, final_pixel_value);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Calculates the average intensity in a 3x3 window with (x, y) has the centre pixel
     * @param image image assumed to be a greyscale image
     * @param x x-coordinate of the centre of the window
     * @param y y-coordinate of the centre of the window
     * @param square true if the intensities should be sqaured i.e calculate the average of the sqaure of the intensities
     * @return the average intensity of the window.
     */
    private int calculateAverageWindowIntensity(BufferedImage image, int x, int y, boolean square) {
        int average_intensity = 0;

        if (square) {
            average_intensity += ((new Color(image.getRGB(x - 1, y - 1))).getRed()) * ((new Color(image.getRGB(x - 1, y - 1))).getRed());
            average_intensity += ((new Color(image.getRGB(x    , y - 1))).getRed()) * ((new Color(image.getRGB(x    , y - 1))).getRed());
            average_intensity += ((new Color(image.getRGB(x + 1, y - 1))).getRed()) * ((new Color(image.getRGB(x + 1, y - 1))).getRed());

            average_intensity += ((new Color(image.getRGB(x - 1, y    ))).getRed()) * ((new Color(image.getRGB(x - 1, y    ))).getRed());
            average_intensity += ((new Color(image.getRGB(x    , y    ))).getRed()) * ((new Color(image.getRGB(x    , y    ))).getRed());
            average_intensity += ((new Color(image.getRGB(x + 1, y    ))).getRed()) * ((new Color(image.getRGB(x + 1, y    ))).getRed());

            average_intensity += ((new Color(image.getRGB(x - 1, y + 1))).getRed()) * ((new Color(image.getRGB(x - 1, y + 1))).getRed());
            average_intensity += ((new Color(image.getRGB(x    , y + 1))).getRed()) * ((new Color(image.getRGB(x    , y + 1))).getRed());
            average_intensity += ((new Color(image.getRGB(x + 1, y + 1))).getRed()) * ((new Color(image.getRGB(x + 1, y + 1))).getRed());

        } else {
            average_intensity += (new Color(image.getRGB(x - 1, y - 1))).getRed();
            average_intensity += (new Color(image.getRGB(x    , y - 1))).getRed();
            average_intensity += (new Color(image.getRGB(x + 1, y - 1))).getRed();

            average_intensity += (new Color(image.getRGB(x - 1, y    ))).getRed();
            average_intensity += (new Color(image.getRGB(x    , y    ))).getRed();
            average_intensity += (new Color(image.getRGB(x + 1, y    ))).getRed();

            average_intensity += (new Color(image.getRGB(x - 1, y + 1))).getRed();
            average_intensity += (new Color(image.getRGB(x    , y + 1))).getRed();
            average_intensity += (new Color(image.getRGB(x + 1, y + 1))).getRed();
        }

        return average_intensity / 9;
    }


    /**
     * Draw a green sqaure on the image centred on the pixel (x, y)
     * @param image image to draw on
     * @param x x-coordinate of the centre of the square
     * @param y y-coordinate of the centre of the square
     * @return image with square drawn on it
     */
    private BufferedImage drawSquare(BufferedImage image, int x, int y){
        Color c = new Color(0, 255, 0);
        for (int i = x - 3; i <= x + 3; i++) {
            for (int j = y - 3; j <= y + 3; j++) {
                image.setRGB(i, j, c.getRGB());
            }
        }
        return image;
    }


    /**
     * Detects corners using Harris Corner Detection
     * @param image greyscale image
     * @return image with corners detected
     */
    public BufferedImage detectHarrisCorners(BufferedImage image, BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                image.getType());
        Color c;

        BufferedImage x_image_derivative = calculateXPartialDerivative(image);
        BufferedImage y_image_derivative = calculateYPartialDerivative(image);

        int average_x_intensity;
        int average_y_intensity;
        // Average of the square of the intensities
        int average_x_intensity_squared;
        int average_y_intensity_sqaured;

        double determinant;
        double trace;
        double r_score;

        // Threshold is an empirically determined value
        double threshold = 20000000.0;

        for (int i = 3; i < image.getWidth() - 3; i++){
            for (int j = 3; j < image.getHeight() - 3; j++) {
                //Get average intensities
                average_x_intensity = calculateAverageWindowIntensity(x_image_derivative, i, j, false);
                average_y_intensity = calculateAverageWindowIntensity(y_image_derivative, i, j, false);
                average_x_intensity_squared = calculateAverageWindowIntensity(x_image_derivative, i, j, true);
                average_y_intensity_sqaured = calculateAverageWindowIntensity(y_image_derivative, i, j, true);

                //Calculate determinant and trace of the structure tensor for the window
                determinant = (float)(average_x_intensity_squared * average_y_intensity_sqaured - ((average_x_intensity * average_y_intensity) * (average_x_intensity * average_y_intensity)));
                trace = (float)(average_x_intensity_squared + average_y_intensity_sqaured);

                //Calculate the R score for the window
                r_score = ( determinant - 0.15 * trace * trace);
                
                if (r_score > threshold) {
                    transformed_image = drawSquare(transformed_image, i, j);
                } else {
                    c = new Color(original_image.getRGB(i, j));
                    transformed_image.setRGB(i, j, c.getRGB());
                }
            }
        }

        return transformed_image;
    }


    /**
     * Detects corners using Shi-Tomasi Corner Detection
     * @param image greyscale image
     * @return image with corners detected
     */
    public BufferedImage detectShiTomasiCorners(BufferedImage image, BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                image.getType());
        Color c;

        BufferedImage x_image_derivative = calculateXPartialDerivative(image);
        BufferedImage y_image_derivative = calculateYPartialDerivative(image);

        int average_x_intensity;
        int average_y_intensity;
        // Average of the square of the intensities
        int average_x_intensity_squared;
        int average_y_intensity_sqaured;

        double determinant;
        double trace;
        double r_score;
        //Eigenvalues
        double e1;
        double e2;

        // Threshold is an empirically determined value
        double threshold = 10000.0;

        for (int i = 3; i < image.getWidth() - 3; i++){
            for (int j = 3; j < image.getHeight() - 3; j++) {
                //Get average intensities
                average_x_intensity = calculateAverageWindowIntensity(x_image_derivative, i, j, false);
                average_y_intensity = calculateAverageWindowIntensity(y_image_derivative, i, j, false);
                average_x_intensity_squared = calculateAverageWindowIntensity(x_image_derivative, i, j, true);
                average_y_intensity_sqaured = calculateAverageWindowIntensity(y_image_derivative, i, j, true);

                //Calculate determinant and trace of the structure tensor for the window
                determinant = (float)(average_x_intensity_squared * average_y_intensity_sqaured - ((average_x_intensity * average_y_intensity) * (average_x_intensity * average_y_intensity)));
                trace = (float)(average_x_intensity_squared + average_y_intensity_sqaured);

                //Calculate the eigenvalues of the structure tensor
                e1 = 0.5 * (trace - Math.sqrt(trace * trace - 4 * determinant));
                e2 = 0.5 * (trace + Math.sqrt(trace * trace - 4 * determinant));

                //Calculate the R score for the window
                r_score = Math.min(e1, e2);
                
                if (r_score > threshold) {
                    transformed_image = drawSquare(transformed_image, i, j);
                } else {
                    c = new Color(original_image.getRGB(i, j));
                    transformed_image.setRGB(i, j, c.getRGB());
                }
            }
        }
        return transformed_image;
    }


    /**
     * Converts greyscale image to false colour using a special color map
     * @param original_image original image is assumed to be a greyscale image
     * @return False colour image
     */
    public BufferedImage convertFalseColour(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int greyscale_val;
        for (int x = 0; x < transformed_image.getWidth(); x++){
            for (int y = 0; y < transformed_image.getHeight(); y++){
                // Fetch current pixel's RGB values
                c = new Color(original_image.getRGB(x, y));
                greyscale_val = c.getRed();
                c = getFalseColourMapValue(greyscale_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Map greyscale intensity value to false color RGB values
     * @param intensity greyscale intensity value
     * @return False colour mapping
     */
    private Color getFalseColourMapValue(int intensity) {
        double adjusted_intensity = intensity / 255.0; // Adjust intensity to range [0, 1]
        if (adjusted_intensity >= 0.0 && adjusted_intensity < 0.25){
            return new Color(0, 0, 128);
        } else if (adjusted_intensity >= 0.25 && adjusted_intensity < 0.5) {
            return new Color(0, 255, 0);
        } else if (adjusted_intensity >= 0.5 && adjusted_intensity < 0.75) {
            return new Color(255, 255, 0);
        } else if (adjusted_intensity >= 0.75 && adjusted_intensity < 1.0) {
            return new Color(255, 128, 0);
        } else {
            return new Color(255, 0, 0);
        }
    }
}
