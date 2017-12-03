package com.Game.engine;

import com.Game.enumerations.Direction;
import com.Game.gameobjects.GameObject;
import com.Game.utilities.Coordinate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	// ------------------------ RANDOMIZATION -------------------------

	public static Direction GetRandomCardinalDirection() { return Direction.values()[Util.GetRandomInteger(0, 4)]; }
	public static int GetRandomInteger() { return ThreadLocalRandom.current().nextInt(0, 101); }
	public static int GetRandomInteger(int min, int max) { return ThreadLocalRandom.current().nextInt(min, max); }

	// ------------------------ MATH ----------------------------------

	// http://stackoverflow.com/questions/16656651/does-java-have-a-clamp-function
	public static int clamp(int val, int min, int max) { return Math.max(min, Math.min(max, val)); }

	// http://www.java-gaming.org/index.php?topic=34706.0
	public static float lerp(float point1, float point2, float alpha) { return point1 + alpha * (point2 - point1); }

	// http://www.java-gaming.org/index.php?topic=34706.0
	public static int lerp(int point1, int point2, float alpha) { return Math.round(point1 + alpha * (point2 - point1)); }

	// ----------------------- IMAGE UTILS ----------------------------

	// http://stackoverflow.com/questions/4248104/applying-a-tint-to-an-image-in-java
	public static BufferedImage tint(BufferedImage image, boolean darker) {

		// copy the image 
		BufferedImage tintedImage = Util.deepCopy(image);

		// loop through all pixels
		for(int x = 0; x < tintedImage.getWidth(); x++) {
			for (int y = 0; y < tintedImage.getHeight(); y++) {

				// second parameter is if there is alpha channel.
				Color color = new Color(tintedImage.getRGB(x, y), true);

				Color tintedColor = null;

				// make the pixel's color darker.
				if(darker) tintedColor = color.darker().darker().darker();
				else tintedColor = color.brighter();

				// apply color to new image.
				tintedImage.setRGB(x, y, tintedColor.getRGB());
			}
		}
		return tintedImage;
	}

	// https://stackoverflow.com/questions/4248104/applying-a-tint-to-an-image-in-java
	public static BufferedImage tintWithColor(BufferedImage image, Color tintColor) {

		// copy the image 
		BufferedImage tintedImage = Util.deepCopy(image);

		// loop through all pixels
		for(int x = 0; x < tintedImage.getWidth(); x++) {
			for (int y = 0; y < tintedImage.getHeight(); y++) {

				// second parameter is if there is alpha channel.
				Color pixelColor = new Color(tintedImage.getRGB(x, y), true);

				int r = (pixelColor.getRed() + tintColor.getRed()) / 2;
				int g = (pixelColor.getGreen() + tintColor.getGreen()) / 2;
				int b = (pixelColor.getBlue() + tintColor.getBlue()) / 2;
				int a = pixelColor.getAlpha();

				int rgba = (a << 24) | (r << 16) | (g << 8) | b;

				// apply color to new image.
				tintedImage.setRGB(x, y, rgba);
			}
		}
		return tintedImage;
	}

	public static BufferedImage changeImageColor(BufferedImage image, Color color) {
	    
	       // copy the image 
        BufferedImage tintedImage = Util.deepCopy(image);

        // loop through all pixels
        for(int x = 0; x < tintedImage.getWidth(); x++) {
            for (int y = 0; y < tintedImage.getHeight(); y++) {

                // second parameter is if there is alpha channel.
                Color pixelColor = new Color(tintedImage.getRGB(x, y), true);

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = pixelColor.getAlpha();

                int rgba = (a << 24) | (r << 16) | (g << 8) | b;

                // apply color to new image.
                tintedImage.setRGB(x, y, rgba);
            }
        }
        return tintedImage;
	}
	
	public static BufferedImage highlightTileBorders(BufferedImage image, Color tintColor) {

		// copy the image 
		BufferedImage tintedImage = Util.deepCopy(image);

		// loop through all pixels
		for(int x = 0; x < tintedImage.getWidth(); x++) {
			for (int y = 0; y < tintedImage.getHeight(); y++) {

				// get pixel + alpha values
				Color pixelColor = new Color(tintedImage.getRGB(x, y), true);
				int r, g, b, a, rgba;


				// don't highlight the corner pixels
				if((x == 0 && y == 0) || (x == tintedImage.getWidth() - 1 && y == 0) ||
						(x == 0 && y == tintedImage.getHeight() - 1) || (x == tintedImage.getWidth() - 1 && y == tintedImage.getHeight() - 1)) {

					// use default pixel color.
					r = pixelColor.getRed();
					g = pixelColor.getGreen();
					b = pixelColor.getBlue();
					a = pixelColor.getAlpha();
					rgba = (a << 24) | (r << 16) | (g << 8) | b;

					tintedImage.setRGB(x, y, rgba);

					continue;
				}

				// if the pixel is not a corner pixel
				if(x == 0 || x == tintedImage.getWidth() - 1 || y == 0 || y == tintedImage.getHeight() - 1) {

					// tint the border with some color
					// calculates average values of all color channels.
					r = (pixelColor.getRed() + tintColor.getRed()) / 2;
					g = (pixelColor.getGreen() + tintColor.getGreen()) / 2;
					b = (pixelColor.getBlue() + tintColor.getBlue()) / 2;
					a = 255;
					rgba = (a << 24) | (r << 16) | (g << 8) | b;

					// ALPHA	RED		 GREEN    BLUE
					// 11111111 00000000 00000000 00000000 32-bit integer

				} else {

					// use default pixel color.
					r = pixelColor.getRed();
					g = pixelColor.getGreen();
					b = pixelColor.getBlue();
					a = pixelColor.getAlpha();
					rgba = (a << 24) | (r << 16) | (g << 8) | b;

				}

				// apply color to new image.
				tintedImage.setRGB(x, y, rgba);
			}
		}
		return tintedImage;
	}

	// http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	// ------------------ RESOURCE LOADING -----------------------

	// https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
	public static void loadCustomFont() {

		String fullPath = "resources/fonts/" + Game.CUSTOMFONTFOLDER + "/" + Game.CUSTOMFONTNAME + Game.CUSTOMFONTEXTENSION;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font font = null;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(fullPath));
			ge.registerFont(font);
		} catch (FontFormatException | IOException e) { e.printStackTrace(); }

		// cache the font
		Game.instance.setCustomFont(font);

		System.out.println("Succesfully loaded custom font: " + font.getFontName());
	}

	// ----------------- OTHER UTILS -------------------------------

	public static String colorToString(Color color) { return color.getRed() + "," + color.getGreen() + "," + color.getBlue(); }

	public static String capitalize(String s) {
		String a = s.substring(0, 1).toUpperCase();
		String b = s.substring(1, s.length()).toLowerCase();
		return a + b;
	}

	public static Coordinate calculateCameraPos(GameObject go) {
	    
	    int spriteSize = 16;
	    
	    int x = 0, y = 0;
	    Camera cam = Game.instance.getCamera();
	    
	    if(cam == null) {
	        System.out.println("camera is null!");
	        return null;
	    }
	    
	    if(go != null) {
	        x = ((-go.GetWorldPosition().x * Game.CAMERAZOOM) - (spriteSize - Game.WIDTH / 2)) / Game.CAMERAZOOM;
	        y = ((-go.GetWorldPosition().y * Game.CAMERAZOOM) - (spriteSize - Game.HEIGHT / 2)) / Game.CAMERAZOOM;
	    } else {
	        if(cam.getFollowTarget() != null) { 
	            x = ((-cam.getFollowTarget().GetWorldPosition().x * Game.CAMERAZOOM) - (spriteSize - Game.WIDTH / 2)) / Game.CAMERAZOOM;
	            y = ((-cam.getFollowTarget().GetWorldPosition().y * Game.CAMERAZOOM) - (spriteSize - Game.HEIGHT / 2)) / Game.CAMERAZOOM;
	        }
	    }
	    
		return new Coordinate(x, y);
	}
}
