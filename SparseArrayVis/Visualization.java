import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Visualization{
	JFrame frame;
	//panel for showing visualization
	VisualizationPanel visPanel;
	//fields for inputing data
	JTextField actionField, dataField, rowField, colField;
	//buttons for adding/removing data
	JButton addBtn, removeBtn;
	private static int testNumber = 1;
	
	public Visualization(){
		frame = new JFrame("Sparse Array Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(550, 750));
		frame.setLayout(new GridBagLayout());
		
		visPanel = new VisualizationPanel(this);
		createGUI();
		frame.pack();
	}
	
	public static void main(String[] args){
		Visualization instance = new Visualization();
		instance.frame.setVisible(true);
	}
	
	/**
	 * Constructs the GUI.
	 */
	private void createGUI(){
		GridBagConstraints c = new GridBagConstraints();
		//1st Row
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 8;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.insets = new Insets(5, 5, 5, 5);
		actionField = new JTextField();
		actionField.setEnabled(false);
		actionField.setDisabledTextColor(Color.BLACK);
		frame.add(actionField, c);
		//2nd row
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 4;
		c.ipady = 4;
		c.insets = new Insets(1, 1, 1, 1);
		JLabel dataLbl = new JLabel("Data:");
		frame.add(dataLbl, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 100;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		dataField = new JTextField(6);
		MaxLengthTextDocument dataMaxLength = new MaxLengthTextDocument();
		dataMaxLength.setMaxChars(8);//sets max length to 8
		dataField.setDocument(dataMaxLength);//applys the format
		frame.add(dataField, c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 2;
		c.ipady = 4;
		JLabel rowLbl = new JLabel("Row:");
		frame.add(rowLbl, c);
		
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		rowField = new JTextField(4);
		MaxLengthTextDocument rowMaxLength = new MaxLengthTextDocument();
		rowMaxLength.setMaxChars(5);//sets max length to 5
		rowMaxLength.setIntOnly(true);//makes the field numbers only
		rowField.setDocument(rowMaxLength);//applys the format
		frame.add(rowField, c);
		
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 4;
		c.ipady = 4;
		JLabel colLbl = new JLabel("Col:");
		frame.add(colLbl, c);
		
		c.gridx = 5;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		colField = new JTextField(4);
		MaxLengthTextDocument colMaxLength = new MaxLengthTextDocument();
		colMaxLength.setMaxChars(5);//sets max length to 5
		colMaxLength.setIntOnly(true);//makes the field numbers only
		colField.setDocument(colMaxLength);//applys the format
		frame.add(colField, c);
		
		c.gridx = 6;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		addBtn = new JButton("Add");
		frame.add(addBtn, c);
		
		c.gridx = 7;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		removeBtn = new JButton("Remove");
		frame.add(removeBtn, c);
		
		//Speed buttons, 3rd row
		JPanel speedPanel = new JPanel();
		JLabel speedLbl = new JLabel("Speed:");//Label for radio buttons
		speedPanel.add(speedLbl);
		JRadioButton slowBtn = new JRadioButton("Slow");//"Slow" radio button
		speedPanel.add(slowBtn);
		JRadioButton mediumBtn = new JRadioButton("Medium");//"Medium" radio button
		speedPanel.add(mediumBtn);
		JRadioButton fastBtn = new JRadioButton("Fast");//"Fast" radio button
		speedPanel.add(fastBtn);
		JRadioButton instantBtn = new JRadioButton("Instant");//"Instant" radio button
		speedPanel.add(instantBtn);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(slowBtn);
		bg.add(mediumBtn);
		bg.add(fastBtn);
		bg.add(instantBtn);
		instantBtn.setSelected(true);//default speed "instant"
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 8;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		frame.add(speedPanel, c);
		
		slowBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				visPanel.setSpeed(1750);
			}
		});
		
		mediumBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				visPanel.setSpeed(1000);
			}
		});
		
		fastBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				visPanel.setSpeed(500);
			}
		});
		
		instantBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				visPanel.setSpeed(0);
			}
		});
		
		//4th row
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 8;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(5, 5, 5, 5);
		frame.add(visPanel, c);

		addBtn.addActionListener(new ActionListener(){
			/**
			 * grabs the data and calls an insertion into the array
			 */
			public void actionPerformed(ActionEvent e){
				if(!validate()){
					return;
				}
				String data = dataField.getText();
				if(data.equals("")){
					data = "test "+testNumber;
					testNumber++;
				}
				int row = Integer.parseInt(rowField.getText());
				int col = Integer.parseInt(colField.getText());
				dataField.setText("");
				rowField.setText("");
				colField.setText("");
				visPanel.putAt(row,col,data+" ");
			}
		});
		/**
		 * grabs the data and calls a removal from the array, only coordinates are needed
		 */
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!validate()){
					return;
				}
				int row = Integer.parseInt(rowField.getText());
				int col = Integer.parseInt(colField.getText());

				rowField.setText("");
				colField.setText("");
				
				visPanel.remove(row, col);
			}
		});
		visPanel.putAt(1,1,"");
	    visPanel.remove(1,1);
	}
	
	public boolean validate(){
		String msg = "";		
		if(rowField.getText().equals("")){
			msg += "No row entered.\n";
		}
		
		if(colField.getText().equals("")){
			msg += "No col entered.\n";
		}
		
		if(msg.equals("")){
			return true;
		}
		
		JOptionPane.showMessageDialog(frame, msg);
		return false;
	}
	
	@SuppressWarnings("serial")
	/**
	 * This is used to restrict input by not inserting data into fields after they have reached
	 * capacity. In terms of row and col field, it restricts input to integers only.
	 */
	private class MaxLengthTextDocument extends PlainDocument {
		private int maxChars;//the max number of characters permitted
		private boolean intOnly=false;//allows all text or just ints
		@Override
		public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
			if(str== null || (getLength() + str.length() > maxChars)){//checks length
				Toolkit.getDefaultToolkit().beep();//if too long, beep
			}
			else if(!intOnly||intOnly&&isInt(str)){//if allowable data
				if(!intOnly){
					str=str.toLowerCase();
				}
				super.insertString(offs, str, a);//insert it into the field
			}			
		}
		public void setMaxChars(int maxChars) {
			this.maxChars = maxChars;
		}
		public void setIntOnly(boolean intOnly) {
			this.intOnly = intOnly;
		}
	}
	/**
	 * Checks if the string is an int. Primarily used by MaxLengthTextDocument
	 * @param str Check if this is an int
	 * @return	true if the string is an int, false otherwise
	 */
	private boolean isInt(String str){
		try{
			Integer.parseInt(str);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	/**
	 * Display str in the actionField
	 * @param str
	 */
	public void display(String str){
		actionField.setText(str);
		update();
	}
	
	/**
	 * Repaint the frame.
	 */
	private void update(){
		frame.paintComponents(frame.getGraphics());
		visPanel.update(visPanel.getGraphics());
	}
}
