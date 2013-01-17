import java.awt.Color;

public class ListNode implements Comparable<ListNode>{
		private int index;
		private Color color,listLC,nodeLC;//LC means Line color
		/**
		 * @param index	row or column
		 */
		public ListNode(int index){
			this.color=Color.green;
			this.index=index;
			this.listLC=Color.white;
			this.nodeLC=Color.white;
		}
		public int getIndex() {
			return index;
		}
		public Color getColor(){
			return color;
		}
		public void setColor(Color color){
			this.color=color;	
		}
		@Override
		public int compareTo(ListNode node) {
			return index-node.getIndex();
		}
		public Color getListLC() {
			return listLC;
		}
		public Color getListLineColor() {
			return listLC;
		}
		public void setListLC(Color color) {
			this.listLC =color;
		}
		public void setListLineColor(Color color) {
			this.listLC =color;
		}
		public Color getNodeLC() {
			return nodeLC;
		}
		public Color getNodeLineColor() {
			return nodeLC;
		}
		public void setNodeLC(Color color) {
			this.nodeLC =color;
		}
		public void setNodeLineColor(Color color) {
			this.nodeLC =color;
		}
	}