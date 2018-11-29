package Server;

import SearchAlgo.Searcher;
import SearchAlgo.State;

public class PipeGameSolver<T> implements Solver<T> {

	private Searcher<T> _algorothem;
	
	public PipeGameSolver(Searcher<T> srch) {
		_algorothem = srch;
	}
	public String solve(State<T> problem) {
		return _algorothem.solve(problem);
	}
}
