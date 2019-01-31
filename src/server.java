import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class server implements Runnable{
    private Socket client;

    private server(Socket s){
        client = s;
    }
    static void main(){
        try{
            ServerSocket host = new ServerSocket(app.serverPort);

            while(true){
                server server1 = new server(host.accept());
                (new Thread(server1)).start();
            }
        }catch(IOException e){
            System.err.println("Server error (IOException, line 25): " + e.getMessage());
        }
    }
    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;
        try{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            dataOut = new BufferedOutputStream(client.getOutputStream());
            String input = in.readLine();
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase();
            fileRequested = parse.nextToken().toLowerCase();
            if (!method.equals("GET") && !method.equals("HEAD")) {
                File file = new File(new File(app.folder), "not_supported.html");
                int fileLength = (int) file.length();
                String contentMimeType = "text/html";
                byte[] fileData = readFileData(file, fileLength);

                out.println("HTTP/1.1 501 Not Implemented");
                out.println("Server: EasyServer : 1.0");
                out.println("Date: " + new Date());
                out.println("Content-type: " + contentMimeType);
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();

            } else {
                if (fileRequested.endsWith("/")) {
                    fileRequested += app.getDefaultFile();
                }

                File file = new File(new File(app.folder), fileRequested);
                int fileLength = (int) file.length();
                String content = getContentType(fileRequested);

                if (method.equals("GET")){
                    byte[] fileData = readFileData(file, fileLength);

                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: EasyServer : 1.0");
                    out.println("Date: " + new Date());
                    out.println("Content-type: " + content);
                    out.println("Content-length: " + fileLength);
                    out.println();
                    out.flush();

                    dataOut.write(fileData, 0, fileLength);
                    dataOut.flush();
                }
            }
        } catch (FileNotFoundException fnfe) {
            try {
                fileNotFound(out, dataOut, fileRequested);
            } catch (IOException e) {
                System.err.println("Server error (IOException, line 87): " + e.getMessage());
            }

        } catch (IOException ioe) {
            System.err.println("Server error (IOException, line 91): " + ioe);
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                client.close();
            } catch (Exception e) {
                System.err.println("Server error (could not close stream, line 100): " + e.getMessage());
            }
        }
    }

    private byte[] readFileData(File file, int fileLength) throws IOException{
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    private String getContentType(String fileRequested){
        if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html")){
            return "text/html";
        }else {
            return "text/plain";
        }
    }

    private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException{
        File file = new File(new File(app.folder), "404.html");
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        out.println("HTTP/1.1 404 File Not Found");
        out.println("Server: EasyServer : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println();
        out.flush();

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();
    }
}
