import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class VisualizationPanel extends JPanel{
	private static final long serialVersionUID = -8033402239715068805L;
	//HashMaps that map a ListNode to its corresponding list of Nodes
	private HashMap<ListNode, ArrayList<Node>> columnList, rowList;
					  //Height and width of a ListNode
	private final int LIST_NODE_WIDTH = 49, LIST_NODE_HEIGHT = 17,
					  //Height and width of a Node
					  NODE_HEIGHT = 17, NODE_WIDTH = 104,
					  //Initial y and x positions for the columnList and rowList, respectively
					  COL_Y = 4, ROW_X = 4,
					  //Initial x and y positions for the columnList and rowList, respectively
					  INIT_COL_X = 80, INIT_ROW_Y = 40,
					  //Standard "grid" x and y distances
					  STD_X_DIST = 150, STD_Y_DIST = 40;
						//Colors of the background and foreground
	private final Color BG = Color.white, FG = Color.black,
						//Colors of components when being added, removed, and looked at
						ADD = Color.green, REMOVE = Color.red, FOCUS = Color.blue;
	private int speed;
	private AnimationTimer timer;
	private Visualization vis;
	public VisualizationPanel(Visualization vis){
		setBackground(BG);
		this.vis=vis;
		columnList = new HashMap<ListNode, ArrayList<Node>>();
		rowList = new HashMap<ListNode, ArrayList<Node>>();
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param data
	 * @return	calls the inserter to animate an insertion of the data at row x col x
	 */
	public void putAt(int row, int col,String data){
		@SuppressWarnings("unused")
		Inserter i = new Inserter(row, col,data);
	}
	
	/**
	 * 
	 * @author John
	 *
	 */
	private class Inserter implements Runnable{
		int row, col;
		String data;
		Thread iThread;
		/**
		 * 
		 * @param row the row to insert at
		 * @param col the col to insert at
		 * @param data the data to insert
		 */
		public Inserter(int row, int col,String data){
			iThread= new Thread(this);
			this.row = row;
			this.col = col;
			this.data=data;
			iThread.run();
		}
		/**
		 * this updates the panel and waits for sleep seconds.
		 */
		private void haveANap(){
			update(getGraphics());
			timer=new AnimationTimer();
			try {
				timer.getThread().join();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer.stop();
		}
		
		public void run(){	
			Boolean done=false;
			ThreeReturnValues colReturnVals=colExists(col);
			Boolean cIsHead=(Boolean) colReturnVals.getFirst();
			ListNode colListNode=(ListNode) colReturnVals.getSecond();
			ListNode prevColListNode=(ListNode) colReturnVals.getThird();
			
			ThreeReturnValues rowReturnVals=rowExists(row);
			Boolean rIsHead=(Boolean) rowReturnVals.getFirst();
			ListNode rowListNode=(ListNode) rowReturnVals.getSecond();
			ListNode prevRowListNode=(ListNode) rowReturnVals.getThird();

			Node newNode=new Node(row,col,data);
			if(colListNode==null){
				if(!cIsHead&&prevColListNode.getListLineColor()==FG){
					vis.display("Hold the reference in memory for the new node.");
					prevColListNode.setListLineColor(REMOVE);
					haveANap();
					prevColListNode.setListLineColor(BG);
					haveANap();
				}
				colListNode=new ListNode(col);
				vis.display("Insert the new List Node.");
				columnList.put(colListNode,null);
				haveANap();
				colListNode.setColor(FOCUS);
				haveANap();
				if(!cIsHead){
					vis.display("Set a reference from the previous node to the new node.");
					prevColListNode.setListLineColor(ADD);
					haveANap();
					prevColListNode.setListLineColor(FG);
					haveANap();
					prevColListNode.setColor(FG);
					haveANap();
				}
				colListNode.setColor(FOCUS);
				haveANap();
				if(getColumns().get(getColumns().size()-1)!=colListNode){
					vis.display("Set a reference in our new List Node to the next List Node.");
					colListNode.setListLineColor(ADD);
					haveANap();
					colListNode.setListLineColor(FG);
					haveANap();
				}
			}
			if(rowListNode==null){		
				if(!rIsHead&&prevRowListNode.getListLineColor()==FG){
					vis.display("Hold the reference in memory for the new node.");
					prevRowListNode.setListLineColor(REMOVE);
					haveANap();
					prevRowListNode.setListLineColor(BG);
					haveANap();
				}
				rowListNode=new ListNode(row);
				vis.display("Insert the new List Node.");
				rowList.put(rowListNode,null);
				haveANap();
				rowListNode.setColor(FOCUS);
				haveANap();
				if(!rIsHead){
					vis.display("Set a reference from the previous node to the new node.");
					prevRowListNode.setListLineColor(ADD);
					haveANap();
					prevRowListNode.setListLineColor(FG);
					haveANap();
					prevRowListNode.setColor(FG);
					haveANap();
				}
				rowListNode.setColor(FOCUS);
				haveANap();
				if(getRows().get(getRows().size()-1)!=rowListNode){
					vis.display("Set a reference in our new List Node to the next List Node.");
					rowListNode.setListLineColor(ADD);
					haveANap();
					rowListNode.setListLineColor(FG);
					haveANap();
				}
			}
			ArrayList<Node> colArray=columnList.get(colListNode);
			
			ArrayList<Node> rowArray=rowList.get(rowListNode);
			
			Node prevNodeC=null;
			Boolean insertedToCol=false;
			Boolean insertedToRow=false;
			if(colArray!=null){
				colArray=columnList.get(colListNode);
					//this itterates through the col and finds the node if it exists. It highlights each node
					//and each line along the way. If it passes where the node should go it highlights the node
					//before it.
				for(Node colNode:colArray){
					if(prevNodeC!=null){
						prevNodeC.setColor(FG);
						haveANap();
						prevNodeC.setBottomLineColor(FOCUS);
						haveANap();
						prevNodeC.setBottomLineColor(FG);
						haveANap();
					}
					else{
						vis.display("Get the first node from the List Node.");
						colListNode.setNodeLC(FOCUS);
						haveANap();
						colListNode.setNodeLC(FG);
						haveANap();
					}
					colNode.setColor(FOCUS);
					haveANap();
					if(colNode.getRow()==row){
						vis.display("This node already exists. We change it's data to "+data+" and we are done.");
						colNode.setColor(ADD);
						colNode.setData(data);
						haveANap();
						colNode.setColor(FG);
						haveANap();
						insertedToCol=true;
						done=true;
						break;
					}
					else if(colNode.getRow()>row){
						if(prevNodeC!=null){
							vis.display("We have passed where our node should be so we look at the previous node.");
							prevNodeC.setColor(FOCUS);
							haveANap();
							vis.display("Hold the reference to the next node for our new node.");
							prevNodeC.setBottomLC(REMOVE);
							haveANap();
							prevNodeC.setBottomLC(BG);
							haveANap();
						}
						else{
							vis.display("The new node will be the first in the column so hold a reference to the next node.");
							colListNode.setNodeLineColor(REMOVE);
							haveANap();
							colListNode.setNodeLineColor(BG);
							haveANap();
						}
						colNode.setColor(FG);
						haveANap();
						break;
					}
					prevNodeC=colNode;
				}
			}
			Node prevNodeR=null;
			
			if(!done&&rowArray!=null){
				rowArray=rowList.get(rowListNode);
					//this itterates through the row and finds the node if it exists. It highlights each node
					//and each line along the way. If it passes where the node should go it highlights the node
					//before it.
				for(Node rowNode:rowArray){
					if(prevNodeR!=null){
						prevNodeR.setColor(FG);
						haveANap();
						prevNodeR.setRightLineColor(FOCUS);
						haveANap();
						prevNodeR.setRightLineColor(FG);
						haveANap();
					}
					else{
						vis.display("Get the first node from the List Node.");
						rowListNode.setNodeLC(FOCUS);
						haveANap();
						rowListNode.setNodeLC(FG);
						haveANap();
						
					}
					rowNode.setColor(FOCUS);
					haveANap();
					if(rowNode.getCol()==col){
						rowNode.setColor(ADD);
						rowNode.setData(data);
						haveANap();
						rowNode.setColor(FG);
						haveANap();
						insertedToRow=true;
						break;
					}
					else if(rowNode.getCol()>col){
						if(prevNodeR!=null){
							vis.display("We have passed where our node should be so we look at the previous node.");
							prevNodeR.setColor(FOCUS);
							haveANap();
							vis.display("Hold the reference to the next node for our new node.");
							prevNodeR.setRightLC(REMOVE);
							haveANap();
							prevNodeR.setRightLC(BG);
							haveANap();
						}
						else{
							vis.display("Our new node will be the first in the column so hold a reference to the next node.");
							rowListNode.setNodeLineColor(REMOVE);
							haveANap();
							rowListNode.setNodeLineColor(BG);
							haveANap();
							
						}
						rowNode.setColor(FG);
						haveANap();
						break;
					}
					prevNodeR=rowNode;
				}
			}
			if(!done&&colArray==null){
				ArrayList<Node> newArray=new ArrayList<Node>();
				newArray.add(newNode);
				columnList.remove(colListNode);
				
				columnList.put(colListNode,newArray);
				vis.display("Add the new node.");
				newNode.setColor(ADD);
				colListNode.setNodeLineColor(BG);
				haveANap();
				newNode.setColor(FG);
				haveANap();
				vis.display("Set a reference to the new node from the List Node.");
				colListNode.setNodeLineColor(ADD);
				haveANap();
				colListNode.setNodeLineColor(FG);
				haveANap();
				colListNode.setColor(FG);
				haveANap();
				insertedToCol=true;
			}
			if(!done&&!insertedToCol){
				colArray.add(newNode);
				Collections.sort(colArray);
				columnList.remove(colListNode);
				columnList.put(colListNode,colArray);
				vis.display("Add the new node.");
				newNode.setColor(ADD);
				haveANap();
				newNode.setColor(FG);
				haveANap();
				
				if(prevNodeC!=null){
					vis.display("Add a reference from the previous node to the new node.");
					prevNodeC.setBottomLC(ADD);
					haveANap();
					prevNodeC.setBottomLC(FG);
					haveANap();
				}
				else{
					vis.display("This is the first node in the column so set a reference from the List Node to the new node.");
					colListNode.setNodeLC(ADD);
					haveANap();
					colListNode.setNodeLC(FG);
					haveANap();
				}
				if(columnList.get(colListNode).get(columnList.get(colListNode).size()-1)!=newNode){
					vis.display("Set a reference from the new node to the next node.");
					newNode.setBottomLC(ADD);
					haveANap();
					newNode.setBottomLC(FG);
					haveANap();
				}
				if(prevNodeC!=null){
					prevNodeC.setColor(FG);
					haveANap();
				}
				colListNode.setColor(FG);
				haveANap();
			}
			if(!done&&rowArray==null){
				ArrayList<Node> newArray=new ArrayList<Node>();
				newArray.add(newNode);
				rowList.remove(rowListNode);
				rowList.put(rowListNode,newArray);
				vis.display("Set a reference to the new node.");
				rowListNode.setNodeLineColor(ADD);
				haveANap();
				rowListNode.setNodeLineColor(FG);
				haveANap();
				rowListNode.setColor(FG);
				haveANap();
				insertedToRow=true;
			}
			if(!done&&!insertedToRow){
				rowArray.add(newNode);
				Collections.sort(rowArray);
				rowList.remove(rowListNode);
				rowList.put(rowListNode,rowArray);
				vis.display("Add the new node.");
				if(prevNodeR!=null){
					vis.display("Add a reference from the previous node to the new node.");
					prevNodeR.setRightLC(ADD);
					haveANap();
					prevNodeR.setRightLC(FG);
					haveANap();
				}
				else{
					vis.display("This is the first node in the column so set a reference from the List Node to the new node.");
					rowListNode.setNodeLC(ADD);
					haveANap();
					rowListNode.setNodeLC(FG);
					haveANap();
				}
				
				if(rowList.get(rowListNode).get(rowList.get(rowListNode).size()-1)!=newNode){
					vis.display("Set a reference from the new node to the next node.");
					newNode.setRightLC(ADD);
					haveANap();
					newNode.setRightLC(FG);
					haveANap();
				}
				if(prevNodeR!=null){
					prevNodeR.setColor(FG);
					haveANap();
				}
				rowListNode.setColor(FG);
			}
			
			fgAll();
			vis.display("Done");
			haveANap();
			vis.display("");
		}
	}
	/**
	 * Removes the node at row and col.
	 * @param row the row of the node to be removed
	 * @param col the column of the node to be removed
	 */
	public void remove(int row, int col){
		Remover r = new Remover(row, col);
		r.run();
	}
	
	private class Remover implements Runnable{
		int row, col;
		
		public Remover(int row, int col){
			this.row = row;
			this.col = col;
		}
		
		private void haveANap(){
			update(getGraphics());
			timer=new AnimationTimer();
			try {
				timer.getThread().join();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void run(){
			ThreeReturnValues r = rowExists(row);
			ThreeReturnValues c = colExists(col);
			
			ListNode rowNode = (ListNode) r.getSecond();
			ListNode colNode = (ListNode) c.getSecond();
			boolean rowIsHead = (Boolean) r.getFirst();
			boolean colIsHead = (Boolean) r.getFirst();
			
			Node[] nodeVals = findNode(row, col);
			Node node = nodeVals[0];
			if(node == null){
				return;
			}
			Node prevInCol = nodeVals[1];
			Node prevInRow = nodeVals[2];
			
			ArrayList<Node> rowNodes = rowList.get(rowNode);
			vis.display("Finding node");
			rowNode.setNodeLineColor(FOCUS);
			haveANap();
			rowNode.setNodeLineColor(FG);
			haveANap();
			
			for(Node n : rowNodes){
				n.setColor(FOCUS);
				haveANap();
				if(n == node){
					break;
				}
				n.setColor(FG);
				haveANap();
				n.setRightLineColor(FOCUS);
				haveANap();
				n.setRightLineColor(FG);
				haveANap();
			}
			
			ArrayList<Node> colNodes = columnList.get(colNode);
			colNode.setNodeLineColor(FOCUS);
			haveANap();
			colNode.setNodeLineColor(FG);
			haveANap();
			
			for(Node n : colNodes){
				n.setColor(FOCUS);
				haveANap();
				if(n == node){
					break;
				}
				n.setColor(FG);
				haveANap();
				n.setBottomLineColor(FOCUS);
				haveANap();
				n.setBottomLineColor(FG);
				haveANap();
			}
			
			boolean lastInCol = lastInCol(node);
			if(!lastInCol){
				vis.display("Removing link to next node in column.");
				node.setBottomLineColor(REMOVE);
				haveANap();
				node.setBottomLineColor(BG);
				haveANap();
			}
			
			boolean lastInRow = lastInRow(node);
			if(!lastInRow){
				vis.display("Removing link to next node in row.");
				node.setRightLineColor(REMOVE);
				haveANap();
				node.setRightLineColor(BG);
				haveANap();
			}
			
			if(prevInRow == null){
				vis.display("Removing link to row head node.");
				rowNode.setNodeLineColor(REMOVE);
				haveANap();
				rowNode.setNodeLineColor(BG);
				haveANap();
			}else{
				vis.display("Removing link to previous in row.");
				prevInRow.setRightLineColor(REMOVE);
				haveANap();
				prevInRow.setRightLineColor(BG);
				haveANap();
			}
			
			if(prevInCol == null){
				vis.display("Removing link to column head node.");
				colNode.setNodeLineColor(REMOVE);
				haveANap();
				colNode.setNodeLineColor(BG);
				haveANap();
			}else{
				vis.display("Removing link to prevous in column.");
				prevInCol.setBottomLineColor(REMOVE);
				haveANap();
				prevInCol.setBottomLineColor(BG);
				haveANap();
			}
			
			vis.display("Removing node.");
			node.setColor(REMOVE);
			haveANap();
			//remove node
			columnList.get(colNode).remove(node);
			rowList.get(rowNode).remove(node);
			haveANap();
			
			if(!lastInRow){
				if(prevInRow == null){
					vis.display("Adding link from row node to next node in row.");
					rowNode.setNodeLineColor(ADD);
					haveANap();
					rowNode.setNodeLineColor(FG);
					haveANap();
				}else{
					vis.display("Adding link from prevous node in row to next node in row.");
					prevInRow.setRightLineColor(ADD);
					haveANap();
					prevInRow.setRightLineColor(FG);
					haveANap();
				}
			}
			
			if(!lastInCol){
				if(prevInCol == null){
					vis.display("Adding link from column node to next node in column.");
					colNode.setNodeLineColor(ADD);
					haveANap();
					colNode.setNodeLineColor(FG);
					haveANap();
				}else{
					vis.display("Adding link from column node in column to next node in column.");
					prevInCol.setBottomLineColor(ADD);
					haveANap();
					prevInCol.setBottomLineColor(FG);
					haveANap();
				}
			}
			
			if(rowList.get(rowNode) == null || rowList.get(rowNode).size() == 0){
				ArrayList<ListNode> rows = getRows();
				int index = rows.indexOf(rowNode);
				if(index < rows.size()-1){
					vis.display("Removing link to next row node.");
					rowNode.setListLineColor(REMOVE);
					haveANap();
					rowNode.setListLineColor(BG);
					haveANap();
				}
				if(!rowIsHead){
					vis.display("Removing link to prevous row node.");
					rows.get(index-1).setListLineColor(REMOVE);
					haveANap();
					rows.get(index-1).setListLineColor(BG);
					haveANap();
				}
				vis.display("Removing row node.");
				rowNode.setColor(REMOVE);
				haveANap();
				rowNode.setColor(BG);
				haveANap();
				rowList.remove(rowNode);
				if(!rowIsHead && index != rows.size()-1){
					vis.display("Adding link from prevous row node to next row node.");
					rows.get(index-1).setListLineColor(ADD);
					haveANap();
					rows.get(index-1).setListLineColor(FG);
					haveANap();
				}
			}
			
			if(columnList.get(colNode) == null || columnList.get(colNode).size() == 0){
				ArrayList<ListNode> columns = getColumns();
				int index = columns.indexOf(colNode);
				if(index < columns.size()-1){
					vis.display("Removing link to next column node.");
					colNode.setListLineColor(REMOVE);
					haveANap();
					colNode.setListLineColor(BG);
					haveANap();
				}
				if(!colIsHead){
					vis.display("Removing link to previous column node.");
					columns.get(index-1).setListLineColor(REMOVE);
					haveANap();
					columns.get(index-1).setListLineColor(BG);
					haveANap();
				}
				vis.display("Removing column node.");
				colNode.setColor(REMOVE);
				haveANap();
				colNode.setColor(BG);
				haveANap();
				columnList.remove(colNode);
				haveANap();
				if(!colIsHead && index != columns.size()-1){
					vis.display("Adding link from previous column node to next column node.");
					columns.get(index-1).setListLineColor(ADD);
					haveANap();
					columns.get(index-1).setListLineColor(FG);
					haveANap();
				}
			}
			
			vis.display("Done");
			fgAll();
			haveANap();
			vis.display("");	
		}
	}
	/**
	 * 
	 * @author John
	 *this is used to create a delay between animations
	 */
	private class AnimationTimer implements Runnable{
		Thread timer;
		public AnimationTimer(){
			timer = new Thread(this);
			timer.run();
		}
		public void run(){			
			try {
				Thread.currentThread();
				Thread.sleep(speed);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void stop() {
			timer = null;
		}
		public Thread getThread(){
			return timer;
		}
	}
	
	
	
	private ArrayList<ListNode> getColumns(){
		Set<ListNode> cols = columnList.keySet();
		ArrayList<ListNode> columns = new ArrayList<ListNode>();
		for(ListNode col : cols){
			columns.add(col);
		}
		Collections.sort(columns);
		return columns;
	}
	
	private ArrayList<ListNode> getRows(){
		Set<ListNode> rows = rowList.keySet();
		ArrayList<ListNode> rowz = new ArrayList<ListNode>();
		for(ListNode row : rows){
			rowz.add(row);
		}
		Collections.sort(rowz);
		return rowz;
	}
	
	/**
	 * Finds the index in rows of the ListNode that has the same row as the row of n
	 * @param rows ListNodes in which to find the ListNode
	 * @param n Node to find corresponding row
	 * @return index of the ListNode in rows, -1 if not in it
	 */
	private int findRow(ArrayList<ListNode> rows, Node n){
		for(int i = 0; i < rows.size(); i++){//find which row node corresponds to the first node in col
			if(n.getRow() == rows.get(i).getIndex()){
				return i;
			}
		}
		return -1;
	}
	
	private int findCol(ArrayList<ListNode> cols, Node n){
		for(int i = 0; i < cols.size(); i++){//find which row node corresponds to the first node in col
			if(n.getCol() == cols.get(i).getIndex()){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds a Node and the two Nodes before it in the row and column.
	 * @param row the row of the node to find
	 * @param col the column of the node to find
	 * @return An array of Nodes of size 3. The first element is the node itself,
	 * null if it isn't in the sparse array; the second element is the node before 
	 * it in the column, null if the node is the first in the column; the third
	 * element is the node before it in the row, null if the node is first in the
	 * row.
	 */
	private Node[] findNode(int row, int col) {
		Node[] returns = new Node[3];
		ArrayList<ListNode> columns = getColumns();
		
		for(ListNode c : columns){
			if(c.getIndex() == col){
				ArrayList<Node> column = columnList.get(c);
				for(int i = 0; i < column.size(); i++){
					if(column.get(i).getRow() == row){
						returns[0] = column.get(i);
						if(i > 0){
							returns[1] = column.get(i-1);
						}
					}
				}
			}
		}
		
		ArrayList<ListNode> rows = getRows();
		
		for(ListNode r : rows){
			if(r.getIndex() == row){
				ArrayList<Node> rowArray = rowList.get(r);
				for(int i = 0; i < rowArray.size(); i++){
					if(rowArray.get(i).getCol() == col){
						if(i > 0){
							returns[2] = rowArray.get(i-1);
						}
					}
				}
			}
		}
		
		return returns;
	}
	
	/**
	 * 
	 * @author John
	 *used to return three values in rowexists and colExists
	 */
	public class ThreeReturnValues {
		
		private Object first;
		private Object second;
		private Object third;
		
		public ThreeReturnValues(Object first, Object second,Object third) {
			this.first = first;
			this.second = second;
			this.third=third;
		}
		
		public Object getFirst() {
			return first;
		}
		
		public Object getSecond() {
			return second;
		}
		public Object getThird(){
			return third;
		}
	}
	/**
	 * this animates finding if the column is currently in existance.
	 * @param col a column to find
	 * @return if the column will be the new head, the current list node, the previous list node.
	 */
	private ThreeReturnValues colExists(int col){
		ArrayList<ListNode> cols = getColumns();
		ListNode prev=null;
		Boolean isHead=true;
		vis.display("Checking to se if the row exists.");
		for(ListNode node : cols){
			if(prev!=null){
				prev.setColor(FG);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
				prev.setListLineColor(FOCUS);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
				prev.setListLineColor(FG);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
			}
			node.setColor(FOCUS);
			update(getGraphics());
			timer=new AnimationTimer();
			try {
				timer.getThread().join();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer.stop();
			if(node.getIndex() == col){
				vis.display("This is the correct column.");
				return new ThreeReturnValues(isHead,node,prev);
			}
			if(node.getIndex()>col){
				if(prev!=null){
					vis.display("The previous List Node we checked came before the one we will insert.");
					prev.setColor(FOCUS);	
					update(getGraphics());
					timer=new AnimationTimer();
					try {
						timer.getThread().join();
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					timer.stop();
				}
				vis.display("We will keep a refernece to this List Node for when we add our new List Node because the column does not exist.");
				node.setColor(FG);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
				return new ThreeReturnValues(isHead,null,prev);
			}
			
			prev=node;
			isHead=false;
		}
		vis.display("The column does not exists");
		return new ThreeReturnValues(isHead,null,prev);
	}
	/**
	 * This animates searching to find if the row is currently in existance.
	 * @param row a row to find
	 * @return if the row will be the new head, the current list node, the previous list node.
	 */
	private ThreeReturnValues rowExists(int row){
		ArrayList<ListNode> rows = getRows();
		ListNode prev=null;
		Boolean isHead=true;
		vis.display("Checking to se if the row exists.");
		for(ListNode node : rows){
			if(prev!=null){
				prev.setColor(FG);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
				prev.setListLineColor(FOCUS);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
				prev.setListLineColor(FG);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
			}
			node.setColor(FOCUS);
			update(getGraphics());
			timer=new AnimationTimer();
			try {
				timer.getThread().join();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer.stop();
			if(node.getIndex() == row){
				vis.display("This is the correct row.");
				return new ThreeReturnValues(isHead,node,prev);
			}
			if(node.getIndex()>row){
				if(prev!=null){
					vis.display("The previous List Node we checked came before the one we will insert.");
					prev.setColor(FOCUS);		
					update(getGraphics());
					timer=new AnimationTimer();
					try {
						timer.getThread().join();
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					timer.stop();
				}
				vis.display("We will keep a refernece to this List Node for when we add our new List Node because the row does not exist.");
				node.setColor(FG);
				update(getGraphics());
				timer=new AnimationTimer();
				try {
					timer.getThread().join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				timer.stop();
				return new ThreeReturnValues(isHead,null,prev);
			}
			

			prev=node;
			isHead=false;
		}
		vis.display("The row does not exists");
		return new ThreeReturnValues(isHead,null,prev);
	}
	
	
	
	

	
	private boolean lastInCol(Node node) {
		ArrayList<ListNode> columns = getColumns();
		
		for(ListNode c : columns){
			if(c.getIndex() == node.getCol()){
				ArrayList<Node> column = columnList.get(c);
				return column.get(column.size()-1) == node;
			}
		}
		return false;
	}
	
	private boolean lastInRow(Node node) {
		ArrayList<ListNode> rows = getRows();
		
		for(ListNode r : rows){
			if(r.getIndex() == node.getRow()){
				ArrayList<Node> row = rowList.get(r);
				return row.get(row.size()-1) == node;
			}
		}
		return false;
	}

	
	public void setSpeed(int s){
		speed = s;
	}
	private void fgAll(){
		ArrayList<ListNode> rows = getRows();
		ArrayList<ListNode> columns = getColumns();
		
		for(ListNode row : rows){
			row.setColor(FG);
			row.setNodeLC(FG);
			row.setListLC(FG);
			ArrayList<Node> nodes = rowList.get(row);
			for(Node n : nodes){
				n.setColor(FG);
				n.setRightLC(FG);
			}
			nodes.get(nodes.size()-1).setRightLC(BG);
		}
		if(rows.size()!=0)rows.get(rows.size()-1).setListLC(BG);
		
		for(ListNode col : columns){
			col.setColor(FG);
			col.setNodeLC(FG);
			col.setListLC(FG);
			ArrayList<Node> nodes = columnList.get(col);
			for(Node n : nodes){
				n.setColor(FG);
				n.setBottomLC(FG);
			}
			nodes.get(nodes.size()-1).setBottomLC(BG);
		}
		if(columns.size()!=0)columns.get(columns.size()-1).setListLC(BG);
		update(getGraphics());
	}
	private int getStringWidth(String data, Graphics g) {
		Rectangle r = g.getFontMetrics().getStringBounds(data, g).getBounds();
		return r.width;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ArrayList<ListNode> columns = getColumns();
		ArrayList<ListNode> rows = getRows();
		
		int x = INIT_COL_X;
		for(int i = 0; i < columns.size(); i++){
			ListNode colNode = columns.get(i);
			g.setColor(colNode.getColor());//get the color of the ListNode
			g.drawRect(x, COL_Y, LIST_NODE_WIDTH, LIST_NODE_HEIGHT);//draw the rectangle around it
			String s = ""+colNode.getIndex();
			g.drawString(s, x+(LIST_NODE_WIDTH-getStringWidth(s,g))/2, LIST_NODE_HEIGHT+COL_Y-3);//draw index as a string
			//draw line connecting to next node in columnList
			g.setColor(colNode.getListLC());
			g.drawLine(x+LIST_NODE_WIDTH+1, LIST_NODE_HEIGHT/2+COL_Y,
					   x+STD_X_DIST       , LIST_NODE_HEIGHT/2+COL_Y);
			
			//get list of nodes in the column pointed to by colNode
			ArrayList<Node> col = columnList.get(colNode);
			if(col != null && col.size() != 0){
				//draw line connecting to first node
				g.setColor(colNode.getNodeLC());
				int r = findRow(rows, col.get(0));//finds the index of the ListNode of the row
				if(colNode.getNodeLineColor() != BG){
					g.drawLine(x+LIST_NODE_WIDTH/2, COL_Y+LIST_NODE_HEIGHT+1,
							   x+LIST_NODE_WIDTH/2, INIT_ROW_Y+r*STD_Y_DIST);
				}
						   
				
				for(int j = 0; j < col.size(); j++){
					r = findRow(rows, col.get(j));
					g.setColor(col.get(j).getColor());
					g.drawRect(x, INIT_ROW_Y+STD_Y_DIST*r, NODE_WIDTH, NODE_HEIGHT);
					g.drawString(col.get(j).getData(),
							x+2+(NODE_WIDTH-getStringWidth(col.get(j).getData(), g))/2,
							NODE_HEIGHT+INIT_ROW_Y+STD_Y_DIST*r-4);
					if(j < col.size()-1){
						int r2 = findRow(rows, col.get(j+1));
						g.setColor(col.get(j).getBottomLC());
						if(g.getColor() != BG){
							g.drawLine(x+LIST_NODE_WIDTH/2, INIT_ROW_Y+STD_Y_DIST*r+NODE_HEIGHT+1,
									   x+LIST_NODE_WIDTH/2, INIT_ROW_Y+STD_Y_DIST*r2);
						}
					}
				}
			}
			x+=STD_X_DIST;//increment column length
		}
		
		int y = INIT_ROW_Y;
		for(int i = 0; i < rows.size(); i++){
			ListNode rowNode = rows.get(i);
			g.setColor(rowNode.getColor());//get the color of the ListNode
			g.drawRect(ROW_X, y, LIST_NODE_WIDTH, LIST_NODE_HEIGHT);//draw the rectangle around it
			//draw index as a string
			String s = ""+rowNode.getIndex();
			g.drawString(s, ROW_X-1+(LIST_NODE_WIDTH-getStringWidth(s,g))/2, y+LIST_NODE_HEIGHT-3);
			//draw line connecting to next node in rowList
			g.setColor(rowNode.getListLC());
			g.drawLine((LIST_NODE_WIDTH+ROW_X)/2, y+LIST_NODE_HEIGHT+1,
					   (LIST_NODE_WIDTH+ROW_X)/2, y+STD_Y_DIST);
			//draw line connecting to first node
			g.setColor(rowNode.getNodeLC());
			//get list of nodes in the row pointed to by rowNode
			ArrayList<Node> row = rowList.get(rowNode);
			if(row != null && row.size() != 0){
				int c = findCol(columns, row.get(0));//finds the index of the ListNode of the column
				if(rowNode.getNodeLineColor() != BG){
				g.drawLine(ROW_X+LIST_NODE_WIDTH+1,   y+LIST_NODE_HEIGHT/2,
						   INIT_COL_X+c*STD_X_DIST-1, y+LIST_NODE_HEIGHT/2);
				}
				
				for(int j = 0; j < row.size(); j++){
					c = findCol(columns, row.get(j));
					if(j < row.size()-1){
						int c2 = findCol(columns, row.get(j+1));
						g.setColor(row.get(j).getRightLC());
						if(g.getColor() != BG){
							g.drawLine(INIT_COL_X+STD_X_DIST*c+NODE_WIDTH+1, y+NODE_HEIGHT/2,
									   INIT_COL_X+STD_X_DIST*c2-1, y+NODE_HEIGHT/2);
						}
					}
				}
			}
			
			y+=STD_Y_DIST;//increment row length
		}
	}
}
