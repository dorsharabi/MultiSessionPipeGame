package test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import SearchAlgo.BFS;
import SearchAlgo.DFS;
import SearchAlgo.HillClimbing;
import SearchAlgo.Location;
import SearchAlgo.MyState;
import SearchAlgo.Searcher;
import SearchAlgo.State;
import Server.CacheManager;
import Server.ClientHandler;
import Server.File;
import Server.MyCHandler;
import Server.MyServer;
import Server.PipeGameSolver;
import Server.Server;
import Server.Solver;


public class TestSetter {
	
	public static void setClasses(DesignTest dt){
		
		// set the server's Interface, e.g., "Server.class"
		// don't forget to import the correct package e.g., "import server.Server"
		dt.setServerInteface(Server.class);
		// now fill in the other types according to their names
		// server's implementation
		dt.setServerClass(MyServer.class);
		// client handler interface
		dt.setClientHandlerInterface(ClientHandler.class);
		// client handler class
		dt.setClientHandlerClass(MyCHandler.class);
		// cache manager interface
		dt.setCacheManagerInterface(CacheManager.class);
		// cache manager class
		dt.setCacheManagerClass(File.class);
		// solver interface
		dt.setSolverInterface(Solver.class);
		// solver class
		dt.setSolverClass(PipeGameSolver.class);
		// searchable interface
		dt.setSearchableInterface(State.class);
		// searcher interface
		dt.setSearcherInterface(Searcher.class);
		// your searchable pipe game class
		dt.setPipeGameClass(MyState.class);
		// your Best First Search implementation
		dt.setBestFSClass(BFS.class);
	}
	
	// run your server here
	static Server s;
	public static void runServer(int port){
		s=new MyServer(port);
		s.start(new MyCHandler(null));
	}
	// stop your server here
	public static void stopServer(){
		s.stop();
	}
	
	/* ------------- Best First Search Test --------------
	 * You are given a Maze
	 * Create a new Searchable from the Maze
	 * Solve the Maze
	 * and return a list of moves from {UP,DOWN,RIGHT,LEFT}
	 *  
	 */
	
