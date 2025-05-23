import java.awt.Color;
import java.awt.Graphics;

public class Player{
    public int x, y;
    public int width, height;

    public boolean up, down;
    
    public Player(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(){
        // Update player actions
        if(up){
            y -= 5;
        }
        if(down){
            y += 5;
        }
        // Colision
        if(y < 0){
            y = 0;
        }
        if(y + height > 600){
            y = 600 - height;
        }
    }
    
    public void render(Graphics g){
        // Render player
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height); // player
    }
}
