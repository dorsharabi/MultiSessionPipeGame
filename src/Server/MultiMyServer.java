package Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import SearchAlgo.Location;
import Server.CompareBoards;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MultiMyServer implements Server {
	private ServerSocket _serverSocket;
	private int _port;
	private boolean stop = false;
	ThreadPoolExecutor _threadPE;
	private int _counter = 0;
	
	public MultiMyServer(int port, int numOfThread) {
		this._port = port;
		this._threadPE = new ThreadPoolExecutor(2, numOfThread, 6, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
	}
	
	private void MultiServerStart(ClientHandler clientHandler) throws IOException{
		_serverSocket = new ServerSocket(_port);
		_serverSocket.setSoTimeout(1000);
		System.out.println("Multi Server Connected! - wating to client..");
		
		while(!stop) {
			try {
				Socket aClient = _serverSocket.accept();

				if(aClient != null) {
					InputStream in = aClient.getInputStream();
					this._threadPE.execute(new CompareBoards(in.available()) {
						@Override
						public void run() {
							try {
								clientHandler.handleClient(in, aClient.getOutputStream());
								aClient.close();
							}
							catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					
				}
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
						MultiServerStart(ch);
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
