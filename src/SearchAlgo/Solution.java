package SearchAlgo;

import java.util.ArrayList;

public class Solution {

	private ArrayList<String> _solution;
	public Solution() {
		_solution = new ArrayList<String>();
	}

	public void addMove(String s) {
		_solution.add(s);
	}
	
	public String getSolution() {
		String temp = "";
		for(String addLine: _solution) {
			temp += addLine + ",";
		}
		return temp;
	}
	
}
