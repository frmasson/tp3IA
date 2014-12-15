package aiplaylist;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
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
	
	private class PerfRow {
		protected long time;
		protected long genTime;
		
		public PerfRow(long elapseTime, long genTime) {
			this.time = elapseTime;
			this.genTime = genTime;
		}
	}
	
	private List<Row> rows = new ArrayList<Row>();
	private List<PerfRow> perfRows = new LinkedList<PerfRow>();
	private long startingTime;
	private long endingTime;
	
	private long startGenTime;
	
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
	
	public void notifyStopGen() {
		long elapseTime = getElapseTime(System.currentTimeMillis());
		long genTime = System.currentTimeMillis() - startGenTime;
		
		perfRows.add(new PerfRow(elapseTime, genTime));
	}
	
	public void notifyStartGen(){
		this.startGenTime = System.currentTimeMillis();
	}
	
	private long getElapseTime(long time) {
		return (time - startingTime);
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
		
		header = "Time,GenTime\n";
		lines = "";
		PerfRow prev = null;
		long averageTime = 0;
		long totalRow = 0;
		
		if (perfRows.size() > 0)
			prev = perfRows.get(0);
		
		for (PerfRow row : perfRows) {
			if (row.time < prev.time + 2) {
				averageTime += row.genTime;
				totalRow += 1;
			} else if (totalRow > 0) {
				lines += row.time + "," + (averageTime / totalRow) + "\n";
				
				totalRow = 0;
				averageTime = 0;
				prev = row;
			} else {
				lines += row.time + "," + row.genTime + "\n";
				prev = row;
			}
		}
		
		if (averageTime != 0) {
			lines += getElapseTime(endingTime) + "," + (averageTime/totalRow) + "\n";
		}
	
		
		System.out.println();
		System.out.print(header);
		System.out.print(lines);
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("PerfStats.csv")));
			writer.write(header);
			writer.write(lines);
			writer.close();
		} catch (IOException ex) {
			System.err.println("Error while writing prefstats to file !");
		}
		
		
	}
}