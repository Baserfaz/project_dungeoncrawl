package com.Game.utilities;

import com.Game.engine.Camera;
import com.Game.engine.Game;
import com.Game.enumerations.Direction;
import com.Game.gameobjects.GameObject;

import java.lang.Math;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
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
            x = ((-go.getWorldPosition().x * Game.CAMERAZOOM) - (spriteSize - Game.WIDTH / 2)) / Game.CAMERAZOOM;
            y = ((-go.getWorldPosition().y * Game.CAMERAZOOM) - (spriteSize - Game.HEIGHT / 2)) / Game.CAMERAZOOM;
        } else {
            if(cam.getFollowTarget() != null) { 
                x = ((-cam.getFollowTarget().getWorldPosition().x * Game.CAMERAZOOM) - (spriteSize - Game.WIDTH / 2)) / Game.CAMERAZOOM;
                y = ((-cam.getFollowTarget().getWorldPosition().y * Game.CAMERAZOOM) - (spriteSize - Game.HEIGHT / 2)) / Game.CAMERAZOOM;
            }
        }

        return new Coordinate(x, y);
    }
}
