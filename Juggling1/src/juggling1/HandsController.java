package juggling1;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class HandsController {
	private static final Logger logger = Logger.getLogger("JugglingBall");
    private juggling1.logger logic;
	private ReentrantLock left = new ReentrantLock();
	private ReentrantLock right = new ReentrantLock();
	private boolean catchAllowed = true;
	private BallsController balls;
	
	public void setBalls(BallsController balls){
		this.balls = balls;
	}
	
	public void stopCatching() { //called when a ball hit the ground to prevent catching balls anymore and finileze the program
		catchAllowed = false;
		logger.info("The hands will no more catch balls because a ball hit the ground, " + balls.getNBalls() + " balls juggled");
		System.exit(0);
	}

	public boolean isAnyHandAvaliable() { //Check if any(left or right) ReentrantLock objects are unlocked to catch a ball 
		if ((!left.isLocked()) || (!right.isLocked())) {
			return true;
		}
		return false;
	}

	public String lock() { //Try to catch a ball with an avaliable hand, if both avaliable random
		String hand = null;
		if (catchAllowed) {
			//Both hands avaliables
			if ((!left.isLocked()) && (!right.isLocked())) {
				double choice = Math.random();
				if (choice < .5) {
					if (!left.isLocked()) {
						left.lock(); 
						hand = "left";
					} else  {
						hand = "retry";
					}
				} else {
					if (!right.isLocked()) {
						right.lock(); 
						hand = "right";
					} else  {
						hand = "retry";
					}
				}
			} else {
				if (!left.isLocked()) { //Just left hand free
					if (!left.isLocked()) {
						left.lock(); 
						hand = "left";
					} else  {
						hand = "retry";
					}
				} else { //Just right hand free
					if (!right.isLocked()) {
						right.lock(); 
						hand = "right";
					} else  {
						hand = "retry";
					}
				}
			} 
		}
		return hand;
	}

	public boolean isCatchAllowed() {
		return catchAllowed;
	}

	public void unlock(String lastHand) { //Release lock on hand(ReentrantLock object) because a ball was throwed back to the air
		if (lastHand.equals("left")) {
			if (left.isHeldByCurrentThread()) left.unlock();
		} else {
			if (right.isHeldByCurrentThread()) right.unlock();
		}
		
	}

	public static Logger getLogger() {
		return logger;
	}

	public juggling1.logger getLogic() {
		return logic;
	}

	public void setLogic(juggling1.logger logic) {
		this.logic = logic;
	}
}
