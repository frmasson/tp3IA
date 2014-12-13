package aiplaylist;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class UsageStatistics {

	private class Row {
		protected long time;
		protected int skipped;
		
		public Row(long elapseTime, int skipped) {
			this.time = elapseTime;
			this.skipped = skipped;
		}
		
	}
	
	private List<Row> rows = new ArrayList<Row>();
	private long startingTime;
	private long endingTime;
	
	public UsageStatistics() {
		rows.add(new Row(0, 0));
	}
	
	public void setStartingTime() {
		startingTime = System.currentTimeMillis();
	}
	
	public void setEndingTime() {
		endingTime = System.currentTimeMillis();
	}
	
	public void notify(boolean played) {
		long elapseTime = getElapseTime(System.currentTimeMillis());
		if (rows.get(rows.size() - 1).time == elapseTime) {
			if (!played) rows.get(rows.size() - 1).skipped += 1;
		} else {
			rows.add(new Row(elapseTime, !played ? 1 : 0));
		}
	}
	
	private long getElapseTime(long time) {
		return (time - startingTime) / 1000;
	}
	
	public void exportStatistics() {
		Writer writer;
		String lines = "";
		String header = "Time,Skipped,TotalTime\n";
		
		System.out.print(header);
		
		for(Row row : rows) {
			lines += row.time + "," + row.skipped;
			lines += "," + getElapseTime(endingTime) + "\n";
		}
		
		System.out.print(lines);
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Stats.csv")));
			writer.write(header);
			writer.write(lines);
			writer.close();
		} catch (IOException ex) {
			System.err.println("Error while writing stats to file !");
		}
		
		
	}
}