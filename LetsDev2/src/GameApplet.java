
import java.applet.Applet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import sound.AudioPlayer;


public class GameApplet extends Applet {
    // Define constants for the game
    static final int CANVAS_WIDTH = 800;    // width and height of the game screen
    static final int CANVAS_HEIGHT = 600;
    static final int UPDATE_RATE = 4;    // number of game update per second
    static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;


   // @SuppressWarnings("compatibility:4806502096835282862")
    private static final long serialVersionUID = 1L; // nanoseconds
    // ......
    
    // Enumeration for the states of the game.
    static enum State {
      INITIALISED, PLAYING, PAUSED, GAMEOVER, DESTROYED
    }
    static State state;   // current state of the game
         

    static public GameController controller;
    public InputHandler listener;
    private Image dbImage;
    private Graphics dbg; 
    
    public void init(){
        setSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        gameInit();
        gameStart();
        //gameLoop();
    }
    public void update (Graphics g)
    {

        // initialize buffer
        if (dbImage == null)
        {
            dbImage = createImage (this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics ();
        }

        // clear screen in background
        dbg.setColor (getBackground ());
        dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

        // draw elements in background
        dbg.setColor (getForeground());
        paint (dbg);
        dbg.drawString("TEST", 200, 100);
        // draw image on the screen
        g.drawImage (dbImage, 0, 0, this);

    } 
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //g2d.setBackground(Color.BLACK);  // may use an image for background
        g2d.setColor(Color.WHITE);
        g2d.drawString("Draw ", 10, 10);
        // Draw the game objects
        gameDraw(g2d); 
    }

    
    // All the game related codes here
    
    // Initialize all the game objects, run only once in the constructor of the main class.
    public void gameInit() {
        // ...... 
        state = State.INITIALISED;
        listener = new InputHandler();
        this.addKeyListener(listener);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        controller = new GameController(this);
        controller.setGameWorld(new GameWorld((int)(System.nanoTime()/ 1000000L))); // random seed
        controller.setRenderEngine(new RenderEngine(getCodeBase(), this, controller));
        controller.setSettings(new UserSettings());
        controller.setSoundManager(new AudioPlayer(this.getCodeBase()));
        
        
        listener.setGameController(controller);
        controller.startGame();
    }
    
    // Shutdown the game, clean up code that runs only once.
    public void gameShutdown() {
      // ...... 
    }
    
    // To start and re-start the game.
    public void gameStart() { 
      // Create a new thread
      Thread gameThread =  new Thread() {
         // Override run() to provide the running behavior of this thread.
         @Override
         public void run() {
            gameLoop();
         }
      };
      // Start the thread. start() calls run(), which in turn calls gameLoop().
      gameThread.start();
    }
    
    // Run the game loop here.
    private void gameLoop() {
        // Regenerate the game objects for a new game
        // ......
        state = State.PLAYING;
        //controller.startGame();
        //world = new GameWorld((int)(System.nanoTime()/ 1000000L)); // random seed
        //engine = new RenderEngine();
        // Game loop
        long beginTime, timeTaken, timeLeft;
        while (true) {
            beginTime = System.nanoTime();
            if (state == State.GAMEOVER) break;  // break the loop to finish the current play
            if (state == State.PLAYING) {
            // Update the state and position of all the game objects,
            // detect collisions and provide responses.
                gameUpdate();
            }

            // Refresh the display
            repaint();

            // Delay timer to provide the necessary delay to meet the target rate
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000L;  // in milliseconds
            timeLeft = 10;
            if (timeLeft < 10) timeLeft = 10;   // set a minimum
            try {
            // Provides the necessary delay and also yields control so that other thread can do work.
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    // Update the state and position of all the game objects,
    // detect collisions and provide responses.
    public void gameUpdate() { 
        listener.doActions();
        controller.update();
    }
    // Refresh the display. Called back via repaint(), which invoke the paintComponent().
    private void gameDraw(Graphics2D g2d) {
      g2d.setBackground(new Color(0,0,0));

      switch (state) {
         case INITIALISED:
            g2d.drawString("Initialised.", 10, 20);
            break;
         case PLAYING:
            controller.draw(g2d);
            g2d.drawString("Playing.", 10, 30);
            break;
         case PAUSED:
            // ......
            break;
         case GAMEOVER:
            // ......
            break;
      }
      // ...... 
    }

}
