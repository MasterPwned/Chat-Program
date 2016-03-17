import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
	CopyOnWriteArrayList<Chatter> clientel = new CopyOnWriteArrayList<Chatter>();
	
	public synchronized void sendMessage(String message) {
		for(int i=0; i<clientel.size(); i++){
			if (clientel.get(i).onoff()) {
	            clientel.get(i).sender(message);
			} else {
				clientel.remove(i);
				i = i-1;
			}
		}
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(25563);
		} catch (IOException e) {
			System.err.println("Could Not Listen On Port: 25563");
			e.printStackTrace();
			System.exit(1);
		}
		while (true) {
			try {
				clientel.add(new Chatter(this,serverSocket.accept()));
				clientel.get(clientel.size()-1).start();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
