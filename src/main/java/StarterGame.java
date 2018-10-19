  import javax.swing.JFrame;

public class StarterGame extends JFrame {

    public StarterGame()
    {
        add(new Board());
        setTitle("Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void mainStart() {
        new StarterGame();
    }
}
  