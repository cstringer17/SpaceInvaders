
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cstring
 */
public class Shot {
    
    boolean visible = true;
    int x;
    int y;
    public Shot(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void move(){
       x += 5;
    }
}
