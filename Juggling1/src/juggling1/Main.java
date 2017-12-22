package juggling1;

import java.util.logging.Logger;

public class Main {
	private static final Logger logger = Logger.getLogger("JugglingBall");
	private static BallsController balls = new BallsController();
	private static HandsController hands = new HandsController();
		
	public static void main(String[] args) {					
		hands.setBalls(balls);
		balls.initPool();
		while (balls.noBallsHitTheGround) { //Throw a new ball every 2 seconds
			balls.newBall(hands);
			try {
				Thread.sleep((2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		balls.shutdownPool(); //Shutdown all ball threads
		logger.info("program terminated");
	}
}
