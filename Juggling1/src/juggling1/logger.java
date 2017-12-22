package juggling1;

import java.util.logging.Logger;

public interface logger {
	public void setBalls(BallsController balls);
	public void stopCatching();
	public boolean isAnyHandAvaliable();
	public String lock();
	public boolean isCatchAllowed();
	public void unlock(String lastHand);
	public Logger getLogger();
	}
