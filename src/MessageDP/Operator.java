package MessageDP;
import java.util.ArrayList;

public class Operator implements MessageOperations {

	public ArrayList<MObserver> messageList;
	
	public Operator() {
		// TODO Auto-generated constructor stub
		messageList = new ArrayList<MObserver>();
	}

	@Override
	public void register(MObserver o) {
		// TODO Auto-generated method stub
		messageList.add(o);
	}

	@Override
	public void unRegister(MObserver o) {
		// TODO Auto-generated method stub
		int messageObserverIndex = messageList.indexOf(o);
		System.out.println("Message History Deleted From Memory ID: " + messageObserverIndex + 1);
		messageList.remove(messageObserverIndex);
	}

	@Override
	public void notifyObserver(int idx , String message) {
		// TODO Auto-generated method stub
		MObserver o = null;
		for (int i=0;i<messageList.size();i++)
		{
			if (messageList.get(i).thisObserverID == idx)
				o = messageList.get(i);
		}
		
		if (o != null)
			o.update(message);
		
		else
			System.out.println("No Observer With This ID");
	}
	
}
