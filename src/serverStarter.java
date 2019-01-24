public class serverStarter implements Runnable{
    private static String folder;
    private static int port;
    public void run(){
        server.main(folder, port);
    }
    public static void main(String f, int p){
        folder = f;
        port = p;
        (new Thread(new serverStarter())).start();
    }
}
