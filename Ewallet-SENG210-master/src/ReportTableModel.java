import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ReportTableModel extends AbstractTableModel {

	
	String[][] data;
	
	String[] columnNames;
	
	public String getColumnName(int col) {
        return columnNames[col].toString();
    }
	
	public ReportTableModel(String[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

}
