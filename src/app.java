import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class app {

    private static JFrame frame = new JFrame("EasyServer");
    private static Boolean serverIsOn = false;
    private static String folder = ".";

    public static void main(String[] args){
        buildFrame();
    }

    private static void buildFrame(){
        frame.setPreferredSize(new Dimension(300,350));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new JLabel("<html>Welcome to EasyServer! Simply find the folder containing your website's files by clicking the button below, then click the 'Start Server' button at the bottom. To shut down the server, close this window.<br/><br/>NOTE: Make sure you have files in your folder named 'index.html' (your website's home page), '404.html' (for when someone attempts to find a file on your website that doesn't exist), and 'not_supported.html' (for when someone tries to interact with the website in a way that EasyServer doesn't support). <br/><html>"), BorderLayout.BEFORE_FIRST_LINE);


        JButton findButton = new JButton("Select Server Files");
        findButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(findButton) == JFileChooser.APPROVE_OPTION) {
                    setFolder(chooser.getSelectedFile().toString());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        frame.getContentPane().add(findButton, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Server");
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!serverIsOn) {
                    server.main(folder);
                    serverIsOn = !serverIsOn;
                }else{
                    server.stop();
                    serverIsOn = !serverIsOn;
                }
            }
        });
        frame.getContentPane().add(startButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    static String getDefaultFile(){
        return "index.html";
    }

    private static void setFolder(String folder1){
        folder = folder1;
    }
}
