package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import connection.DbConnection;

public class ImportFileDAO {

	Connection con;
	PreparedStatement ps = null;

	public ImportFileDAO(Connection conn) {
		this.con = conn;

	}

	@SuppressWarnings("resource")
	public void importExcel(InputStream inpStream) throws SQLException, IOException, InvalidFormatException {

		Connection dbConnec = null;
		Statement state = null;

		try {
			// FILE XLS
//			 FileInputStream input = new
//			 FileInputStream("D://datasetimport.xls");
//			 System.out.println("====================>>" + input);
			 POIFSFileSystem fs = new POIFSFileSystem(inpStream);
			 HSSFWorkbook wb = new HSSFWorkbook(fs);
			 HSSFSheet sheet = wb.getSheetAt(0);
			 Row row;
			
			// FILE XLSX
//			OPCPackage pkg = OPCPackage.open(new File("D://FILE_DIKA.xlsx"));
//			XSSFWorkbook wb = new XSSFWorkbook(pkg);
//			XSSFSheet sheet = wb.getSheetAt(0);
//			XSSFRow row;
//			CellReference cr = new CellReference("A1");
//			row = sheet.getRow(cr.getCol());

			dbConnec = DbConnection.getDBConnection();
			state = dbConnec.createStatement();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				
				// getCell 0 dipakai nomor 
				String nis = row.getCell(1).getStringCellValue();
				// getCell 2 dipakai nama
				Double matematika_1 = row.getCell(3).getNumericCellValue(); 
				Double b_indo_1 = row.getCell(4).getNumericCellValue();  
				Double ipa_1 = row.getCell(5).getNumericCellValue(); 
				Double b_ing_1 = row.getCell(6).getNumericCellValue(); 
				Double rata_rata_1 = row.getCell(7).getNumericCellValue(); 
				String keterangan_1 = row.getCell(8).getStringCellValue(); 
				Double matematika_2 = row.getCell(9).getNumericCellValue(); 
				Double b_indo_2 = row.getCell(10).getNumericCellValue(); 
				Double ipa_2 = row.getCell(11).getNumericCellValue(); 
				Double b_ing_2 = row.getCell(12).getNumericCellValue(); 
				Double rata_rata_2 = row.getCell(13).getNumericCellValue(); 
				String keterangan_2 = row.getCell(14).getStringCellValue(); 
				Double matematika_3 = row.getCell(15).getNumericCellValue(); 
				Double b_indo_3 = row.getCell(16).getNumericCellValue(); 
				Double ipa_3 = row.getCell(17).getNumericCellValue(); 
				Double b_ing_3 = row.getCell(18).getNumericCellValue(); 
				Double rata_rata_3 = row.getCell(19).getNumericCellValue();  
				String keterangan_3 = row.getCell(20).getStringCellValue(); 
				Double matematika_4 = row.getCell(21).getNumericCellValue(); 
				Double b_indo_4 = row.getCell(22).getNumericCellValue(); 
				Double ipa_4 = row.getCell(23).getNumericCellValue(); 
				Double b_ing_4 = row.getCell(24).getNumericCellValue(); 
				Double rata_rata_4 = row.getCell(25).getNumericCellValue(); 
				String keterangan_4 = row.getCell(26).getStringCellValue(); 
				Double matematika_5 = row.getCell(27).getNumericCellValue(); 
				Double b_indo_5 = row.getCell(28).getNumericCellValue(); 
				Double ipa_5 = row.getCell(29).getNumericCellValue(); 
				Double b_ing_5 = row.getCell(30).getNumericCellValue(); 
				Double rata_rata_5 = row.getCell(31).getNumericCellValue(); 
				String keterangan_5 = row.getCell(32).getStringCellValue(); 
				String klasifikasi = row.getCell(33).getStringCellValue(); 
				
				int year = Calendar.getInstance().get(Calendar.YEAR);
				Integer tahun = year;

				String insUpdTbl = "insert into nilai_try_out"
						+ "(nis, matematika_1, b_indo_1, ipa_1, b_ing_1, rata_rata_1, keterangan_1, "
						+ "matematika_2, b_indo_2, ipa_2, b_ing_2, rata_rata_2, keterangan_2, "
						+ "matematika_3, b_indo_3, ipa_3, b_ing_3, rata_rata_3, keterangan_3, "
						+ "matematika_4, b_indo_4, ipa_4, b_ing_4, rata_rata_4, keterangan_4, "
						+ "matematika_5, b_indo_5, ipa_5, b_ing_5, rata_rata_5, keterangan_5, klasifikasi, tahun)"
						+ "VALUES" + "('" + nis + "',"
						+ "'" + matematika_1 + "','" + b_indo_1 + "','" + ipa_1 + "','" + b_ing_1+"', '" + rata_rata_1+"', '" + keterangan_1+"',"
						+ "'" + matematika_2 + "','" + b_indo_2 + "','" + ipa_2 + "','" + b_ing_2+"', '" + rata_rata_2+"', '" + keterangan_2+"',"
						+ "'" + matematika_3 + "','" + b_indo_3 + "','" + ipa_3 + "','" + b_ing_3+"', '" + rata_rata_3+"', '" + keterangan_3+"',"
						+ "'" + matematika_4 + "','" + b_indo_4 + "','" + ipa_4 + "','" + b_ing_4+"', '" + rata_rata_4+"', '" + keterangan_4+"',"
						+ "'" + matematika_5 + "','" + b_indo_5 + "','" + ipa_5 + "','" + b_ing_5+"', '" + rata_rata_5+"', '" + keterangan_5+"',"
						+ "'" + klasifikasi+"', '" + tahun+"')";
	
				System.out.println("Import rows " + i);
				state.executeUpdate(insUpdTbl);

			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (state != null) {
				state.close();
			}

			if (dbConnec != null) {
				dbConnec.close();
			}
		}
	}

}
