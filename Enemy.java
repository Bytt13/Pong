import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

    public int x, y;
    public int width, height;

    public Enemy(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update(){
        // Update enemy actions
        y += (Pong.ball.y - y) - 1;
        // Colision
        if(y < 0){
            y = 0;
        }   
        if(y + height > 600){
            y = 600 - height;
        }
        // AI logic to follow the ball

    }

    public void render(Graphics g){
        // Render Enemy
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height); // enemy
    }
}
