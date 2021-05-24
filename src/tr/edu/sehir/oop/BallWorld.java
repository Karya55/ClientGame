package tr.edu.sehir.oop;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 * The control logic and main display panel for game.
 *
 * @author Hock-Chuan Chua
 * @version October 2010
 * modified by e gul
 */
public class BallWorld extends JPanel {
    private static final int UPDATE_RATE = 30;    // Frames per second (fps)

    private int ball_count = 4;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private Color ball_color = Color.BLUE;
    private ContainerBox box;  // The container rectangular box
    client1 client;

    private DrawCanvas canvas; // Custom canvas for drawing the box/ball
    private int canvasWidth;
    private int canvasHeight;

    /**
     * Constructor to create the UI components and init the game objects.
     * Set the drawing canvas to fill the screen (given its width and height).
     *
     * @param width : screen width
     * @param height : screen height
     */
    public BallWorld(int width, int height) {

        canvasWidth = width;
        canvasHeight = height;
        
        client = new client1();

        // Init the ball at a random location (inside the box) and moveAngle
        for(int i =0; i<ball_count; i++) {
        	Random rand = new Random();
            int radius = 20;
            int x = rand.nextInt(canvasWidth - radius * 2 - 20) + radius + 10;
            int y = rand.nextInt(canvasHeight - radius * 2 - 20) + radius + 10;
            int speed = 5;
            int angleInDegree = rand.nextInt(360);
            balls.add(new Ball(x, y, radius, speed, angleInDegree, ball_color));
        }
        

//
        // Init the Container Box to fill the screen
        box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);

        // Init the custom drawing panel for drawing the game
        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);

        // Handling window resize.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                Dimension dim = c.getSize();
                canvasWidth = dim.width;
                canvasHeight = dim.height;
                // Adjust the bounds of the container to fill the window
                box.set(0, 0, canvasWidth, canvasHeight);
            }
        });

        // Start the ball bouncing
		client.start();
        gameStart();

	
    }
    /*
    public void gameStart(){
        gameThread gmthr = new gameThread(this,UPDATE_RATE);
        gmthr.start();

    }
    */

    // Start the ball bouncing.
    public void gameStart() {
        // Run the game logic in its own thread.
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    // Execute one time-step for the game
                    gameUpdate();
                    // Refresh the display
                    repaint();
                    // Delay and give other thread a chance
                    try {
                        Thread.sleep(1000 / UPDATE_RATE);
                    } catch (InterruptedException ex) {}
                }
            }
        };
        gameThread.start();  // Invoke GaemThread.run()
    }


    /**
     * One game time-step.
     * Update the game objects, with proper collision detection and response.
     */
    public void gameUpdate() {
    	for(int i = 0; i<ball_count; i++) {
    		Ball ball = balls.get(i);
    		ball.moveOneStepWithCollisionDetection(box,client);
    		if(ball.readyToSend) {
    			client.sendObject(ball.getBallDTO());
    			balls.remove(i);
    			ball_count--;
    		}
    	}
        
    }

    /** The custom drawing panel for the bouncing ball (inner class). */
    class DrawCanvas extends JPanel {
        /** Custom drawing codes */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);    // Paint background
            // Draw the box and the ball
            box.draw(g);
            for(int i =0; i<ball_count; i++) {
            	balls.get(i).draw(g);
        	}
        }

        /** Called back to get the preferred size of the component. */
        @Override
        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }

	public Color getBallColor() {
		return ball_color;
	}

	public void setBallColor(Color ball_color) {
		this.ball_color = ball_color;
	}
}
