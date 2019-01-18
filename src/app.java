import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app {

    private static JFrame frame = new JFrame("EasyServer");

    public static void main(String[] args){
        buildFrame();
    }

    private static void buildFrame(){
        frame.setPreferredSize(new Dimension(600,800));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JButton startButton = new JButton("Start/Stop Server");
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                server.main();
            }
        });
        frame.getContentPane().add(startButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public static String getDefaultFile(){
        return "index.html";
    }
}
