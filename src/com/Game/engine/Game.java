package com.Game.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Game.data.GuiElement;
import com.Game.data.World;
import com.Game.engine.Camera;
import com.Game.engine.Renderer;
import com.Game.engine.Window;
import com.Game.enumerations.ActorType;
import com.Game.enumerations.GameState;
import com.Game.enumerations.GuiSpriteType;
import com.Game.utilities.ActorManager;
import com.Game.utilities.Coordinate;
import com.Game.utilities.GuiElementCreator;
import com.Game.utilities.ItemManager;
import com.Game.utilities.SpriteCreator;
import com.Game.utilities.SpriteLoader;
import com.Game.utilities.Util;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -5226776943692411279L;

    public static Game instance;

    public static final int WIDTH                  = 448;			                   // window width
    public static final int HEIGHT                 = 256;                              // window height

    public static final int SCREEN_MULTIPLIER      = 3;

    public static final String TITLE               = "Project Dungeoncrawl";

    public static final int CAMERA_WIDTH           = 9 * 32 + 19;
    public static final int CAMERA_HEIGHT          = 5 * 32;

    public static final int CAMERA_POSX            = 4 * 32 + 13;
    public static final int CAMERA_POSY            = 0;

    public static final int CAMERAZOOM             = 1;                                // level of zoom
    public static final double FRAME_CAP           = 60.0;                             // cap the framerate to this

    public static final String SPRITESHEETNAME     = "images/UI_mockup.png";           // name of the spritesheet
    public static final String FRAMICONPATH        = "resources/images/icon.png";      // path to frame icon.

    public static final int SPRITEGRIDSIZE         = 32;
    public static final int ITEMSPRITESIZEMULT     = 2;

    public static final String CUSTOMFONTNAME      = "coders_crux";		               // name of the custom font
    public static final String CUSTOMFONTEXTENSION = ".ttf";			               // file extension name
    public static final String CUSTOMFONTFOLDER    = "coders_crux";		               // folder name within 'resources/fonts/'

    public static final int BASEFONTSIZE 		   = 8;						           // base font size used when rendering strings.
    public static final int LINEHEIGHT			   = 2;						           

    public static final int WORLD_WIDTH            = 10;
    public static final int WORLD_HEIGHT           = 10;

    public static boolean renderMinimap            = false;
    
    // ------------------------------
    // DEBUG

    public static boolean drawGUIRects             = false;
    public static final Color GUIDebugRectColor    = Color.green;
    public static boolean drawCameraRect           = false;
    public static final Color cameraRectColor      = Color.red;
    public static boolean drawItemRects            = false;
    public static final Color itemRectColor        = Color.red;

    public static final boolean drawDebugInfo      = true;
    public static final Color debugInfoColor       = Color.green;

    // -----------------------------

    private float deltaTime = 0;

    private Thread thread;
    private boolean isRunning = false;
    private Window window;

    private Font customFont;
    private Camera camera;
    private SpriteCreator spriteCreator;
    private Handler handler;
    private GuiRenderer guiRenderer;
    private GameState gamestate;
    
    private List<GuiElement> allGuiElements;
    private List<GuiElement> guiElements;
    private List<GuiElement> equipmentSlots;
    private List<GuiElement> inventorySlots;
    
    private Map<Character, BufferedImage> alphabets = new HashMap<Character, BufferedImage>(); 
    private Map<GuiSpriteType, BufferedImage> guiSprites = new HashMap<GuiSpriteType, BufferedImage>();
    
    private World world;
    private ActorManager actorManager;
    private Minimap minimap;
    private Point mousePos;

    public Game() {

        if(instance != null) return;
        Game.instance = this;

        // create object handler
        this.handler = new Handler();

        // create key listener for inputs.
        this.addKeyListener(new KeyInput());

        // create mouse input object
        MouseInput mouseInput = new MouseInput();

        // create mouse listener
        this.addMouseMotionListener(mouseInput);
        this.addMouseListener(mouseInput);

        // load custom font
        Util.loadCustomFont();

        // create window 
        this.window = new Window(WIDTH * SCREEN_MULTIPLIER, HEIGHT * SCREEN_MULTIPLIER, TITLE, this);

        // create sprite creator
        this.spriteCreator = new SpriteCreator(SPRITESHEETNAME);

        // create guiRenderer
        this.guiRenderer = new GuiRenderer();

        // initiate sprite font
        this.alphabets = SpriteLoader.initiateAlphabets();

        // load all gui sprites
        this.guiSprites = SpriteLoader.loadGuiSprites();
        System.out.println("Loaded " + this.guiSprites.size() + "/" + GuiSpriteType.values().length + " gui sprites succesfully.");

        // create gui-elements
        this.guiElements = GuiElementCreator.createGuiElements();
        System.out.println("Created " + this.guiElements.size() + " GUI-elements.");

        // create equipment slot gui-elements
        this.equipmentSlots = GuiElementCreator.createEquipmentSlots();
        System.out.println("Created " + this.equipmentSlots.size() + " equipment slots.");
        
        // create inventory slots
        this.inventorySlots = GuiElementCreator.createInventorySlots();
        System.out.println("Created " + this.inventorySlots.size() + " inventory slots.");
        
        // merge all gui elements into one single list
        this.allGuiElements = new ArrayList<GuiElement>(this.guiElements);
        this.allGuiElements.addAll(this.equipmentSlots);
        this.allGuiElements.addAll(this.inventorySlots);        
        
        // create camera
        this.camera = new Camera();

        // create actor manager
        setActorManager(new ActorManager());

        // set gamestate
        this.gamestate = GameState.Ingame;

        // create world
        this.world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        System.out.println("Created world succesfully, tiles: " + this.world.getTiles().size());

        // create minimap
        this.minimap = new Minimap();

        // debug: create items on screen
        ItemManager.createAllItems();
        
        // create mock up player actor
        actorManager.createActorInstance("Player_01", new Coordinate(0, 0), new Coordinate(3, 3), null, 0, 0, ActorType.Player, 3, 1, 5);
        
        // start game thread
        start();

        System.out.println("Game started succesfully!");
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            isRunning = false;
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void run() { 
        gameLoop();
    }

    private void gameLoop() {

        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        int frames = 0;
        long frameCounter = 0;

        final double frameTime = 1 / FRAME_CAP;
        final long SECOND = 1000000000L;

        boolean render = false;
        long now = 0l, passedTime = 0l;

        while(isRunning) {

            render = false;

            now = System.nanoTime();
            passedTime = now - lastTime;
            lastTime = now;

            unprocessedTime += passedTime / (double) SECOND;
            frameCounter += passedTime;

            while(unprocessedTime > frameTime) {

                render = true;
                unprocessedTime -= frameTime;

                tick();

                if(frameCounter >= SECOND) {
                    window.SetCustomTitle("FPS: " + frames);
                    frames = 0;
                    frameCounter = 0;

                    // 1 nanosecond = 10^-9 --> 0.000000001 seconds
                    // 71531 ns = 0.000071531 seconds
                    deltaTime = (System.nanoTime() - now) * 0.000000001f;
                }
            }

            // render the scene
            if(isRunning && render) {
                render();
                frames++;
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // DRAW GRAPHICS HERE ---------------------------------

        Renderer.preRender(g);

        // END DRAW -------------------------------------------

        g.dispose();
        bs.show();
    }

    private void tick() { handler.tick(); }
    public static void main(String args[]) { new Game(); }
    public Window getWindow() { return this.window; }
    public Font getCustomFont() { return customFont; }
    public void setCustomFont(Font customFont) { this.customFont = customFont; }
    public Camera getCamera() { return camera; }
    public void setCamera(Camera camera) { this.camera = camera; }
    public SpriteCreator getSpriteCreator() { return spriteCreator;}
    public void setSpriteCreator(SpriteCreator spriteCreator) { this.spriteCreator = spriteCreator; }
    public Handler getHandler() { return handler; }
    public void setHandler(Handler handler) { this.handler = handler; }

    public ActorManager getActorManager() {
        return actorManager;
    }

    public void setActorManager(ActorManager actorManager) {
        this.actorManager = actorManager;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public GameState getGamestate() {
        return gamestate;
    }

    public void setGamestate(GameState gamestate) {
        this.gamestate = gamestate;
    }

    public GuiRenderer getGuiRenderer() {
        return guiRenderer;
    }

    public void setGuiRenderer(GuiRenderer guiRenderer) {
        this.guiRenderer = guiRenderer;
    }

    public List<GuiElement> getGuiElements() {
        return guiElements;
    }

    public void setGuiElements(List<GuiElement> guiElements) {
        this.guiElements = guiElements;
    }

    public Point getMousePos() {
        return mousePos;
    }

    public void setMousePos(Point mousePos) {
        this.mousePos = mousePos;
    }

    public Minimap getMinimap() {
        return minimap;
    }

    public void setMinimap(Minimap minimap) {
        this.minimap = minimap;
    }

    public List<GuiElement> getEquipmentSlots() {
        return equipmentSlots;
    }

    public void setEquipmentSlots(List<GuiElement> equipmentSlots) {
        this.equipmentSlots = equipmentSlots;
    }

    public List<GuiElement> getInventorySlots() {
        return inventorySlots;
    }

    public void setInventorySlots(List<GuiElement> inventorySlots) {
        this.inventorySlots = inventorySlots;
    }

    public Map<Character, BufferedImage> getAlphabets() {
        return alphabets;
    }

    public void setAlphabets(Map<Character, BufferedImage> alphabets) {
        this.alphabets = alphabets;
    }

    public Map<GuiSpriteType, BufferedImage> getGuiSprites() {
        return guiSprites;
    }

    public void setGuiSprites(Map<GuiSpriteType, BufferedImage> guiSprites) {
        this.guiSprites = guiSprites;
    }

    public List<GuiElement> getAllGuiElements() {
        return allGuiElements;
    }

    public void setAllGuiElements(List<GuiElement> allGuiElements) {
        this.allGuiElements = allGuiElements;
    }

}
