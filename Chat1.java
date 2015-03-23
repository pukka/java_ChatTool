import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.*;

public class Chat1 {

  public static final int ECHO_PORT = 8080;

  public static void main(String args[]) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    try {
      serverSocket = new ServerSocket(ECHO_PORT);

      System.out.println("--- Start Server ---");
      System.out.println("Listening on " + serverSocket.getLocalPort());
      
      socket = serverSocket.accept();
      System.out.println("Accept returned! ：" + socket.getRemoteSocketAddress());
      
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

      String input;
      new Receive(socket).start();

      while ( (input = keyIn.readLine()).length() > 0 ) {
        out.println(input);
      } 
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Receive extends Thread {
  private Socket socket;

  public Receive(Socket socket) {
    this.socket = socket;
    System.out.println("接続完了。");
  }

  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String line;

      while((line = in.readLine()) != null){
        System.out.println("Receive : " + line);
      }
    } catch (IOException e){
      System.out.println("CLOSE");
    }
  }
}
