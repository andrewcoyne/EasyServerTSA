import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    private static ServerSocket host;
    private static Socket client;

    public server(){
        Boolean init = true;
    }
    public static void main(String[] args){
        try{
            host = new ServerSocket(80);
            client = host.accept();

            while(true){
                server server1 = new server();
                new Thread(server1).start();
            }
        }catch(IOException e){
            System.err.println("Server error: " + e.getMessage());
        }
    }
    //@Override
    public void run(){
        BufferedReader in;
        PrintWriter out;
        BufferedOutputStream dataOut;
        String fileRequested;


    }
}
