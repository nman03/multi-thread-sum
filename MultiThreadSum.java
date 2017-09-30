
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadSum  {
	private static Integer sumWithNoSync = new Integer(0);
	private static SynchronizedSum syncSum = new SynchronizedSum();
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 0 ; i < 1000 ; i++) {
			executor.execute(new Runner());
		}
		
		executor.shutdown();
		
		while (!executor.isTerminated()) {
		}
		
		System.out.println("Sum without sync is " + sumWithNoSync);
		System.out.print("Sum with sync is " + syncSum.getSum());
	}	
	
	public static class Runner implements Runnable {
		public void run() {
			sumWithNoSync = new Integer(sumWithNoSync) + 1;
			syncSum.incSum();
		}
	}
	
	public static class SynchronizedSum {
		private static Lock lock = new ReentrantLock();
		private Integer sumWithSync = new Integer(0);
		
		public Integer getSum(){
			return sumWithSync;
		}
		
		public void incSum() {
			lock.lock();
			
			try {
				sumWithSync++;
			}
			finally {
				lock.unlock();
			}	
		}		
	}
}
