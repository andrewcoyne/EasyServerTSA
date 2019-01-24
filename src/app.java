import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class app {

    private static JFrame frame = new JFrame("EasyServer");
    private static Boolean serverIsOn = false;
    private static String folder = ".";
    private static int serverPort = 8080;

    public static void main(String[] args){
        buildFrame();
    }

    private static void buildFrame(){
        frame.setPreferredSize(new Dimension(300,350));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e){
            System.err.println("Line 22 Error: " + e.getMessage());
        }

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Options");
        menuBar.add(menu);

        menuItem = new JMenuItem("Force Server Shutdown");
        menuItem.getAccessibleContext().setAccessibleDescription(
                "If the application window won't close, use this.");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Set Port");
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Use this to change the port EasyServer uses.");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame setPort = new JFrame("Set Port");
                setPort.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setPort.setPreferredSize(new Dimension(300,120));
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }catch (Exception ex){
                    System.err.println("Line 22 Error: " + ex.getMessage());
                }
                JTextField text = new JTextField(1);
                setPort.getContentPane().add(text, BorderLayout.BEFORE_FIRST_LINE);
                JLabel status = new JLabel("<html>Enter a port to see if it can be used.</html>");
                setPort.getContentPane().add(status, BorderLayout.AFTER_LAST_LINE);
                JButton button = new JButton("Set Port");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int port = Integer.parseInt(text.getText());
                        if(portIsAvailable(port)){
                            serverPort = port;
                            status.setText("<html>Port successfully changed. Remember that this does not guarantee that EasyServer will work on this port.</html>");
                        }else{
                            status.setText("Port invalid. Try again.");
                        }
                    }
                });
                setPort.getContentPane().add(button, BorderLayout.CENTER);
                setPort.pack();
                setPort.setVisible(true);
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        JLabel mainText = new JLabel("<html>Welcome to EasyServer! Simply find the folder containing your website's" +
                                          " files by clicking the button below, then click the 'Start Server' button at " +
                                          "the bottom. To shut down the server, close this window.<br/><br/>NOTE: Make " +
                                          "sure you have files in your folder named 'index.html' (your website's home " +
                                          "page), '404.html' (for when someone attempts to find a file on your website" +
                                          " that doesn't exist), and 'not_supported.html' (for when someone tries to " +
                                          "interact with the website in a way that EasyServer doesn't support). <br/><html>");
        Font textfont = new Font("Arial", Font.PLAIN, 14);
        mainText.setFont(textfont);
        frame.getContentPane().add(mainText, BorderLayout.BEFORE_FIRST_LINE);


        JButton findButton = new JButton("Select Server Files");
        findButton.setFont(textfont);
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
        startButton.setFont(textfont);
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!serverIsOn) {
                    serverStarter.main(folder, serverPort);
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

    private static boolean portIsAvailable(int port) {
        Socket s = null;
        try {
            s = new Socket("localhost", port);
            return false;
        } catch (IOException e) {
            return true;
        } finally {
            if( s != null){
                try {
                    s.close();
                } catch (IOException e) {
                    throw new RuntimeException("You should handle this error." , e);
                }
            }
        }
    }

    private static void setFolder(String folder1){
        folder = folder1;
    }
}
