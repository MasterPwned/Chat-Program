import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Chatter extends Thread {
	Main cs;
	Socket o;
	boolean running = true;
	
	public Chatter(Main chatterServer, Socket outlit) {
	   cs = chatterServer;
	   o = outlit;
	}
	
	
	public boolean onoff() {
		return running;
	}
	public void run() {
		BufferedReader in = getBufferedReader(o);
		while(running) {
			try {
				cs.sendMessage(in.readLine());
			} catch (IOException e) {
				running = false;
			}
		}		
	}
	
	public void sender(String message) {
			PrintWriter out = getPrintWriter(o);
			out.println(message);
	}

	public static BufferedReader getBufferedReader(Socket clientSocket) {
		try {
			return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;			
		}
	}
	
	public static PrintWriter getPrintWriter(Socket clientSocket) {
		try {
		    return new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
}
