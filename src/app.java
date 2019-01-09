import javax.swing.*;
import java.awt.*;

public class app {

    private static JFrame frame = new JFrame("EasyServer");

    public static void main(String[] args){
        frame.getContentPane().add(new JLabel("Welcome!"), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
