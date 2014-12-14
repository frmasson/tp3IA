package aiplaylist;

import org.junit.Before;
import org.junit.Test;

public class UsageStatisticsTest {
	
	private UsageStatistics stats;

	@Before
	public void setUp() throws Exception {
		stats = new UsageStatistics();
		stats.setStartingTime();
	}

	@Test
	public void test() {
		for (int i = 0; i < 6; i++) {
			stats.notify(true);
			try {
			    Thread.sleep(500);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		
		for (int i = 0; i < 2; i++) {
			stats.notify(false);
			stats.notifyStartGen();
			try {
			    Thread.sleep(500);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			stats.notifyStopGen();
		}
		
		for (int i = 0; i < 4; i++) {
			stats.notify(true);
			stats.notifyStartGen();
			try {
			    Thread.sleep(500);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			stats.notifyStopGen();
		}
		
		stats.setEndingTime();
		
		stats.exportStatistics();
		
	}

}
