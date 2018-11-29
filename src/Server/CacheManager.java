package Server;

public interface CacheManager {

	public String load();
	public void save(String problem, String hash);
	public String checkForSolve(Integer hash);
}
