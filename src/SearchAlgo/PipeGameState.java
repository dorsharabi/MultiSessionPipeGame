package SearchAlgo;

import java.util.ArrayList;
import java.util.Comparator;

public class PipeGameState<T> extends MyState<T> {
	
	protected Comparator<? super State<T>> sc = null;

	public PipeGameState(ArrayList<T> matrix) {
		super(matrix);
		sc = new StateComperator<>();
	}
	
	public PipeGameState(ArrayList<T> matrix, Location newLocation, State<T> father, Integer rotation,
			Integer uristicNum) {
		super(matrix, newLocation, father, rotation, uristicNum);
		sc = new StateComperator<>();
	}

	//TODO If it dosen't running, we need to check that
	public ArrayList<State<T>> getNeighbors(){
		ArrayList<State<T>> nList = new ArrayList<State<T>>();
		nList.addAll(getUpNeighbors());
		nList.addAll(getDownNeighbors());
		nList.addAll(getRightNeighbors());
		nList.addAll(getLeftNeighbors());
		
		return nList;
	}			
	
	private ArrayList<PipeGameState<T>> getUpNeighbors(){
		ArrayList<PipeGameState<T>> nList = new ArrayList<PipeGameState<T>>();
		char c = this._matrix.get(_loc.getRow()).toString().charAt(_loc.getCol());
		if(this._loc.getRow() > 0) {
			if((c == 'L' && this._rotated == 0) || (c == 'L' && this._rotated == 3) || (c == '-' && this._rotated == 1) || c == 's') {
				Location newLocation = new Location(_loc.getRow() -1, _loc.getCol());
				char charUp = this._matrix.get(_loc.getRow() - 1).toString().charAt(_loc.getCol());
				if(!_track.contains(newLocation.getIndex())) {
					if( charUp == 'L') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 1 , getDistance(newLocation)));
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 2 , getDistance(newLocation)));
					}
					if(charUp == '-') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 1 , getDistance(newLocation)));
					}
					if(charUp == 'g') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
					}
				}
			}
		}
		return nList;
	}
	
	private ArrayList<PipeGameState<T>> getDownNeighbors(){
		ArrayList<PipeGameState<T>> nList = new ArrayList<PipeGameState<T>>();
		char c = this._matrix.get(_loc.getRow()).toString().charAt(_loc.getCol());
		if(this._loc.getRow() < _matrix.size() - 1) {
			if((c == 'L' && this._rotated == 1) || (c == 'L' && this._rotated == 2) || (c == '-' && this._rotated == 1) || c == 's') {
				Location newLocation = new Location(_loc.getRow() + 1, _loc.getCol());
				char charDown = this._matrix.get(_loc.getRow() + 1).toString().charAt(_loc.getCol());
				if(!_track.contains(newLocation.getIndex())) {
					if( charDown == 'L') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 3 , getDistance(newLocation)));
					}
					if(charDown == '-') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 1 , getDistance(newLocation)));
					}
					if(charDown == 'g') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
					}
				}
			}
		}
		return nList;
	}
	
	private ArrayList<PipeGameState<T>> getRightNeighbors(){
		ArrayList<PipeGameState<T>> nList = new ArrayList<PipeGameState<T>>();
		char c = this._matrix.get(_loc.getRow()).toString().charAt(_loc.getCol());
		if(this._loc.getCol() < _matrix.get(0).toString().length() - 1) {
			if((c == 'L' && this._rotated == 0) || (c == 'L' && this._rotated == 1) || (c == '-' && this._rotated == 0) || c == 's') {
				Location newLocation = new Location(_loc.getRow(), _loc.getCol() + 1);
				char charRight = this._matrix.get(_loc.getRow()).toString().charAt(_loc.getCol() + 1);
				if(!_track.contains(newLocation.getIndex())) {
					if( charRight == 'L') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 2 , getDistance(newLocation)));
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 3 , getDistance(newLocation)));
					}
					if(charRight == '-') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
					}
					if(charRight == 'g') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
					}
				}
			}
		}
		return nList;
	}
	
	private ArrayList<PipeGameState<T>> getLeftNeighbors(){
		ArrayList<PipeGameState<T>> nList = new ArrayList<PipeGameState<T>>();
		char c = this._matrix.get(_loc.getRow()).toString().charAt(_loc.getCol());
		if(this._loc.getCol() > 0) {
			if((c == 'L' && this._rotated == 2) || (c == 'L' && this._rotated == 3) || (c == '-' && this._rotated == 0) || c == 's') {
				Location newLocation = new Location(_loc.getRow(), _loc.getCol() - 1);
				char charLeft = this._matrix.get(_loc.getRow()).toString().charAt(_loc.getCol() - 1);
				if(!_track.contains(newLocation.getIndex())) {
					if( charLeft == 'L') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 1 , getDistance(newLocation)));
					}
					if(charLeft == '-') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
					}
					if(charLeft == 'g') {
						nList.add(new PipeGameState<T>(_matrix, newLocation, this, 0 , getDistance(newLocation)));
					}
				}
			}
		}
		return nList;
	}

	public Location getGoal() {
		return super.getGoal();
	}

	
	public String getSolution() {
		String sol = new String();
		PipeGameState<T> temp = this;
		while(temp != null) {
			sol += temp.getLocation().getIndex() + "," + temp.getRotation() + ",";
			temp = (PipeGameState<T>) temp.getCameFromState();
		}
		return sol;
	}
	
	public int getDistance(Location l) {
		return super.getDistance(l);
	}

	@Override
	public Comparator<? super State<T>> getComp() {
		return sc;
	}

	public int compareTo(State<T> arg0) {
		if (_uristicNum >= arg0.getUristicNum())
			return 1;
		else
			return -1;
	}	
	
	
}
