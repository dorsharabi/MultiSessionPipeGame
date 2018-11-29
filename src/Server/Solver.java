package Server;

import SearchAlgo.State;

public interface Solver<T> {
	public String solve(State<T> searchable);
	
}
