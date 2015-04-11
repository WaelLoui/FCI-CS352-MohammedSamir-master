package MessageDP;
public interface MessageOperations {

	public void register (MObserver o);
	public void unRegister (MObserver o);
	public void notifyObserver(int idx , String message);
}
