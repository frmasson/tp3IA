package aiplaylist;

import java.util.LinkedList;
import java.util.List;

public class UsageStatistics {

	private class Row {
		protected long time;
		protected boolean skipped;
		
		public Row(long elapseTime, boolean skipped) {
			this.time = elapseTime;
			this.skipped = skipped;
		}
		
	}
	
	private List<Row> rows = new LinkedList<Row>();
	private long startingTime;
	private long endingTime;
	
	public void setStartingTime() {
		startingTime = System.currentTimeMillis();
	}
	
	public void setEndingTime() {
		endingTime = System.currentTimeMillis();
	}
	
	public void notify(boolean played) {
		rows.add(new Row(getElapseTime(System.currentTimeMillis()), !played));
	}
	
	private long getElapseTime(long time) {
		return (time - startingTime) / 1000;
	}
	
	public void exportStatistics() {
		//TODO output to a file
		String line;
		String header = "Time,Skipped,TotalTime\n";
		
		System.out.print(header);
		
		for(Row row : rows) {
			int n = 0;
			if (row.skipped) n = 1;
			line = row.time + "," + n;
			line += "," + getElapseTime(endingTime) + "\n";
			System.out.print(line);
		}
	}
}