import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Color;
//Imports

public class Pong extends Canvas implements Runnable, KeyListener{
    // Game variables

    public final long serialVersionUID = 1L;
    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    public final int SCALE = 1;
    public static final int ballStartX = 400;
    public static final int ballStartY = 200;

    public static int score;
    public static int scoreEnemy;

    private Thread thread;
    private boolean isRunning;

    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public static Player player;
    public static Enemy enemy;
    public static Ball ball;

    public Pong(){
        // Set up the constructor
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        player = new Player(40, 200 , 10, 200);
        enemy = new Enemy(760, 200, 10, 200);
        ball = new Ball(ballStartX, ballStartY, 10,10);
        this.addKeyListener(this);
    }

    public void initFrame(){
        // Set up the Game window
        JFrame frame = new JFrame("Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        // Main method
        Pong pong = new Pong();
        pong.start();
    }

    public synchronized void start(){
        // Start the game
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop(){
        // Stop the game
        try{
            thread.join();
            isRunning = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        // Update game state
        player.update();
        enemy.update();
        ball.update();
    }

    public void render(){
        // Render game state
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = layer.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
        player.render(g);
        enemy.render(g);
        ball.render(g);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Player: " + score, (WIDTH/2) - 200, 50);
        g.drawString("Enemy: " + scoreEnemy, (WIDTH/2) + 50, 50);


        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);

        bs.show();
    }

    @Override
    public void run(){
        // Game loop 60 FPS
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }

    @Override
    public void keyTyped(KeyEvent e){
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e){
        //Movements
        if(e.getKeyCode() == KeyEvent.VK_UP){
            player.up = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        // Movements
        if(e.getKeyCode() == KeyEvent.VK_UP){
            player.up = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = false;
        }
    }
}