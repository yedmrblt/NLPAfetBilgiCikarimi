import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frame;
	public static JComboBox<String> dataSet;
	public static JTextArea sumText; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		dataSet = new JComboBox();
		dataSet.setModel(new DefaultComboBoxModel(new String[] {"Nagano", "Nepal"}));
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.insets = new Insets(0, 0, 5, 0);
		gbc_dataSet.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataSet.gridx = 0;
		gbc_dataSet.gridy = 1;
		panel.add(dataSet, gbc_dataSet);
		
		JButton startSumButton = new JButton("Summarize");
		startSumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.sumText.setText("");
				FindStatic findst = new FindStatic();
			    findst.execute();
			}
		});
		GridBagConstraints gbc_startSumButton = new GridBagConstraints();
		gbc_startSumButton.insets = new Insets(0, 0, 5, 0);
		gbc_startSumButton.gridx = 0;
		gbc_startSumButton.gridy = 3;
		panel.add(startSumButton, gbc_startSumButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel staticPane = new JPanel();
		tabbedPane.addTab("Static", null, staticPane, null);
		GridBagLayout gbl_staticPane = new GridBagLayout();
		gbl_staticPane.columnWidths = new int[]{0, 0};
		gbl_staticPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_staticPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_staticPane.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		staticPane.setLayout(gbl_staticPane);
		
		sumText = new JTextArea();
		sumText.setEditable(false);
		GridBagConstraints gbc_sumText = new GridBagConstraints();
		gbc_sumText.gridheight = 3;
		gbc_sumText.insets = new Insets(0, 0, 5, 0);
		gbc_sumText.fill = GridBagConstraints.BOTH;
		gbc_sumText.gridx = 0;
		gbc_sumText.gridy = 0;
		staticPane.add(sumText, gbc_sumText);
		
		JPanel stanfordPane = new JPanel();
		tabbedPane.addTab("Stanford", null, stanfordPane, null);
		GridBagLayout gbl_stanfordPane = new GridBagLayout();
		gbl_stanfordPane.columnWidths = new int[]{0, 0};
		gbl_stanfordPane.rowHeights = new int[]{0, 0, 0};
		gbl_stanfordPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_stanfordPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		stanfordPane.setLayout(gbl_stanfordPane);
		
		JTextArea stanfordSumText = new JTextArea();
		stanfordSumText.setEditable(false);
		GridBagConstraints gbc_stanfordSumText = new GridBagConstraints();
		gbc_stanfordSumText.fill = GridBagConstraints.BOTH;
		gbc_stanfordSumText.gridx = 0;
		gbc_stanfordSumText.gridy = 1;
		stanfordPane.add(stanfordSumText, gbc_stanfordSumText);
	}

}
