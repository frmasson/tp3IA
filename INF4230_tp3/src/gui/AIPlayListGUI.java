package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import aiplaylist.AIPlayList;
import aiplaylist.AprioriSequencer;
import aiplaylist.Item;

public class AIPlayListGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private static AIPlayList engine;

	private boolean debugMode = false;

	private static AIPlayListGUI main;
	private JLabel itemDisplay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final Map<String, String> params = new HashMap<String, String>();
		for (String param : args) {
			String[] parsedParam = param.split("=");
			params.put(parsedParam[0].toLowerCase(),
					parsedParam[1].toLowerCase());
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					main = new AIPlayListGUI(params.get("mode"),
							new AIPlayList(params.get("source"), params
									.get("sequencer"), params
									.get("libraryloader")));
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param mode
	 */
	public AIPlayListGUI(String mode, AIPlayList apl) {
		engine = apl;
		if (mode != null && mode.equals("debug")) {
			this.debugMode = true;
		}
		
		addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent event) {
        		engine.getSequencer().getUsageStats().setEndingTime();
        		engine.getSequencer().getUsageStats().exportStatistics();
        		System.exit(0);
        	}
        });

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 490, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		itemDisplay = new JLabel("Click Next to Begin");
		itemDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		itemDisplay.setHorizontalAlignment(SwingConstants.CENTER);

		JButton nextButton = new JButton("NEXT");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.next();
			}
		});

		JPanel debugPanel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addComponent(itemDisplay, GroupLayout.DEFAULT_SIZE, 463,
						Short.MAX_VALUE)
				.addComponent(nextButton, GroupLayout.DEFAULT_SIZE, 463,
						Short.MAX_VALUE)
				.addComponent(debugPanel, GroupLayout.DEFAULT_SIZE, 463,
						Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(itemDisplay)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(nextButton)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(debugPanel, GroupLayout.DEFAULT_SIZE, 88,
								Short.MAX_VALUE)));
		debugPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton LoadLibraryButton = new JButton("Load Library");
		LoadLibraryButton.setHorizontalAlignment(SwingConstants.LEFT);
		debugPanel.add(LoadLibraryButton);

		JButton btnLike = new JButton("Like");
		btnLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.like();
			}
		});

		JPanel aprioriPanel = new JPanel();
		debugPanel.add(aprioriPanel);

		JButton btnRecord = new JButton("Record");
		aprioriPanel.add(btnRecord);

		JButton btnLoadTransactions = new JButton("Load Transactions");
		aprioriPanel.add(btnLoadTransactions);
		btnLoadTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				engine.setSequencer(new AprioriSequencer());
			}
		});
		aprioriPanel
				.setVisible(engine.getSequencer() instanceof AprioriSequencer);
		debugPanel.add(btnLike);
		debugPanel.setVisible(debugMode);
		contentPane.setLayout(gl_contentPane);
	}
	
	

	protected void like() {
		setDisplay(engine.like());
	}

	protected void next() {
		setDisplay(engine.next());
	}

	private void setDisplay(Item next) {
		if (next == null) {
			this.itemDisplay.setText("No Songs Available");
		} else {
			this.itemDisplay.setText(next.getDisplayName());
		}
	}
}
