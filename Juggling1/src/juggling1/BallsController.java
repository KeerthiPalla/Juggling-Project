package juggling1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class BallsController {
	 private static final Logger logger = Logger.getLogger("Juggling1");
	public boolean noBallsHitTheGround = true;
	private static ExecutorService threadPool;
	private Map<Integer, String> ballsNames = new HashMap<Integer, String>();
	
	public void initPool() {
		threadPool = Executors.newCachedThreadPool(); 		
	}

	public void newBall(HandsController hands) { //create a new ball object and execute runnable code in a new thread
		Ball ball = new Ball(ballsNames.size()+1, hands){
			@Override
			public void fallen(){
				if (noBallsHitTheGround) {
					hands.stopCatching();
				}
				noBallsHitTheGround = false;
			}
		};				
		logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": Ball " + ball.getName() + " is added");	
		ballsNames.put(ballsNames.size(), ball.getName());
		threadPool.execute(ball);	
	}

	public void shutdownPool() { //shutdown all ball threads
		threadPool.shutdown();		
	}
	
	public int getNBalls(){
		return ballsNames.size();
	}
}
