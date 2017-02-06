package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import connection.DbConnection;
import model.dao.ImportFileDAO;
import util.CustomeUtil;

public class ImportFileController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Window winImportFile;

	Textbox txtTahun;
	Textbox txtNamaFile;
	
	Button btnUpload;
	Button btnDowload;
	Button btnSave;
	Button btnClear;

	Datebox dtbTanggalUpload;
	
	InputStream inpStream = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		initComponents();
	}

	private void initComponents() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		txtTahun.setValue(Integer.toString(year));
		dtbTanggalUpload.setValue(new Date());
	}
	
	public void onUpload$btnUpload(UploadEvent evt){
		Media media = null;
		media = evt.getMedia();
		inpStream = media.getStreamData();
		txtNamaFile.setValue(media.getName().toString()); 
	}

	public void onClick$btnSave() {
		if (isValidate()) {
			try {
				ImportFileDAO impDAO = new ImportFileDAO(DbConnection.getDBConnection());
				impDAO.importExcel(inpStream);
				CustomeUtil.infoWithTitle("Data berhasil disimpan", "Informasi");
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onClick$btnDowload(){
		try {
			Filedownload.save("/asset/download/file_import.xlsx", null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isValidate() {
		Boolean bol = true;
		String strMessage = "";

		if (txtNamaFile.getValue().isEmpty()) {
			strMessage += "Mandatory harus diisikan";
		}

		if (!strMessage.isEmpty()) {
			alert(strMessage);
			bol = false;
		}
		return bol;
	}

}
