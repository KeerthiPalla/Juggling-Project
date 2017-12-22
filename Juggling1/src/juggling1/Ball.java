package juggling1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Ball implements Runnable{
	 private static final Logger logger = Logger.getLogger("Juggling1");
	    private Logger logic;
	    private static final Map<Integer, Character> ballsNames = new HashMap<Integer, Character>(); 
	private String name;
	private boolean catched = false;
	private HandsController hands;
	private boolean fallen = false;
	private String lastHand;
	private CatchStop cs;
	private int timeOnHand;
	
	public Ball(int n, HandsController hands){
		this.name = generateName(n);
		this.hands = hands;
	}
	
	@Override
	public void run() {
		if (!fallen) { 
			//the ball object sleep the time while ball is on air
			int delay = 1000 + ((int) (Math.random()*500));
			logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": The ball " + name + " enters the air for " + delay + " miliseconds");			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// a new "CatchStop" object is created in a separate thread to mesure "on catchable zone time" and notify
			// this thread to stop catching the ball 
			cs = new CatchStop();
			cs.start();
			startCatch();
			if (!catched) {
				// If the ball hits the ground, end.
				logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": The ball " + name + " hit the ground");
				fallen();
				fallen = true;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else { 		
				// If the ball is caught, hold it (sleep) for between 100ms and 200ms				
				try {
					Thread.sleep(timeOnHand);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hands.unlock(lastHand);
				catched = false;
			   logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": The " + lastHand + " hand becomes free");
			}
			//Go back to ball in air.
			cs.interrupt();
			this.run();
		}		
	}
	
	private void startCatch(){ 	// the balls enter this function to ask an avaliable hand to catch it
								// this function is leaved only when catchable time is over or another ball hit the ground 
		logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": The ball " + name + " enters the catchable zone");
		while (hands.isCatchAllowed() && (!cs.isDone()) ){
			if (hands.isAnyHandAvaliable()) {
				lastHand = hands.lock();
				if (!lastHand.equals("retry")) {
					cs.cancel(true);
					catched = true;
					timeOnHand = (int) (100 + (Math.random() * 100));
					logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + ": The ball " + name + " have been catched by the " + lastHand + "hand for " + timeOnHand + "miliseconds");
				}
			} else {				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void fallen() {} //callback
	
	private String generateName(int n) { //This function generates string with the alphabet repetitions (A..Z.AA..AZ.AAA..ZZZ...)
		String name = "";
		String ballsNames = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int length = ballsNames.length();
		
		do {
			if (n%length == 0) {
				name = String.valueOf(ballsNames.charAt((length-1))) + name;
				if (n == length) break;
				n = n/length;
				if (n == 0) break;
				n = n-1;
			} else {
				name = String.valueOf(ballsNames.charAt((n%length)-1)) + name;
				if (n == length) break;
				n = n/length;
			}		
		}
		while (n >= 1);
		return name;
	}

	public String getName() {
		return name;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Map<Integer, Character> getBallsnames() {
		return ballsNames;
	}

	public Logger getLogic() {
		return logic;
	}

	public void setLogic(Logger logic) {
		this.logic = logic;
	}

}
