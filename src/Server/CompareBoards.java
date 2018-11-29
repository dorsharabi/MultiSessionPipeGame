package Server;

public abstract class CompareBoards implements Comparable<CompareBoards>,Runnable {

	public int _bSize;
	
	public CompareBoards(int bSize) {
		this._bSize = bSize;
	}


	@Override
	public int compareTo(CompareBoards arg0) {
		
		return this._bSize-arg0._bSize;
	}
}
