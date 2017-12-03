package com.Engine.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.List;

import com.Engine.data.World;
import com.Engine.engine.Camera;
import com.Engine.engine.Renderer;
import com.Engine.engine.Util;
import com.Engine.engine.Window;
import com.Engine.enumerations.CursorMode;
import com.Engine.enumerations.GameState;
import com.Engine.enumerations.ItemType;
import com.Engine.enumerations.SpriteType;
import com.Engine.utilities.ActorManager;
import com.Engine.utilities.Coordinate;
import com.Engine.utilities.ItemManager;
import com.Engine.utilities.SpriteCreator;

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
	
	public static final String CUSTOMFONTNAME      = "coders_crux";		               // name of the custom font
	public static final String CUSTOMFONTEXTENSION = ".ttf";			               // file extension name
	public static final String CUSTOMFONTFOLDER    = "coders_crux";		               // folder name within 'resources/fonts/'

	public static final int BASEFONTSIZE 		   = 8;						           // base font size used when rendering strings.
	public static final int LINEHEIGHT			   = 2;						           

	public static final int WORLD_WIDTH            = 10;
	public static final int WORLD_HEIGHT           = 10;
	
	// ------------------------------
	// DEBUG
	
	public static boolean drawGUIRects             = false;
	public static final Color GUIDebugRectColor    = Color.green;
	public static boolean drawCameraRect           = false;
	public static final Color cameraRectColor      = Color.red;
	public static boolean drawItemRects            = false;
	public static final Color itemRectColor        = Color.red;
	
	public static boolean renderMinimap            = true;
	
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
	private GUIRenderer guiRenderer;
	private GameState gamestate;
	
	private List<GuiString> guiStrings;
	private List<GuiElement> guiElements;
	
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
		this.guiRenderer = new GUIRenderer();
		
        // initiate sprite font
        this.guiRenderer.initiateAlphabets();
		
        // load all gui sprites
        this.guiRenderer.loadGuiSprites();
        System.out.println("Loaded " + this.guiRenderer.guiSprites.size() + " gui sprites succesfully.");
        
		// create gui-elements
		this.guiElements = GuiElementCreator.createGuiElements();
		System.out.println("Created " + this.guiElements.size() + " GUI-elements.");
		
		// create gui-strings
		this.guiStrings = GuiElementCreator.createGuiStrings();
		System.out.println("Created " + this.guiStrings.size() + " gui strings.");
		
		// create camera
		this.camera = new Camera();
		
		// create actor manager
		setActorManager(new ActorManager());
		
		// set gamestate
		this.gamestate = GameState.Ingame;
		
		// create world
		this.world = new World(WORLD_WIDTH, WORLD_HEIGHT);
		
		System.out.println("Created world succesfully, tiles: " + this.world.getTiles().size());
		
		this.minimap = new Minimap();
		
		// debug: create item on screen
		ItemManager.createItem("Health Potion", ItemType.Potion, new Coordinate(500, 400), SpriteType.RedPotion, 32, 2);
		ItemManager.createItem("Health Potion", ItemType.Potion, new Coordinate(700, 400), SpriteType.RedPotion, 32, 2);
		
		// start game thread
		Start();
		
		System.out.println("Game started succesfully!");
	}

	public synchronized void Start() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public synchronized void Stop() {
		try {
			thread.join();
			isRunning = false;
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void run() { 
	    GameLoop();
	}
	
	private void GameLoop() {

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

    public GUIRenderer getGuiRenderer() {
        return guiRenderer;
    }

    public void setGuiRenderer(GUIRenderer guiRenderer) {
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

    public List<GuiString> getGuiStrings() {
        return guiStrings;
    }

    public void setGuiStrings(List<GuiString> guiStrings) {
        this.guiStrings = guiStrings;
    }

    public Minimap getMinimap() {
        return minimap;
    }

    public void setMinimap(Minimap minimap) {
        this.minimap = minimap;
    }
	
}
