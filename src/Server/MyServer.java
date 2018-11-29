package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import SearchAlgo.Location;

public class MyServer implements Server {


	private ServerSocket _serverSocket;
	private int _port;
	private boolean stop;
	
	
	
	public MyServer(int port) {
		this._port = port;
	}
	
	
	private void serverStart(ClientHandler clientHandler) throws IOException{
		_serverSocket = new ServerSocket(_port);
		_serverSocket.setSoTimeout(1000);
		System.out.println("Server Connected! - wating to client..");
		
		while(!stop) {
			try {
				Socket aClient = _serverSocket.accept();
				clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
				
				aClient.close();
			}
			catch(SocketTimeoutException e) {
				//System.err.println("Client didn't connect..");
			}
		}
		
	}

	@Override
	public void start(ClientHandler ch) {
		new Thread(
				() -> {
					try {
						serverStart(ch);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		).start();
	}
	
	@Override
	public void stop() {
		this.stop = true;
	}

}
