package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class File implements CacheManager {

	java.io.File _f;
	String _solution;
	
	public File() {
		// TODO Auto-generated constructor stub
		
	}

	public String checkForSolve(Integer s) {
		String fileName = s.toString() + ".txt";
		_solution = null;
		_f = new java.io.File(fileName);
		
		if(_f.exists()) {
			try {
				_solution=readFile(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return _solution;
		}
		return null;
	}
	
	private String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");
	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            //stringBuilder.append("/n");
	            stringBuilder.append(",");
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }		
	}

	@Override
	public void save(String solution,String hash) {
		try {
			_f = new java.io.File(hash+".txt");
			_f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(hash+".txt", true));
			writer.append(solution);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public String load() {
		// TODO Auto-generated method stub
		return null;
	}

}
