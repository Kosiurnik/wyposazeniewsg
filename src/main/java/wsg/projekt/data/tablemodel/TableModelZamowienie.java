package wsg.projekt.data.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wsg.projekt.data.entity.EntitySprzet;
import wsg.projekt.data.entity.EntityZamowienie;
import wsg.projekt.data.repository.RepositoryZamowienie;

public class TableModelZamowienie extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<EntityZamowienie> zamowienia;

	private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("wyposazeniewsg-context.xml");
	private RepositoryZamowienie repository = context.getBean("RepositoryZamowienie",RepositoryZamowienie.class);
	
	public TableModelZamowienie(){
		super();
		this.zamowienia = repository.getAllZamowienie();
	}
	
	public int getColumnCount() {
		return 7;
	}

	public int getRowCount() {
		return zamowienia.size();
	}

    public Object getValueAt(int rowIndex, int columnIndex)
    {
    	EntityZamowienie z=zamowienia.get(rowIndex);
        Object[] values=new Object[]{z.getZamowienieID(),z.getWykladowca().getImie()+" "+z.getWykladowca().getNazwisko(),z.getSala().getNumerSali(),
        		sprzetyListString(z.getSprzety()),z.getDataStart(),z.getRodzajZajec(),z.getUwagi()};
        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column)
    {
        String[] columnNames=new String[]{"ID","Wykładowca","Sala","Sprzęty","Data","Rodzaj Zajęć","Uwagi"};
        return columnNames[column];
    }
    
    private String sprzetyListString(List<EntitySprzet> sprzety){
    	if(sprzety.size()!=0){
    		String[] sprzetyString = new String[sprzety.size()];
    		int index = 0;
    		for(EntitySprzet sprzet : sprzety){
    			sprzetyString[index]=sprzet.getNazwa();
    			index++;
    		}
    		return String.join(", ", sprzetyString);
    	}else
    		return "brak";
    }

}
