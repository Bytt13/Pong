import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {

    public int x, y;
    public int width, height;
    public double xVel, yVel;
    public double vel = 5; 

    public Ball(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        xVel = new Random().nextGaussian();
        yVel = new Random().nextGaussian();
    }
    
    public void update(){
        x += xVel * vel;
        y += yVel * vel;
        // Colision
        if(x < 0){
            x = 0;
            // Reset ball position
            x = Pong.ballStartX;
            y = Pong.ballStartY;

            Pong.scoreEnemy++;
            
        }
        if(x + width > 800){
            x = 800 - width;   
            // Reset ball position
            x = Pong.ballStartX;
            y = Pong.ballStartY;

            Pong.score++;
        }
        if(y < 0){
            y = 0;
            yVel = -yVel;
        }
        if(y + height > 600){
            y = 600 - height;
            yVel = -yVel;
        }

        Rectangle bounds = new Rectangle(x, y, width, height);
        
        Rectangle playerBounds = new Rectangle(Pong.player.x, Pong.player.y, Pong.player.width, Pong.player.height);
        Rectangle enemyBounds = new Rectangle(Pong.enemy.x, Pong.enemy.y, Pong.enemy.width, Pong.enemy.height);

        if(bounds.intersects(playerBounds)){
            xVel *= -1;
        }
        else if(bounds.intersects(enemyBounds)){
            xVel *= -1;
        }
    }

    public void render(Graphics g){
        // Render Enemy
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height); 
    }
}
