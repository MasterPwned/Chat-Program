import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Chat {

	public static void main(String[] args) {
		System.out.println("Username:");

		Scanner sc = new Scanner(System.in);
		String username = sc.next();
		
		System.out.println("Hello, "+username);
		System.out.println("IP Address:");
		
		String address = sc.next();
		System.out.println("Connecting To: "+address);
		
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(address, 25563);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		PrintWriter out = getPrintWriter(clientSocket);
		BufferedReader in = getBufferedReader(clientSocket);
		System.out.println("Connection Successful" );

		out.println(username+" Connected!");
		
		new Thread() {
			public void run() { 
				boolean running = true;
				while(running) {
					try {
						System.out.println(in.readLine());
					} catch (IOException e) {
						running = false;
					}
				}
			}
		}.start();
		
		boolean inuse = true;
		sc.nextLine();
		while(inuse) {
			String message = sc.nextLine();
			out.println(username+": "+message);
			if(message.equals("exit")) {
				inuse = false;
			}
		}
		
		sc.close();
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static BufferedReader getBufferedReader(Socket clientSocket) {
		try {
			return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;			
		}
	}
}
