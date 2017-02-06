package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import connection.DbConnection;
import model.dao.HasilTryOutSiswaDAO;
import model.dao.StatusSiswaDAO;
import model.dto.HasilTryOutSiswaDTO;
import model.dto.StatusSiswaDTO;
import util.CustomeUtil;



public class HasilTryoutSiswaController extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Window winHasilSiswa;
	
	Combobox cmbKlasifikasi;
	
	Button btnSearch;
	Button btnClear;
		
	Listbox lbxHasilSiswa;
	

	private ListModelList<HasilTryOutSiswaDTO> lmlHasilSiswa;
	List<HasilTryOutSiswaDTO> lstDataHasilSiswa;
	List<StatusSiswaDTO> lstDataJumlahSiswa;
	
	HasilTryOutSiswaDAO hslTryOutSiswaDAO = new HasilTryOutSiswaDAO(DbConnection.getDBConnection());
	StatusSiswaDAO jmlKlasSiswaDAO = new StatusSiswaDAO(DbConnection.getDBConnection());
	
	ListModelList<HasilTryOutSiswaDTO> lmlHslTryOutSiswaDTO = new ListModelList<HasilTryOutSiswaDTO>();
	ListModelList<StatusSiswaDTO> lmlJumTryOutSiswaDTO = new ListModelList<StatusSiswaDTO>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initComponents();
	}

	private void initComponents() {
		getValCmbKriteriaKinerja();
	}

	private void getValCmbKriteriaKinerja() {
		Map<String, Object> lstKriteriaKinerja = new LinkedHashMap<String, Object>();
		lstKriteriaKinerja.put("1", "Siap");
		lstKriteriaKinerja.put("2", "Belum Siap");
		lstKriteriaKinerja.put("3", "Butuh Bantuan");
		CustomeUtil.populateComboDecisionLabel(cmbKlasifikasi, lstKriteriaKinerja, null, true, true);
	}
	
	
	public void onClick$btnClear(){
		cmbKlasifikasi.setSelectedIndex(0);
		if (lmlHasilSiswa != null) {
			lmlHasilSiswa.clear();
			lstDataHasilSiswa.clear();
		}
	}
	
	
	public static String getThn() {
        SimpleDateFormat sdfThn = new SimpleDateFormat("yyyy");
        Date dateThn = new Date();
        return sdfThn.format(dateThn);
    }
	
	public void onClick$btnSearch(){
		
		String statusNilaiSiswa= cmbKlasifikasi.getSelectedItem().getValue();
		
		lmlHslTryOutSiswaDTO = new ListModelList<HasilTryOutSiswaDTO>(hslTryOutSiswaDAO.findAll(statusNilaiSiswa));
		
//		lmlJumTryOutSiswaDTO = new ListModelList<StatusSiswaDTO>(jmlKlasSiswaDAO.findAll(statusNilaiSiswa));
		System.out.println("================>> lsDataJumlahKinerja" + lmlJumTryOutSiswaDTO);

		lstDataHasilSiswa = lmlHslTryOutSiswaDTO;
		if (lstDataHasilSiswa.isEmpty()) {
			CustomeUtil.infoWithTitle("Data yang anda cari tidak ditemukan", "Informasi");
		}

		renderListBox(lstDataHasilSiswa);
	}
	
	private void renderListBox(List<HasilTryOutSiswaDTO> lstDataHasilKinerja) {

		lmlHasilSiswa = new ListModelList<HasilTryOutSiswaDTO>(lstDataHasilKinerja);
		lbxHasilSiswa.setModel(lmlHasilSiswa);
		lbxHasilSiswa.setItemRenderer(new ListitemRenderer<Object>() {
			
			@Override
			public void render(Listitem li, Object data, int arg) throws Exception {
					
				
				final HasilTryOutSiswaDTO hasil = (HasilTryOutSiswaDTO) data;
				
				Label lblNis = new Label(hasil.getNis().toString());
				Label lblNama = new Label(hasil.getNama_siswa().toString());
				Label lblTryOut1 = new Label(hasil.getKeterangan_1().toString());
				Label lblTryOut2 = new Label(hasil.getKeterangan_2().toString());
				Label lblTryOut3 = new Label(hasil.getKeterangan_3().toString());
				Label lblTryOut4 = new Label(hasil.getKeterangan_4().toString());
				Label lblTryOut5 = new Label(hasil.getKeterangan_5().toString());
				Label lblKlasifikasi = new Label(hasil.getStatus_nilai_siswa().toString());
				
				CustomeUtil.setObjectToListCell(li, lblNis, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblNama, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut1, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut2, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut3, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut4, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblTryOut5, "text-align:center");
				CustomeUtil.setObjectToListCell(li, lblKlasifikasi, "text-align:center");
				
				li.setValue(hasil);
				}
			});
		}
}
