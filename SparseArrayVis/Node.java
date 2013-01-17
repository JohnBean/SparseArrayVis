import java.awt.Color;

public class Node implements Comparable<Node>{
	private int row, col;
	private String data;
	private Color color,bottomLC,rightLC;
	
	public Node(int row, int col,String data){
		this.row = row;
		this.col = col;
		this.data = new String(data);
		this.color=Color.white;
		this.bottomLC=Color.white;
		this.rightLC=Color.white;
	}
	public void setRow(int row){
		this.row = row;
	}
	public void setCol(int col){
		this.col = col;
	}
	public void setData(String data){
		this.data = data;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}
	public String getData(){
		return new String(data);
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public Color getRightLC() {
		return rightLC;
	}
	public Color getRightLineColor() {
		return rightLC;
	}
	public void setRightLC(Color color) {
		this.rightLC =color;
	}
	public void setRightLineColor(Color color) {
		this.rightLC =color;
	}
	public Color getBottomLC() {
		return bottomLC;
	}
	public Color getBottomLineColor() {
		return bottomLC;
	}
	public void setBottomLC(Color color) {
		this.bottomLC =color;
	}
	public void setBottomLineColor(Color color) {
		this.bottomLC =color;
	}
	/**
	 * implementing compareable
	 */
	public int compareTo(Node node){
		if(col==node.getCol()){
			return row-node.getRow();
		}
		else if(row==node.getRow()){
			return col-node.getCol();
		}
		return 0;
	}
	public String toString(){
		return "r:"+row+" c:"+col+" data:"+data;
	}
}
