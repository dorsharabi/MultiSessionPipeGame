package SearchAlgo;

public class Location {

	Integer _i;
	Integer _j;
	String _toString;
	Integer _start, _end;
	
	public Location(int i, int j) {
		this._i = i;
		this._j = j;
	}
	
	public Location(Location loc) {
		this._i = loc.getRow();
		this._j = loc.getCol();
	}
	
	public int getRow() {
		return this._i;
	}
	
	public int getCol() {
		return this._j;
	}
	
	public String getIndex() {
		_toString = _i.toString() + "," + _j.toString();
		return _toString;
	}
	
	
}
