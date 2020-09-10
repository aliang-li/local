package label.bean;



import java.util.List;

public class dcmSize {

	private int row;
	private int columns;
	private int size;
	
	private List<String> urlsList; //用来存储dcm文件的绝对路径
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<String> getUrlsList() {
		return urlsList;
	}
	public void setUrlsList(List<String> urlsList) {
		this.urlsList = urlsList;
	}
	@Override
	public String toString() {
		return "dcmSize [row=" + row + ", columns=" + columns + ", size=" + size + ", urlsList=" + urlsList + "]";
	}
	
}
