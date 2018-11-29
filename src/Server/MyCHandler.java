package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import SearchAlgo.BFS;
import SearchAlgo.DFS;
import SearchAlgo.HillClimbing;
import SearchAlgo.PipeGameState;
import SearchAlgo.Searcher;

//import com.sun.deploy.util.Waiter;

import java.security.*;


public class MyCHandler implements ClientHandler {
	private Solver<String> sol;
	private CacheManager cm;
	
	public MyCHandler(Solver<String> sol) {
		this.sol = sol;
		cm = new File();
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		
		BufferedWriter outTC = new BufferedWriter(new OutputStreamWriter(outToClient)); // will contain to solution
		BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
		
		
		String test = "";
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			while(!test.equals("done")) 
			{
				test = inFClient.readLine();
				list.add(test);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int row = list.size() - 1;
		int col = list.get(0).length();
		
		char tmp;
		String[] normalMat = new String[row] ;
		int[][] moveMat = new int[row][col];
		
		
		for(int i = 0; i < row; i++) {
			normalMat[i]="";
			for(int j = 0; j < col; j++) {
				tmp = list.get(i).charAt(j);
				if(tmp == '-') {
					moveMat[i][j] = 0;
					normalMat[i] = normalMat[i] + "-"; 
				}
				else if(tmp == '|') {
					moveMat[i][j] = 1;
					normalMat[i] = normalMat[i] + "-";
				}
				else if(tmp == 'L') {
					moveMat[i][j] = 0; 
					normalMat[i] = normalMat[i] + "L";
				}
				else if(tmp == 'J') {
					moveMat[i][j] = 1;
					normalMat[i] = normalMat[i] + "L";
				}
				else if(tmp == '7') {
					moveMat[i][j] = 2;
					normalMat[i] = normalMat[i] + "L";
				}
				else if(tmp == 'F') {
					moveMat[i][j] = 3;
					normalMat[i] = normalMat[i] + "L";
				}
				else if(tmp == 's' || tmp == 'S') {
					moveMat[i][j] = 0; 
					normalMat[i] = normalMat[i] + "s";
				}
				else if(tmp == 'g' || tmp == 'G') {
					moveMat[i][j] = 0; 
					normalMat[i] = normalMat[i] + "g";
				}
				else {
					moveMat[i][j] = 0; 
					normalMat[i] = normalMat[i] + "0";
				}
			}
		}
		
		
		Integer hash = 0;
		for(String s: normalMat) {
			hash += s.hashCode();
		}
			
		
		String _Solution = cm.checkForSolve(hash);
		
		
		if(_Solution != null) {
		
			String FixedSol = "";
			Integer rowSol=0,colSol=0,turns=0,rotation=0;
			String[] parts2 = _Solution.split(",");

			for(int i = 3; i < parts2.length - 4; i += 3) {
				rowSol = Integer.parseInt(parts2[i]);
				colSol = Integer.parseInt(parts2[i+1]); 
				turns = Integer.parseInt(parts2[i+2]);
						
				if(normalMat[rowSol].charAt(colSol) == 'L') {
					rotation = ((turns.intValue() + moveMat[rowSol][colSol]) % 4);
				}
				else if(normalMat[rowSol].charAt(colSol) == '-') {
					rotation = ((turns.intValue() + moveMat[rowSol][colSol]) % 2);
				}
				else {
					rotation = 0;
				}
			
				FixedSol = (rowSol.toString() + "," + colSol.toString() + "," + rotation.toString());
			
				try {
					outTC.write(FixedSol);
					outTC.newLine();
				} catch (IOException e) {
				
					e.printStackTrace();
				}	
			}
		}
		else
		{
			String FixedSol = "";
			Integer rowSol=0,colSol=0,turns=0,rotation=0;
			ArrayList<String> normalized = new ArrayList<String>();
			for(String s : normalMat) {
				normalized.add(s);
			}
					
			PipeGameState<String> state = new PipeGameState<String>(normalized);
			Searcher<String> dfs = new BFS<String>();
			String Solution = dfs.solve(state);

			String[] parts = Solution.split(",");
			
			cm.save(Solution, hash.toString());
			for(int i = 3; i < parts.length - 4; i += 3) {
				rowSol = Integer.parseInt(parts[i]);
				colSol = Integer.parseInt(parts[i+1]); 
				turns = Integer.parseInt(parts[i+2]);
				if(normalMat[rowSol].charAt(colSol) == 'L') {
					rotation = ((turns.intValue() + moveMat[rowSol][colSol]) % 4);
				}
				else if(normalMat[rowSol].charAt(colSol) == '-') {
					rotation = ((turns.intValue() + moveMat[rowSol][colSol]) % 2);
				}
				else {
					rotation = 0;
				}
			
				FixedSol = (rowSol.toString() + "," + colSol.toString() + "," + rotation.toString());
			
				try {
					outTC.write(FixedSol);
					outTC.newLine();
				} catch (IOException e) {
				
					e.printStackTrace();
				}	
			}
		}
		
		try {
			outTC.write("done");
			outTC.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
	}
}
