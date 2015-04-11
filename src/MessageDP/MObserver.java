package MessageDP;
import java.util.ArrayList;


public class MObserver implements MessageObserver {
	
	ArrayList<String> messages;
	public static int observerID = 0;
	public int thisObserverID;
	
	public MObserver() {
		// TODO Auto-generated constructor stub
		messages = new ArrayList<String>();
		thisObserverID = observerID++;
	}

	@Override
	public void update(String Message) {
		// TODO Auto-generated method stub
		messages.add(Message);
	}

}
