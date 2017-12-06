package com.Game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import com.Game.utilities.Coordinate;

public class Renderer {

    public static void preRender(Graphics g) {

        // get references
        Handler handler = Game.instance.getHandler();
        GuiRenderer guirenderer = Game.instance.getGuiRenderer();
        Camera cam = Game.instance.getCamera();
        Graphics2D g2d = (Graphics2D) g;

        // set rendering hints
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);   

        // set background
        Renderer.fillScreen(g, Color.white);

        // set zoom level
        g2d.scale(1, 1);

        // camera follow object
        if(cam.isFollowing()) {

            // calculate camera position
            Coordinate camPos = Util.calculateCameraPos(null);

            // move the camera
            g.translate(camPos.x, camPos.y);

            // update camera position
            cam.Update(new Coordinate(-camPos.x, -camPos.y), Game.CAMERA_WIDTH, Game.CAMERA_HEIGHT);

        }

        // render GUI
        guirenderer.render(g);

        // render camera debug 
        if(Game.drawCameraRect) {
            Rectangle camRect = cam.getCameraBounds();
            g.setColor(Game.cameraRectColor);
            g.drawRect(camRect.x, camRect.y, camRect.width, camRect.height);
        }

        // render game objects
        handler.render(g);

    }

    public static void fillScreen(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(0, 0, Game.WIDTH * Game.SCREEN_MULTIPLIER, Game.HEIGHT * Game.SCREEN_MULTIPLIER);
    }

}
