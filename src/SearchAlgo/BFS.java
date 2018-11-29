package SearchAlgo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS<T> implements Searcher<T> {

	private LinkedList<State<T>> _open;
	private HashSet<State<T>> _close;
	
	public BFS() {
		
	}

	@Override
	public String solve(State<T> problem) {
		_open = new LinkedList<State<T>>();
		_close = new HashSet<State<T>>();
		
		_open.add(problem);
		State<T> temp;
		
		while(!_open.isEmpty()) {
			temp = _open.remove();
			_close.add(temp);
			if(temp.isGoal()) {
				return temp.getSolution();
			}
			ArrayList<State<T>> tempChild = temp.getNeighbors();
			while(!tempChild.isEmpty()) {
				temp = tempChild.remove(0);
				if(!_close.contains(temp)) {
					_open.add(temp);
				}
			}
		}
		return null;
	}
}
