package juggling1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("rawtypes")
public class CatchStop extends Thread implements Future{
	private boolean done = false;
	private boolean cancel = false;
	
	@Override
	public void run() { //Wait the catchable time and then activate done boolean flag
		int time = 15000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {} //just finalize
		done = true;
	}

	@Override
	public boolean cancel(boolean set) {
		done = true;
		cancel = set;
		return set;
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		return null;
	}
	@Override
	public Object get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}
	@Override
	public boolean isCancelled() { 
		return cancel;
	}
	@Override
	public boolean isDone() { 
		return done;
	}
	
}
