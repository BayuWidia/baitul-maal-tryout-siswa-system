package model.dao;

import java.util.List;

import connection.DbConnection;
import model.dto.KelolaSiswaDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class KelolaSiswaDAO {

	Connection con;

	public KelolaSiswaDAO(Connection conn) {
		this.con = conn;

	}

	public List<KelolaSiswaDTO> findAll() {
		List<KelolaSiswaDTO> allDataSiswa = new ArrayList<KelolaSiswaDTO>();

		String query = "select * from siswa";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			KelolaSiswaDTO kelSiswaDTO;
			while (rs.next()) {
				kelSiswaDTO = new KelolaSiswaDTO();
				kelSiswaDTO.setNis(rs.getString("nis"));
				kelSiswaDTO.setNama_siswa(rs.getString("nama_siswa"));
				allDataSiswa.add(kelSiswaDTO);
			}
		} catch (SQLException | NullPointerException x) {
			x.getMessage();
		}
		return allDataSiswa;
	}

	public List<KelolaSiswaDTO> findSearch(String search) {
		List<KelolaSiswaDTO> allDataSiswa = new ArrayList<KelolaSiswaDTO>();

		String query = "select * from siswa where nama_siswa like '%" + search + "%'";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			KelolaSiswaDTO kelSiswaDTO;
			while (rs.next()) {
				kelSiswaDTO = new KelolaSiswaDTO();
				kelSiswaDTO.setNis(rs.getString("nis"));
				kelSiswaDTO.setNama_siswa(rs.getString("nama_siswa"));
				allDataSiswa.add(kelSiswaDTO);
			}
		} catch (SQLException | NullPointerException x) {
			x.getMessage();
		}
		return allDataSiswa;
	}

	public void insertKelolaSiswa(String nis, 
			Double matematika_1, Double b_indo_1, Double ipa_1, Double b_ing_1, Double rata_rata_1, String keterangan_1,
			Double matematika_2, Double b_indo_2, Double ipa_2, Double b_ing_2, Double rata_rata_2, String keterangan_2, 
			Double matematika_3, Double b_indo_3, Double ipa_3, Double b_ing_3, Double rata_rata_3, String keterangan_3, 
			Double matematika_4, Double b_indo_4, Double ipa_4, Double b_ing_4, Double rata_rata_4, String keterangan_4, 
			Double matematika_5, Double b_indo_5, Double ipa_5, Double b_ing_5, Double rata_rata_5, String keterangan_5, 
			String klasifikasi, Integer tahun) throws SQLException {

		Connection dbConnec = null;
		Statement state = null;
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
		System.out.println("==============>> insUpdTbl" + insUpdTbl);
		try {
			dbConnec = DbConnection.getDBConnection();
			state = dbConnec.createStatement();

			state.executeUpdate(insUpdTbl);
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
