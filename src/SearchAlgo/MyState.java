package SearchAlgo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public abstract class MyState<T> implements State<T>, Comparator<State<T>>{
	
	protected Location _loc;
	protected ArrayList<T> _matrix;
	protected Integer _rotated, _uristicNum;
	protected State<T> _father;
	protected int rows,cols;
	protected Location _Goal;
	protected HashSet<String> _track; 
	protected int _visited;
	
	public MyState(ArrayList<T> matrix) {
		for(int i =0 ; i < matrix.size(); i++)
		{
			for(int j =0 ; j < matrix.get(0).toString().length() ; j++)
			{
				if((matrix.get(i).toString().charAt(j) == 's')) {
					_matrix = new ArrayList<T>(matrix);
					_loc = new Location(i,j);
					_father = null;
					_rotated = 0;
					_track = new HashSet<String>();
					_track.add(_loc.getIndex());
					_uristicNum = getDistance(_loc);
					_visited = 0;
					_Goal = new Location(getGoal());
				}
			}
		}
	}
	
	public MyState(ArrayList<T> matrix, Location newLocation, State<T> father, Integer rotation, Integer uristicNum) {
		_matrix = new ArrayList<T>(matrix);
		_loc = new Location(newLocation);
		_father = father;
		_track = new HashSet<String>(father.getTrack());
		_track.add(_loc.getIndex());
		_rotated = rotation;
		_uristicNum = uristicNum;
		_visited = 0;
		_Goal = _father.getGoalLoc();
	}


	@Override
	public State<T> getCameFromState() {
		return this._father;
	}



	@Override
	public Location getLocation() {
		return this._loc;
	}



	@Override
	public Integer getRotation() {
		return this._rotated;
	}



	@Override
	public abstract ArrayList<State<T>> getNeighbors();



	@Override
	public Integer getUristicNum() {
		return this._uristicNum;
	}



	@Override
	public Boolean isGoal() {
		if(_matrix.get(_loc.getRow()).toString().charAt(_loc.getCol()) == 'g') {			
			return true;
		}
		else
			return false;
	}


	@Override
	public int visited() {
		return _visited;
	}

	public void setVisited(int color) {
		_visited = color;
	}
	
	@Override
	public HashSet<String> getTrack() {
		return this._track;
	}
	
	public Location getGoalLoc() {
		return this._Goal;
	}
	
	public abstract String getSolution();

	
	
	public Location getGoal() {
		for(int i =0 ; i < _matrix.size(); i++)
		{
			for(int j =0 ; j < _matrix.get(0).toString().length() ; j++)
			{
				if((_matrix.get(i).toString().charAt(j) == 'g')) {
					Location l = new Location(i, j);
					return l;
				}
			}
		}
		return null;
		
	}
	
	public int getDistance(Location l) {
		int i = (int) Math.abs((getGoal().getRow() - l.getRow())+(getGoal().getCol() - l.getCol()));
		//System.out.println(i);
		return (int) Math.abs((getGoal().getRow() - l.getRow())+(getGoal().getCol() - l.getCol()));
	}
	
	public int compare(State<T> arg0, State<T> arg1) {
		return arg0.getUristicNum() - arg1.getUristicNum();
	}

}
