package controller;

import java.sql.SQLException;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import connection.DbConnection;
import model.dao.HistoryTryOutSiswaDAO;
import model.dao.KelolaSiswaDAO;
import model.dto.HasilTryOutSiswaDTO;
import model.dto.KelolaSiswaDTO;
import util.CustomeUtil;



public class HistoryTryOutSiswaController extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Window winHistoryTryOutSiswa;
	
	Textbox txtSearchNis;
	Textbox txtNamaSiswa;
	
	Button btnCariNis;
	
	Button btnSearch;
	Button btnClear;
	
	Bandbox bdxNis;
	
	Listbox lbxNis;
	Listbox lbxHistorySiswa;
	
	String nis;
	String nama;
	

	private ListModelList<HasilTryOutSiswaDTO> lmlHistorySiswa;
	
	private List<HasilTryOutSiswaDTO> lstDataHistorySiswa;
	
	private List<KelolaSiswaDTO> lsNis;

	KelolaSiswaDAO kelSiswaDAO = new KelolaSiswaDAO(DbConnection.getDBConnection());
	ListModelList<KelolaSiswaDTO> modelKelSiswa = new ListModelList<KelolaSiswaDTO>();
	
	HistoryTryOutSiswaDAO hslKinSiswaDAO = new HistoryTryOutSiswaDAO(DbConnection.getDBConnection());
	ListModelList<HasilTryOutSiswaDTO> lmlHslKinSiswaDTO = new ListModelList<HasilTryOutSiswaDTO>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initComponents();
	}

	private void initComponents() {
	}


	public void onClick$btnClear(){
		bdxNis.setValue("");
		txtNamaSiswa.setValue("");
		if (lmlHistorySiswa != null) {
			lmlHistorySiswa.clear();
			lstDataHistorySiswa.clear();
		}
	}
	
	public void onClick$btnSearch(){
		if (isValidate()) {
			String strNip= nis.toLowerCase().trim();
			lmlHslKinSiswaDTO = new ListModelList<HasilTryOutSiswaDTO>(hslKinSiswaDAO.findAll(strNip));
			lstDataHistorySiswa = lmlHslKinSiswaDTO;
			if (lstDataHistorySiswa.isEmpty()) {
				CustomeUtil.infoWithTitle("Data yang anda cari tidak ditemukan", "Informasi");
			}
			renderListBox(lstDataHistorySiswa);
		}
	}
	
	private void renderListBox(List<HasilTryOutSiswaDTO> lstDataHasilKinerja) {
		
		lmlHistorySiswa = new ListModelList<HasilTryOutSiswaDTO>(lstDataHasilKinerja);
		lbxHistorySiswa.setModel(lmlHistorySiswa);
		lbxHistorySiswa.setItemRenderer(new ListitemRenderer<Object>() {
			
			@Override
			public void render(Listitem li, Object data, int arg) throws Exception {
					
//				Map<String, Object> hasilkinerja = (Map<String, Object>) data;
				
				final HasilTryOutSiswaDTO hasil = (HasilTryOutSiswaDTO) data;
				
				Label lblTryOut1 = new Label(hasil.getKeterangan_1().toString());
				Label lblTryOut2 = new Label(hasil.getKeterangan_2().toString());
				Label lblTryOut3 = new Label(hasil.getKeterangan_3().toString());
				Label lblTryOut4 = new Label(hasil.getKeterangan_4().toString());
				Label lblTryOut5 = new Label(hasil.getKeterangan_5().toString());
				Label lblTahun = new Label(hasil.getTahun().toString());
				Label lblKlasifikasi = new Label(hasil.getStatus_nilai_siswa().toString());
				
				CustomeUtil.setObjectToListCell(li, lblTryOut1, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut2, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut3, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut4, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut5, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTahun, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblKlasifikasi, "text-align:center");
				
				li.setValue(hasil);
				}
			});
		}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onOpen$bdxNis() throws SQLException {
		txtSearchNis.focus();
		modelKelSiswa = new ListModelList<KelolaSiswaDTO>(kelSiswaDAO.findAll());
		lsNis = modelKelSiswa;
		lbxNis.setModel(new ListModelList(lsNis, true));
	}

	public void onSelect$lbxNis() {
		Listitem li = lbxNis.getSelectedItem();
		String strValue = "";
		for (Object cell : ((Listitem) li).getChildren()) {
			if (((Listcell) cell).getListheader() != null) {
				if (((Listcell) cell).getListheader().isVisible()) {
					strValue += ((Listcell) cell).getLabel() + ";";
				}
			}
		}
		String[] parts = strValue.split(";");
		nis = parts[0];
		nama = parts[1];

		System.out.println("===========>> HAHAHA 3: " + nis);
		System.out.println("===========>> HAHAHA 4: " + nama);
		
		bdxNis.setValue(nis.toString());
		txtNamaSiswa.setValue(nama.toString());
		bdxNis.close();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btnCariNis(){
		String search = txtSearchNis.getValue().trim();
		modelKelSiswa = new ListModelList<KelolaSiswaDTO>(kelSiswaDAO.findSearch(search));
		lsNis = modelKelSiswa;
		lbxNis.setModel(new ListModelList(lsNis, true));
	}
	
	public void onOK$txtSearchNis(){
		onClick$btnCariNis();
	}
	
	private boolean isValidate() {
		Boolean bol = true;
		String strMessage = "";

		if (bdxNis.getValue().isEmpty()) {
			strMessage += "Mandatory harus diisikan";
		}

		if (!strMessage.isEmpty()) {
			alert(strMessage);
			bol = false;
		}
		return bol;
	}
}
