package SearchAlgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DFS<T> implements Searcher<T> {

	private Stack<State<T>> _open;
	private HashSet<State<T>> _close;
	
	public DFS() {
	
	}

	@Override
	public String solve(State<T> problem) {
		_open = new Stack<State<T>>();
		_close = new HashSet<State<T>>();
		
		_open.push(problem);
		State<T> temp;
		
		while(!_open.isEmpty()) {
			temp = _open.pop();
			_close.add(temp);
			if(temp.isGoal()) {
				return temp.getSolution();
			}
			ArrayList<State<T>> tempChild = temp.getNeighbors();
			while(!tempChild.isEmpty()) {
				temp = tempChild.remove(0);
				if(!_close.contains(temp)) {
					_open.push(temp);
				}
			}
		}
		return null;
	}


}
