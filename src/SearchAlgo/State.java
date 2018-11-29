package SearchAlgo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public interface State<T> {
	
	
	public State<T> getCameFromState();
	public Location getLocation();
	public Integer getRotation();
	public ArrayList<State<T>> getNeighbors();
	public Integer getUristicNum();
	public Boolean isGoal();
	public int visited();
	//public Location findStart();
	public HashSet<String> getTrack();
	public void setVisited(int color);
	public String getSolution();
	public Location getGoal();
	public int getDistance(Location l);
	public Location getGoalLoc();
	Comparator<? super State<T>> getComp();

	class StateComperator<T> implements Comparator<State<T>>{

		@Override
		public int compare(State<T> arg0, State<T> arg1) {
			if(arg1.getUristicNum() >= arg0.getUristicNum())
				return 1;
			else
				return -1;
		}
		
	}
	
}
