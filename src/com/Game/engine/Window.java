package com.Game.engine;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.Game.engine.Game;
import com.Game.enumerations.CursorMode;

public class Window extends Canvas {

    private static final long serialVersionUID = 351245801233048538L;

    private String title;
    private JFrame frame;

    public Window(int width, int height, String title, Game game) {

        this.title = title;

        // create a new frame
        frame = new JFrame(title);

        // set cursor
        setCursor(CursorMode.Default);

        // add window borders
        int actualHeight = height + 25;
        int actualWidth = width + 5;

        Dimension dim = new Dimension(actualWidth, actualHeight);

        // set the dimensions of the frame
        frame.setPreferredSize(dim);
        frame.setMinimumSize(dim);
        frame.setMaximumSize(dim);

        // set closing operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set if the frame is resizeable
        frame.setResizable(false);

        // set the location
        frame.setLocationRelativeTo(null);

        // set frame icon
        ImageIcon icon = new ImageIcon(Game.FRAMICONPATH);
        frame.setIconImage(icon.getImage());

        // add our game to the frame
        frame.add(game);

        // pack the frame
        frame.pack();

        // set the frame to be visible.
        frame.setVisible(true);
    }

    public void setCursor(CursorMode mode) {

        Image img = null;

        switch(mode) {
        case Attack:
            img = Toolkit.getDefaultToolkit().getImage("./resources/images/cursor.png");
            break;
        case Default:
            img = Toolkit.getDefaultToolkit().getImage("./resources/images/hand.png");
            break;
        case Dragging:
            img = Toolkit.getDefaultToolkit().getImage("./resources/images/grab.png");
            break;
        case Inspect:
            img = Toolkit.getDefaultToolkit().getImage("./resources/images/inspect.png");
            break;
        default:
            break;
        }

        // create custom cursor
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "customCursor");

        // set cursor
        frame.getContentPane().setCursor(cursor);
    }

    public void setCursor(String imgPath) {
        // create cursor image
        Image img = Toolkit.getDefaultToolkit().getImage(imgPath);

        // create custom cursor
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "customCursor");

        // set cursor
        frame.getContentPane().setCursor(cursor);
    }

    public JFrame getFrame() { return this.frame; }
    public void SetCustomTitle(String text) { frame.setTitle(title + ", " + text); }
}
