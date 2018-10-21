
import com.bulenkov.darcula.DarculaLaf;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class Board extends JPanel implements Runnable, MouseListener {

    boolean ingame = true;
    private Dimension d;
    int BOARD_WIDTH = 500;
    int BOARD_HEIGHT = 500;
    int x = 0;
    BufferedImage img;

    //Import Images
    //private BufferedImage imgEnemy = null;
    //imgEnemy = new ImageIO.read(new File("src/main/resources/32_Enemy.png"));
    BufferedImage imgE = null;
    BufferedImage imgP = null;

    boolean shotCooldown = true;
    int coolDownCounter = 0;

    {
    }

    private Thread animator;

    Player p;
    ArrayList<Shot> shots = new ArrayList<Shot>();
    Alien[] a = new Alien[10];

    public Board() {
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);

        try {
            imgP = ImageIO.read(new File("src/main/resources/32_Player.png"));
            imgE = ImageIO.read(new File("src/main/resources/32_Enemy.png"));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        // x,y,s
        p = new Player(BOARD_WIDTH / 2, BOARD_HEIGHT - 60, 5);

        int ax = 10;
        int ay = 10;

        // staring position of aliens
        for (int i = 0; i < a.length; i++) {
            a[i] = new Alien(ax, ay, 10);
            ax += 40;
            if (i == 4) {
                ax = 10;
                ay += 40;
            }

        }

        setBackground(Color.black);

        /*
         * try { img = ImageIO.read(this.getClass().getResource("mount.jpg")); } catch
         * (IOException e) { System.out.println("Image could not be read"); //
         * System.exit(1); }
         */
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }

        setDoubleBuffered(true);
    }

    public void paint(Graphics g) {
        super.paint(g);

        //cooldown
        if(coolDownCounter == 20){
            shotCooldown = true;
            coolDownCounter = 0;
        }else{
            coolDownCounter++;
            
        }
        
        
        
        BasicLookAndFeel darcula = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(darcula);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StartFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
//g.fillOval(x,y,r,r);

        // represent player
        g.drawImage(imgP, p.x, p.y, this);

        //test shots
        for (int i = 0; i < shots.size(); i++) {
            g.setColor(Color.RED);
            g.fillRect(shots.get(i).x, shots.get(i).y, 10, 10);
            if (shots.get(i).y < 0) {
                shots.remove(i);
            }
            shots.get(i).y -= 5;
        }

        if (p.moveRight == true) {
            p.x += p.speed;
        }
        if (p.moveLeft == true) {
            p.x -= p.speed;
        }
        moveAliens();
        checkAliens();

        // display aliens
        for (int i = 0; i < a.length; i++) {
            //  g.fillRect(a[i].x, a[i].y, 30, 30);
            g.drawImage(imgE, a[i].x, a[i].y, this);

        }

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.black);
        g.setFont(small);

        if (ingame) {

            // g.drawImage(img,0,0,200,200 ,null);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void moveAliens() {
        for (int i = 0; i < a.length; i++) {
            if (a[i].moveLeft == true) {
                a[i].x -= 5;
            }
            if (a[i].moveRight == true) {
                a[i].x += 5;
            }

        }
        for (int i = 0; i < a.length; i++) {
            if (a[i].x > BOARD_WIDTH - 30) {
                for (int j = 0; j < a.length; j++) {
                    a[j].moveLeft = true;
                    a[j].moveRight = false;
                    a[j].y += 5;
                }

            }
            if (a[i].x < 0) {
                for (int j = 0; j < a.length; j++) {
                    a[j].moveRight = true;
                    a[j].moveLeft = false;
                    a[j].y += 5;
                }
                // a[i].y += a[i].speed;
            }
        }
    }

    public void checkAliens() {
        for (int i = 0; i < a.length; i++) {
            int ylocation = a[i].y;
            if (ylocation == 420) {
                //end game
                try {
                    EndFrame test = new EndFrame("", "", "");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            p.moveRight = false;
            p.moveLeft = false;
        }

        public void keyPressed(KeyEvent e) {
//System.out.println( e.getKeyCode());
            // message = "Key Pressed: " + e.getKeyCode();
            int key = e.getKeyCode();
            if (key == 39) {
                p.moveRight = true;
            }
            if (key == 37) {
                p.moveLeft = true;
            }
            if (key == 32) {
                if(shotCooldown == true){
                System.out.println("Space");
                shotCooldown = false;
                shots.add(new Shot(p.x, p.y));
            }
        }
        }      

    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        int animationDelay = 20;
        long time = System.currentTimeMillis();
        while (true) {// infinite loop
            // spriteManager.update();
            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                System.out.println(e);
            } // end catch
        } // end while loop

    }// end of run

}// end of class