	public static List<String> solveMaze(Maze m){
		 class MazeState implements State<String>{
			protected Maze _maze;
			protected Location _loc;
			protected MazeState _father;
			protected int _visited;
			protected Location _goal;
			protected HashSet<String> _track;
			
			public MazeState(Maze _maze, Location _loc, MazeState father, int _visited, Location _goal) {
				super();
				this._maze = _maze;
				this._loc = _loc;
				this._father = father;
				this._visited = _visited;
				this._goal = _goal;
				_track=new HashSet<String>();
				_track.addAll(father._track);
				_track.add(_loc.getIndex());
				
			}

			public MazeState(Maze _maze) {
				super();
				this._maze = _maze;
				_loc= new Location(_maze.getEntrance().row,_maze.getEntrance().col);
				_father=null;
				_visited=0;
				_goal=new Location(_maze.getExit().row,_maze.getExit().col);
				_track=new HashSet<String>();
				_track.add(_loc.getIndex());			}

			@Override
			public State<String> getCameFromState() {
				return _father;
			}

			@Override
			public Location getLocation() {
				// TODO Auto-generated method stub
				return _loc;
			}

			@Override
			public Integer getRotation() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public ArrayList<State<String>> getNeighbors() {
				ArrayList<State<String>> retLis=new ArrayList<State<String>>();
				ArrayList<Grid> l=new ArrayList<Grid>(_maze.getPossibleMoves(new Grid(_loc.getRow(),_loc.getCol())));
				State<String> temp;
				Grid tgrid;
				while(!l.isEmpty()) {
					tgrid=l.remove(0);
					if(!_track.contains(l.toString())) {
					temp=new MazeState(_maze,new Location(tgrid.row,tgrid.col),this,0,_goal);
					retLis.add(temp);
					}
				}
				return retLis;
			}

			@Override
			public Integer getUristicNum() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Boolean isGoal() {
				int myRow=this.getLocation().getRow();
				int myCol=this.getLocation().getCol();
				int fRow=_goal.getRow();
				int fCol=_goal.getCol();
				int dRow=fRow-myRow;
				int dCol=fCol-myCol;
				if (dRow==1&&dCol==0) {
					return true;
				} 
				if (dRow==-1&&dCol==0) {
					return true;
				} 
				if (dRow==0&&dCol==-1) {
					return true;
				} 
				if (dRow==0&&dCol==1) {
					
					return true;
				} 
				if (dRow==1&&dCol==1) {
					
					return true;
				} 
				if (dRow==-1&&dCol==-1) {
					return true;
					} 
				if (dRow==1&&dCol==-1) {
					return true;
					} 
				if (dRow==-1&&dCol==1) {
					return true;
					} 
				
				return false;
			}

			@Override
			public int visited() {
				// TODO Auto-generated method stub
				return _visited;
			}

			@Override
			public HashSet getTrack() {
				// TODO Auto-generated method stub
				return _track;
			}

			@Override
			public void setVisited(int colour) {
				// TODO Auto-generated method stub
				_visited=colour;
			}

			@Override
			public String getSolution() {
				String sol=new String();
				String check= new String();
				sol="";
				int myRow,myCol,fRow,fCol,dRow,dCol;
				MazeState temp=this;
				while(temp._father!=null) {
					myRow=temp.getLocation().getRow();
					myCol=temp.getLocation().getCol();
					fRow=temp._father.getLocation().getRow();
					fCol=temp._father.getLocation().getCol();
					dRow=myRow-fRow;
					dCol=myCol-fCol;
					if (dRow==1&&dCol==0) {
						sol += "DOWN"+",";
					} 
					if (dRow==-1&&dCol==0) {
						sol += "UP"+",";
					} 
					if (dRow==0&&dCol==-1) {
						sol += "LEFT"+",";
					} 
					if (dRow==0&&dCol==1) {
						
						sol += "RIGHT"+",";
					} 
					if (dRow==1&&dCol==1) {
						
						sol += "DOWN"+","+"RIGHT"+",";
					} 
					if (dRow==-1&&dCol==-1) {
						sol += "UP"+","+"LEFT"+",";
					} 
					if (dRow==1&&dCol==-1) {
						sol += "DOWN"+","+"LEFT"+",";
					} 
					if (dRow==-1&&dCol==1) {
						sol += "UP"+","+"RIGHT"+",";
					} 
					
					
				    
					temp = temp._father;
				}
				
				myRow=this.getLocation().getRow();
				myCol=this.getLocation().getCol();
				fRow=_goal.getRow();
				fCol=_goal.getCol();
				dRow=fRow-myRow;
				dCol=fCol-myCol;
				if (dRow==1&&dCol==0) {
					sol += "DOWN"+",";
				} 
				if (dRow==-1&&dCol==0) {
					sol += "UP"+",";
				} 
				if (dRow==0&&dCol==-1) {
					sol += "LEFT"+",";
				} 
				if (dRow==0&&dCol==1) {
					
					sol += "RIGHT"+",";
				} 
				if (dRow==1&&dCol==1) {
					
					sol += "DOWN"+","+"RIGHT"+",";
				} 
				if (dRow==-1&&dCol==-1) {
					sol += "UP"+","+"LEFT"+",";
				} 
				if (dRow==1&&dCol==-1) {
					sol += "DOWN"+","+"LEFT"+",";
				} 
				if (dRow==-1&&dCol==1) {
					sol += "UP"+","+"RIGHT"+",";
				} 
				
			String[] sp=sol.split(",");
			String fixSol=new String();
			fixSol="";
			for(int i=sp.length-1;i>=0;i--) {
				fixSol+=sp[i]+",";
				
			}
			
		//.out.println(fixSol);
			return fixSol;
			}

			@Override
			public Location getGoal() {
				// TODO Auto-generated method stub
				return _goal;
			}

			public Location getGoalLocation() {
				// TODO Auto-generated method stub
				return _loc;
			}

			@Override
			public Comparator getComp() {
				// TODO Auto-generated method stub
				return null;
			}


			@Override
			public int getDistance(Location l) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Location getGoalLoc() {
				// TODO Auto-generated method stub
				return null;
			}
			
			
		}
	    MazeState mazeState=new MazeState(m);
	    Searcher<String> s=new BFS<String>();
	    String solution=s.solve(mazeState);
	    ArrayList<String> fx=new ArrayList<String>();
	    String[] sx=solution.split(",");
	    for(int p=0;p<sx.length;p++) {
	    	fx.add(sx[p]);
	    }
	    
		return fx;
	}

}
