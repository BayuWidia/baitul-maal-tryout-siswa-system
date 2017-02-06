package controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zhtml.Big;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import connection.DbConnection;
import model.dao.HasilTryOutSiswaDAO;
import model.dao.KelolaSiswaDAO;
import model.C45Algorithm;
import model.dao.C45AlgorithmDAO;
import model.dto.KelolaSiswaDTO;
import util.CustomeUtil;

public class KelolaDataSiswaController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Window winKelolaDataSiswa;

	Bandbox bdxNis;

	Textbox txtSearchNis;
	Textbox txtNamaSiswa;
	Textbox txtStatus1;
	Textbox txtStatus2;
	Textbox txtStatus3;
	Textbox txtStatus4;
	Textbox txtStatus5;
	// Textbox txtNilaiKlasifikasi;
	Textbox txtKlasifikasi;
	Textbox txtTahun;

	Listbox lbxNis;

	Decimalbox dcxMtk1;
	Decimalbox dcxInd1;
	Decimalbox dcxIpa1;
	Decimalbox dcxIng1;
	Decimalbox dcxrata1;

	Decimalbox dcxMtk2;
	Decimalbox dcxInd2;
	Decimalbox dcxIpa2;
	Decimalbox dcxIng2;
	Decimalbox dcxrata2;

	Decimalbox dcxMtk3;
	Decimalbox dcxInd3;
	Decimalbox dcxIpa3;
	Decimalbox dcxIng3;
	Decimalbox dcxrata3;

	Decimalbox dcxMtk4;
	Decimalbox dcxInd4;
	Decimalbox dcxIpa4;
	Decimalbox dcxIng4;
	Decimalbox dcxrata4;

	Decimalbox dcxMtk5;
	Decimalbox dcxInd5;
	Decimalbox dcxIpa5;
	Decimalbox dcxIng5;
	Decimalbox dcxrata5;

	Button btnCariNis;
	Button btnView;
	Button btnSave;
	Button btnClear;

	private List<KelolaSiswaDTO> lsNis;

	KelolaSiswaDAO kelSiswaDAO = new KelolaSiswaDAO(DbConnection.getDBConnection());
	C45AlgorithmDAO c45AlgortithmDAO = new C45AlgorithmDAO(DbConnection.getDBConnection());

	ListModelList<KelolaSiswaDTO> modelKelSiswa = new ListModelList<KelolaSiswaDTO>();

	HasilTryOutSiswaDAO hslTryOoutSiswaDAO = new HasilTryOutSiswaDAO(DbConnection.getDBConnection());

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initComponents();
	}

	private void initComponents() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		txtTahun.setValue(Integer.toString(year));
		btnSave.setDisabled(true);
	}

	public void onClick$btnClear() {
		bdxNis.setValue("");
		txtNamaSiswa.setValue("");
		txtSearchNis.setValue("");
		txtStatus1.setValue("");
		txtStatus2.setValue("");
		txtStatus3.setValue("");
		txtStatus4.setValue("");
		txtStatus5.setValue("");
		// txtNilaiKlasifikasi.setValue("");
		txtKlasifikasi.setValue("");
		txtTahun.setValue("");
		dcxMtk1.setValue(BigDecimal.ZERO);
		dcxInd1.setValue(BigDecimal.ZERO);
		dcxIpa1.setValue(BigDecimal.ZERO);
		dcxIng1.setValue(BigDecimal.ZERO);
		dcxrata1.setValue(BigDecimal.ZERO);
		dcxMtk2.setValue(BigDecimal.ZERO);
		dcxInd2.setValue(BigDecimal.ZERO);
		dcxIpa2.setValue(BigDecimal.ZERO);
		dcxIng2.setValue(BigDecimal.ZERO);
		dcxrata2.setValue(BigDecimal.ZERO);
		dcxMtk3.setValue(BigDecimal.ZERO);
		dcxInd3.setValue(BigDecimal.ZERO);
		dcxIpa3.setValue(BigDecimal.ZERO);
		dcxIng3.setValue(BigDecimal.ZERO);
		dcxrata3.setValue(BigDecimal.ZERO);
		dcxMtk4.setValue(BigDecimal.ZERO);
		dcxInd4.setValue(BigDecimal.ZERO);
		dcxIpa4.setValue(BigDecimal.ZERO);
		dcxIng4.setValue(BigDecimal.ZERO);
		dcxrata4.setValue(BigDecimal.ZERO);
		dcxMtk5.setValue(BigDecimal.ZERO);
		dcxInd5.setValue(BigDecimal.ZERO);
		dcxIpa5.setValue(BigDecimal.ZERO);
		dcxIng5.setValue(BigDecimal.ZERO);
		dcxrata5.setValue(BigDecimal.ZERO);
		btnSave.setDisabled(true);
		disableFalseComponents();
	}

	public void disableFalseComponents() {
		bdxNis.setDisabled(false);
		dcxMtk1.setReadonly(false);
		dcxInd1.setReadonly(false);
		dcxIpa1.setReadonly(false);
		dcxIng1.setReadonly(false);
		dcxMtk2.setReadonly(false);
		dcxInd2.setReadonly(false);
		dcxIpa2.setReadonly(false);
		dcxIng2.setReadonly(false);
		dcxMtk3.setReadonly(false);
		dcxInd3.setReadonly(false);
		dcxIpa3.setReadonly(false);
		dcxIng3.setReadonly(false);
		dcxMtk4.setReadonly(false);
		dcxInd4.setReadonly(false);
		dcxIpa4.setReadonly(false);
		dcxIng4.setReadonly(false);
		dcxMtk5.setReadonly(false);
		dcxInd5.setReadonly(false);
		dcxIpa5.setReadonly(false);
		dcxIng5.setReadonly(false);
		btnView.setDisabled(false);
	}

	public void disableTrueComponents() {
		bdxNis.setDisabled(true);
		dcxMtk1.setReadonly(true);
		dcxInd1.setReadonly(true);
		dcxIpa1.setReadonly(true);
		dcxIng1.setReadonly(true);
		dcxMtk2.setReadonly(true);
		dcxInd2.setReadonly(true);
		dcxIpa2.setReadonly(true);
		dcxIng2.setReadonly(true);
		dcxMtk3.setReadonly(true);
		dcxInd3.setReadonly(true);
		dcxIpa3.setReadonly(true);
		dcxIng3.setReadonly(true);
		dcxMtk4.setReadonly(true);
		dcxInd4.setReadonly(true);
		dcxIpa4.setReadonly(true);
		dcxIng4.setReadonly(true);
		dcxMtk5.setReadonly(true);
		dcxInd5.setReadonly(true);
		dcxIpa5.setReadonly(true);
		dcxIng5.setReadonly(true);
		btnView.setDisabled(true);
	}

	public void onClick$dcxMtk1() {
		dcxMtk1.setSelectionRange(0, 50);
	}

	public void onChange$dcxMtk1() {
		if (dcxMtk1.getValue() == null) {
			dcxMtk1.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk1.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxMtk1.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk1.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxMtk1.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxInd1() {
		dcxInd1.setSelectionRange(0, 50);
	}

	public void onChange$dcxInd1() {
		if (dcxInd1.getValue() == null) {
			dcxInd1.setValue(BigDecimal.ZERO);
		}
		if (dcxInd1.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxInd1.setValue(BigDecimal.ZERO);
		}
		if (dcxInd1.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxInd1.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIpa1() {
		dcxIpa1.setSelectionRange(0, 50);
	}

	public void onChange$dcxIpa1() {
		if (dcxIpa1.getValue() == null) {
			dcxIpa1.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa1.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIpa1.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa1.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIpa1.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIng1() {
		dcxIng1.setSelectionRange(0, 50);
	}

	public void onChange$dcxIng1() {
		if (dcxIng1.getValue() == null) {
			dcxIng1.setValue(BigDecimal.ZERO);
		}
		if (dcxIng1.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIng1.setValue(BigDecimal.ZERO);
		}
		if (dcxIng1.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIng1.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxMtk2() {
		dcxMtk2.setSelectionRange(0, 50);
	}

	public void onChange$dcxMtk2() {
		if (dcxMtk2.getValue() == null) {
			dcxMtk2.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk2.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxMtk2.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk2.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxMtk2.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxInd2() {
		dcxInd2.setSelectionRange(0, 50);
	}

	public void onChange$dcxInd2() {
		if (dcxInd2.getValue() == null) {
			dcxInd2.setValue(BigDecimal.ZERO);
		}
		if (dcxInd2.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxInd2.setValue(BigDecimal.ZERO);
		}
		if (dcxInd2.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxInd2.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIpa2() {
		dcxIpa2.setSelectionRange(0, 50);
	}

	public void onChange$dcxIpa2() {
		if (dcxIpa2.getValue() == null) {
			dcxIpa2.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa2.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIpa2.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa2.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIpa2.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIng2() {
		dcxIng2.setSelectionRange(0, 50);
	}

	public void onChange$dcxIng2() {
		if (dcxIng2.getValue() == null) {
			dcxIng2.setValue(BigDecimal.ZERO);
		}
		if (dcxIng2.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIng2.setValue(BigDecimal.ZERO);
		}
		if (dcxIng2.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIng2.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxMtk3() {
		dcxMtk3.setSelectionRange(0, 50);
	}

	public void onChange$dcxMtk3() {
		if (dcxMtk3.getValue() == null) {
			dcxMtk3.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk3.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxMtk3.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk3.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxMtk3.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxInd3() {
		dcxInd3.setSelectionRange(0, 50);
	}

	public void onChange$dcxInd3() {
		if (dcxInd3.getValue() == null) {
			dcxInd3.setValue(BigDecimal.ZERO);
		}
		if (dcxInd3.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxInd3.setValue(BigDecimal.ZERO);
		}
		if (dcxInd3.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxInd3.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIpa3() {
		dcxIpa3.setSelectionRange(0, 50);
	}

	public void onChange$dcxIpa3() {
		if (dcxIpa3.getValue() == null) {
			dcxIpa3.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa3.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIpa3.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa3.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIpa3.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIng3() {
		dcxIng3.setSelectionRange(0, 50);
	}

	public void onChange$dcxIng3() {
		if (dcxIng3.getValue() == null) {
			dcxIng3.setValue(BigDecimal.ZERO);
		}
		if (dcxIng3.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIng3.setValue(BigDecimal.ZERO);
		}
		if (dcxIng3.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIng3.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxMtk4() {
		dcxMtk4.setSelectionRange(0, 50);
	}

	public void onChange$dcxMtk4() {
		if (dcxMtk4.getValue() == null) {
			dcxMtk4.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk4.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxMtk4.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk4.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxMtk4.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxInd4() {
		dcxInd4.setSelectionRange(0, 50);
	}

	public void onChange$dcxInd4() {
		if (dcxInd4.getValue() == null) {
			dcxInd4.setValue(BigDecimal.ZERO);
		}
		if (dcxInd4.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxInd4.setValue(BigDecimal.ZERO);
		}
		if (dcxInd4.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxInd4.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIpa4() {
		dcxIpa4.setSelectionRange(0, 50);
	}

	public void onChange$dcxIpa4() {
		if (dcxIpa4.getValue() == null) {
			dcxIpa4.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa4.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIpa4.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa4.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIpa4.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIng4() {
		dcxIng4.setSelectionRange(0, 50);
	}

	public void onChange$dcxIng4() {
		if (dcxIng4.getValue() == null) {
			dcxIng4.setValue(BigDecimal.ZERO);
		}
		if (dcxIng4.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIng4.setValue(BigDecimal.ZERO);
		}
		if (dcxIng4.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIng4.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxMtk5() {
		dcxMtk5.setSelectionRange(0, 50);
	}

	public void onChange$dcxMtk5() {
		if (dcxMtk5.getValue() == null) {
			dcxMtk5.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk5.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxMtk5.setValue(BigDecimal.ZERO);
		}
		if (dcxMtk5.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxMtk5.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxInd5() {
		dcxInd5.setSelectionRange(0, 50);
	}

	public void onChange$dcxInd5() {
		if (dcxInd5.getValue() == null) {
			dcxInd5.setValue(BigDecimal.ZERO);
		}
		if (dcxInd5.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxInd5.setValue(BigDecimal.ZERO);
		}
		if (dcxInd5.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxInd5.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIpa5() {
		dcxIpa5.setSelectionRange(0, 50);
	}

	public void onChange$dcxIpa5() {
		if (dcxIpa5.getValue() == null) {
			dcxIpa5.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa5.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIpa5.setValue(BigDecimal.ZERO);
		}
		if (dcxIpa5.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIpa5.setValue(BigDecimal.ZERO);
		}
	}

	public void onClick$dcxIng5() {
		dcxIng5.setSelectionRange(0, 50);
	}

	public void onChange$dcxIng5() {
		if (dcxIng5.getValue() == null) {
			dcxIng5.setValue(BigDecimal.ZERO);
		}
		if (dcxIng5.getValue().compareTo(BigDecimal.ZERO) == -1) {
			dcxIng5.setValue(BigDecimal.ZERO);
		}
		if (dcxIng5.getValue().compareTo(new BigDecimal(100)) == 1) {
			dcxIng5.setValue(BigDecimal.ZERO);
		}
	}

	private void getNilai() {
		BigDecimal mtk1 = dcxMtk1.getValue();
		BigDecimal bind1 = dcxInd1.getValue();
		BigDecimal ipa1 = dcxIpa1.getValue();
		BigDecimal bing1 = dcxIng1.getValue();
		BigDecimal rata1 = (mtk1.add(bind1).add(ipa1).add(bing1)).divide(new BigDecimal(4));

		dcxrata1.setValue(rata1);
		if ((mtk1.compareTo(new BigDecimal(39)) == 1) && (bind1.compareTo(new BigDecimal(39)) == 1)
				&& (ipa1.compareTo(new BigDecimal(39)) == 1) && (bing1.compareTo(new BigDecimal(39)) == 1)
				&& (rata1.compareTo(new BigDecimal(54)) == 1)) {
			txtStatus1.setValue("Lulus");
		} else {
			txtStatus1.setValue("Belum Lulus");
		}

		// ==========================================================
		BigDecimal mtk2 = dcxMtk2.getValue();
		BigDecimal bind2 = dcxInd2.getValue();
		BigDecimal ipa2 = dcxIpa2.getValue();
		BigDecimal bing2 = dcxIng2.getValue();
		BigDecimal rata2 = (mtk2.add(bind2).add(ipa2).add(bing2)).divide(new BigDecimal(4));

		dcxrata2.setValue(rata2);
		if ((mtk2.compareTo(new BigDecimal(39)) == 1) && (bind2.compareTo(new BigDecimal(39)) == 1)
				&& (ipa2.compareTo(new BigDecimal(39)) == 1) && (bing2.compareTo(new BigDecimal(39)) == 1)
				&& (rata2.compareTo(new BigDecimal(54)) == 1)) {
			txtStatus2.setValue("Lulus");
		} else {
			txtStatus2.setValue("Belum Lulus");
		}

		// ==========================================================
		BigDecimal mtk3 = dcxMtk3.getValue();
		BigDecimal bind3 = dcxInd3.getValue();
		BigDecimal ipa3 = dcxIpa3.getValue();
		BigDecimal bing3 = dcxIng3.getValue();
		BigDecimal rata3 = (mtk3.add(bind3).add(ipa3).add(bing3)).divide(new BigDecimal(4));

		dcxrata3.setValue(rata3);
		if ((mtk3.compareTo(new BigDecimal(39)) == 1) && (bind3.compareTo(new BigDecimal(39)) == 1)
				&& (ipa3.compareTo(new BigDecimal(39)) == 1) && (bing3.compareTo(new BigDecimal(39)) == 1)
				&& (rata3.compareTo(new BigDecimal(54)) == 1)) {
			txtStatus3.setValue("Lulus");
		} else {
			txtStatus3.setValue("Belum Lulus");
		}

		// ==========================================================
		BigDecimal mtk4 = dcxMtk4.getValue();
		BigDecimal bind4 = dcxInd4.getValue();
		BigDecimal ipa4 = dcxIpa4.getValue();
		BigDecimal bing4 = dcxIng4.getValue();
		BigDecimal rata4 = (mtk4.add(bind4).add(ipa4).add(bing4)).divide(new BigDecimal(4));

		dcxrata4.setValue(rata4);
		if ((mtk4.compareTo(new BigDecimal(39)) == 1) && (bind4.compareTo(new BigDecimal(39)) == 1)
				&& (ipa4.compareTo(new BigDecimal(39)) == 1) && (bing4.compareTo(new BigDecimal(39)) == 1)
				&& (rata4.compareTo(new BigDecimal(54)) == 1)) {
			txtStatus4.setValue("Lulus");
		} else {
			txtStatus4.setValue("Belum Lulus");
		}

		// ==========================================================
		BigDecimal mtk5 = dcxMtk5.getValue();
		BigDecimal bind5 = dcxInd5.getValue();
		BigDecimal ipa5 = dcxIpa5.getValue();
		BigDecimal bing5 = dcxIng5.getValue();
		BigDecimal rata5 = (mtk5.add(bind5).add(ipa5).add(bing5)).divide(new BigDecimal(4));

		dcxrata5.setValue(rata5);
		if ((mtk5.compareTo(new BigDecimal(39)) == 1) && (bind5.compareTo(new BigDecimal(39)) == 1)
				&& (ipa5.compareTo(new BigDecimal(39)) == 1) && (bing5.compareTo(new BigDecimal(39)) == 1)
				&& (rata5.compareTo(new BigDecimal(54)) == 1)) {
			txtStatus5.setValue("Lulus");
		} else {
			txtStatus5.setValue("Belum Lulus");
		}

	}

	public void onClick$btnView() throws SQLException {
		if (isValidate()) {
			getNilai();
			getNilaiC45Algorithm();
			btnSave.setDisabled(false);
			disableTrueComponents();
		}
//		getNilai();
//		getNilaiC45Algorithm();
	}

	public void onClick$btnSave() {
		if (isValidate()) {
			try {
				prosesInsert();
				onClick$btnClear();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getNilaiC45Algorithm() {
		String dataClass = "KLASIFIKASI";

		List<Map<String, Object>> listDataMaster = c45AlgortithmDAO.findByC45Algoritm();

		Map<String, Object> kasus = getValComponents();

		List<String> listDelData = new ArrayList<>();

		List<Map<String, Object>> listData = new ArrayList<>(listDataMaster);
		List<Map<String, Object>> listResult = new ArrayList<>();

		System.out.println("TIME BEGIN : " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " - "
				+ Calendar.getInstance().get(Calendar.MILLISECOND));

		int step = 1;
		boolean printTabel = false;

		System.out.println("TIME START : " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " - "
				+ Calendar.getInstance().get(Calendar.MILLISECOND));

		// baru
		List<String> listIndex = new ArrayList<>();
		List<Map<String, Object>> allLisResultnew = new ArrayList();
		Map<String, Object> mapDataListIndex = new LinkedHashMap<>();
		mapDataListIndex.put("0", listData);
		String index = "0";
		listIndex.add(index);
		C45Algorithm algo = new C45Algorithm();
		algo.getAllTableAndValue(kasus, dataClass, listDelData, step, printTabel, allLisResultnew, index,
				mapDataListIndex, listIndex);
		String strResult = algo.getResultValue(allLisResultnew, kasus);
		txtKlasifikasi.setValue(strResult);
		
		System.out.println("LIST ALL RESULT : " + allLisResultnew);
		System.out.println("TREE : " + listResult);
		System.out.println("TIME END : " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + " - "
				+ Calendar.getInstance().get(Calendar.MILLISECOND));

		// txtKlasifikasi.setValue(strResult);

	}

	public Map<String, Object> getValComponents() {
		Map<String, Object> mapData = new LinkedHashMap<>();
		mapData.put("KETERANGAN_1", txtStatus1.getValue().toString().trim());
		mapData.put("KETERANGAN_2", txtStatus2.getValue().toString().trim());
		mapData.put("KETERANGAN_3", txtStatus3.getValue().toString().trim());
		mapData.put("KETERANGAN_4", txtStatus4.getValue().toString().trim());
		mapData.put("KETERANGAN_5", txtStatus5.getValue().toString().trim());
		return mapData;
	}

	private void prosesInsert() throws SQLException {
		String nis = bdxNis.getValue();
		Double matematika_1 = dcxMtk1.getValue().doubleValue();
		Double b_indo_1 = dcxInd1.getValue().doubleValue();
		Double ipa_1 = dcxIpa1.getValue().doubleValue();
		Double b_ing_1 = dcxIng1.getValue().doubleValue();
		Double rata_rata_1 = dcxrata1.getValue().doubleValue();
		String keterangan_1 = txtStatus1.getValue().toString().trim();

		Double matematika_2 = dcxMtk2.getValue().doubleValue();
		Double b_indo_2 = dcxInd2.getValue().doubleValue();
		Double ipa_2 = dcxIpa2.getValue().doubleValue();
		Double b_ing_2 = dcxIng2.getValue().doubleValue();
		Double rata_rata_2 = dcxrata2.getValue().doubleValue();
		String keterangan_2 = txtStatus2.getValue().toString().trim();

		Double matematika_3 = dcxMtk3.getValue().doubleValue();
		Double b_indo_3 = dcxInd3.getValue().doubleValue();
		Double ipa_3 = dcxIpa3.getValue().doubleValue();
		Double b_ing_3 = dcxIng3.getValue().doubleValue();
		Double rata_rata_3 = dcxrata3.getValue().doubleValue();
		String keterangan_3 = txtStatus3.getValue().toString().trim();

		Double matematika_4 = dcxMtk4.getValue().doubleValue();
		Double b_indo_4 = dcxInd4.getValue().doubleValue();
		Double ipa_4 = dcxIpa4.getValue().doubleValue();
		Double b_ing_4 = dcxIng4.getValue().doubleValue();
		Double rata_rata_4 = dcxrata4.getValue().doubleValue();
		String keterangan_4 = txtStatus4.getValue().toString().trim();

		Double matematika_5 = dcxMtk5.getValue().doubleValue();
		Double b_indo_5 = dcxInd5.getValue().doubleValue();
		Double ipa_5 = dcxIpa5.getValue().doubleValue();
		Double b_ing_5 = dcxIng5.getValue().doubleValue();
		Double rata_rata_5 = dcxrata5.getValue().doubleValue();
		String keterangan_5 = txtStatus5.getValue().toString().trim();

		String klasifikasi = txtKlasifikasi.getValue().trim();
		Integer tahun = new Integer(txtTahun.getValue());

		KelolaSiswaDAO kelSisDAO = new KelolaSiswaDAO(DbConnection.getDBConnection());
		kelSisDAO.insertKelolaSiswa(nis, matematika_1, b_indo_1, ipa_1, b_ing_1, rata_rata_1, keterangan_1,
				matematika_2, b_indo_2, ipa_2, b_ing_2, rata_rata_2, keterangan_2, matematika_3, b_indo_3, ipa_3,
				b_ing_3, rata_rata_3, keterangan_3, matematika_4, b_indo_4, ipa_4, b_ing_4, rata_rata_4, keterangan_4,
				matematika_5, b_indo_5, ipa_5, b_ing_5, rata_rata_5, keterangan_5, klasifikasi, tahun);
		CustomeUtil.infoWithTitle("Data berhasil disimpan", "Informasi");
	}

	private boolean isValidate() {
		Boolean bol = true;
		String strMessage = "";

		if (bdxNis.getValue().isEmpty() || dcxMtk1.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxInd1.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIpa1.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIng1.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxMtk2.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxInd2.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIpa2.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIng2.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxMtk3.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxInd3.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIpa3.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIng3.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxMtk4.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxInd4.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIpa4.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIng4.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxMtk5.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxInd5.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIpa5.getValue().compareTo(BigDecimal.ZERO) == 0
				|| dcxIng5.getValue().compareTo(BigDecimal.ZERO) == 0) {
			strMessage += "Mandatory harus diisikan";
		}

		if (!strMessage.isEmpty()) {
			alert(strMessage);
			bol = false;
		}
		return bol;
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
		String nip = parts[0];
		String nama = parts[1];

		bdxNis.setValue(nip.toString());
		txtNamaSiswa.setValue(nama.toString());
		bdxNis.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClick$btnCariNis() {
		String search = txtSearchNis.getValue().trim();
		modelKelSiswa = new ListModelList<KelolaSiswaDTO>(kelSiswaDAO.findSearch(search));
		lsNis = modelKelSiswa;
		lbxNis.setModel(new ListModelList(lsNis, true));
	}

	public void onOK$txtSearchNis() {
		onClick$btnCariNis();
	}

}
