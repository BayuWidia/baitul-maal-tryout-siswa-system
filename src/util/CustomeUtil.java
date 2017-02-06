package util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;


public class CustomeUtil {
	public static String TEMPLATE_MESSAGE_BOX_PATH = "messagebox.zul";

	public static void setObjectToListCell(Listitem li, Object comp,
			String style) {
		Listcell lc = new Listcell();
		lc.setStyle(style);
		lc.appendChild((Component) comp);
		lc.setParent(li);
	}
	
	public static void infoWithTitle(String message, String title) {
		Messagebox.setTemplate(TEMPLATE_MESSAGE_BOX_PATH);
		Messagebox.show(removeTypeError(message), title, Messagebox.OK, Messagebox.INFORMATION,
				null);
	}
	
	public static Integer confirmIyaTidak(String message, String title) {
		Messagebox.setTemplate(TEMPLATE_MESSAGE_BOX_PATH);
		return Messagebox.show(removeTypeError(message), title, Messagebox.OK|Messagebox.CANCEL, Messagebox.QUESTION);
	}
	
	public static String removeTypeError(String message){
		if(message!=null){
			message=message.replace("Error Code", "Error");
		}
		return message;
	}

	public static <T> void render(Component compTarget, Listbox lbox,
			String[] colHeader, List<T> lsModelData, Boolean multiple,
			String[] strAction) {
		ListModelList<T> lsModel = new ListModelList<T>(lsModelData);
		render(compTarget, lbox, colHeader, lsModel, multiple, strAction);
	}
	
	public static void populateComboDecisionLabel(Combobox combo,
			Map<String, Object> lstStatus, BigDecimal selVal, boolean isNew,
			boolean incPilih) {
		if (isNew) {
			if (combo.getChildren() != null) {
				combo.getChildren().clear();
			}
		}
		if (incPilih) {
			combo.appendItem("--PILIH--").setValue("");
		}
		int idx = 0;
		int selIdx = 0;
		
		for (String m : lstStatus.keySet()) {
			
			String strLabel = (String) (m != null ? lstStatus.get(m) : "");
			String strValue = (m != null ? m : "");
			if (isNew) {
				combo.appendItem(strValue+" - "+strLabel).setValue(strLabel);
			}
			if (selVal != null) {
				if (selVal.toString().equalsIgnoreCase(strValue)) {
					selIdx = idx;
				}
			}
			idx++;
		} 
		combo.setSelectedIndex(selIdx);
	}
	
	
	public static void populateComboDecisionId(Combobox combo,
			Map<String, Object> lstStatus, BigDecimal selVal, boolean isNew,
			boolean incPilih) {
		if (isNew) {
			if (combo.getChildren() != null) {
				combo.getChildren().clear();
			}
		}
		if (incPilih) {
			combo.appendItem("--PILIH--").setValue("");
		}
		int idx = 0;
		int selIdx = 0;
		
		for (String m : lstStatus.keySet()) {
			
			String strLabel = (String) (m != null ? lstStatus.get(m) : "");
			String strValue = (m != null ? m : "");
			if (isNew) {
				combo.appendItem(strValue+" - "+strLabel).setValue(strValue);
			}
			if (selVal != null) {
				if (selVal.toString().equalsIgnoreCase(strValue)) {
					selIdx = idx;
				}
			}
			idx++;
		} 
		combo.setSelectedIndex(selIdx);
	}
	
}
