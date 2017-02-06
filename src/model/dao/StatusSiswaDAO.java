package model.dao;

import java.util.List;
import model.dto.StatusSiswaDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StatusSiswaDAO {

	Connection con;

	public StatusSiswaDAO(Connection conn) {
		this.con = conn;

	}
	
	public List<StatusSiswaDTO> findAll(String statusKinerja, String tahun) {
        List<StatusSiswaDTO> allDataKinerjaPegawai = new ArrayList<StatusSiswaDTO>();
        int inTahun = 0;
        if (!tahun.isEmpty()) {
        	inTahun = Integer.parseInt(tahun);
		}
        String query = "";
        if (!statusKinerja.isEmpty() && !tahun.isEmpty()) {
        	query = "SELECT COUNT(CASE WHEN status_kinerja = 'Kinerja Sangat Tinggi' THEN 1 ELSE NULL END) AS kinerja_sangat_tinggi "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Tinggi' THEN 1ELSE NULL END) AS kinerja_tinggi "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Sesuai Standar' THEN 1 ELSE NULL END) AS kinerja_sesuai_standar "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Rendah' THEN 1 ELSE NULL END) AS kinerja_rendah "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Tidak Efektif' THEN 1 ELSE NULL END) AS kinerja_tidak_efektif "
					+ ",COUNT(*) AS all_status_kinerja FROM penilaian_kinerja "
					+ "WHERE tahun = '"+inTahun+"' AND status_kinerja = '"+statusKinerja+"'";
        	
		} else if (!statusKinerja.isEmpty()) {
			query = "SELECT COUNT(CASE WHEN status_kinerja = 'Kinerja Sangat Tinggi' THEN 1 ELSE NULL END) AS kinerja_sangat_tinggi "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Tinggi' THEN 1ELSE NULL END) AS kinerja_tinggi "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Sesuai Standar' THEN 1 ELSE NULL END) AS kinerja_sesuai_standar "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Rendah' THEN 1 ELSE NULL END) AS kinerja_rendah "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Tidak Efektif' THEN 1 ELSE NULL END) AS kinerja_tidak_efektif "
					+ ",COUNT(*) AS all_status_kinerja FROM penilaian_kinerja "
					+ "WHERE status_kinerja = '"+statusKinerja+"'";
			
		} else if (!tahun.isEmpty()) {
			query = "SELECT COUNT(CASE WHEN status_kinerja = 'Kinerja Sangat Tinggi' THEN 1 ELSE NULL END) AS kinerja_sangat_tinggi "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Tinggi' THEN 1ELSE NULL END) AS kinerja_tinggi "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Sesuai Standar' THEN 1 ELSE NULL END) AS kinerja_sesuai_standar "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Rendah' THEN 1 ELSE NULL END) AS kinerja_rendah "
					+ ",COUNT(CASE WHEN status_kinerja = 'Kinerja Tidak Efektif' THEN 1 ELSE NULL END) AS kinerja_tidak_efektif "
					+ ",COUNT(*) AS all_status_kinerja FROM penilaian_kinerja "
					+ "WHERE tahun = '"+inTahun+"'";
			
		} else {
			query = "SELECT COUNT(CASE WHEN a.status_kinerja = 'Kinerja Sangat Tinggi' THEN 1 ELSE NULL END) AS kinerja_sangat_tinggi "
					+ ",COUNT(CASE WHEN a.status_kinerja = 'Kinerja Tinggi' THEN 1ELSE NULL END) AS kinerja_tinggi "
					+ ",COUNT(CASE WHEN a.status_kinerja = 'Kinerja Sesuai Standar' THEN 1 ELSE NULL END) AS kinerja_sesuai_standar "
					+ ",COUNT(CASE WHEN a.status_kinerja = 'Kinerja Rendah' THEN 1 ELSE NULL END) AS kinerja_rendah "
					+ ",COUNT(CASE WHEN a.status_kinerja = 'Kinerja Tidak Efektif' THEN 1 ELSE NULL END) AS kinerja_tidak_efektif "
					+ ",COUNT(*) AS all_status_kinerja FROM penilaian_kinerja a left join master_pegawai b on a.nip=b.nip";
		}
       
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            StatusSiswaDTO jmlKinPegDTO;
            
            while (rs.next()) {
                jmlKinPegDTO = new StatusSiswaDTO();
//                jmlKinPegDTO.setKinerja_sangat_tinggi(rs.getString("kinerja_sangat_tinggi"));
//				jmlKinPegDTO.setKinerja_tinggi(rs.getString("kinerja_tinggi"));
//				jmlKinPegDTO.setKinerja_sesuai_standar(rs.getString("kinerja_sesuai_standar"));
//				jmlKinPegDTO.setKinerja_rendah(rs.getString("kinerja_rendah"));
//				jmlKinPegDTO.setKinerja_tidak_efektif(rs.getString("kinerja_tidak_efektif"));
//				jmlKinPegDTO.setAll_status_kinerja(rs.getString("all_status_kinerja"));
				
				allDataKinerjaPegawai.add(jmlKinPegDTO);
				System.out.println("=========>> allDataKinerjaPegawai 1: " + allDataKinerjaPegawai);
            }
        } catch (SQLException | NullPointerException x) {
            x.getMessage();
        }
        System.out.println("=========>> allDataKinerjaPegawai 2: " + allDataKinerjaPegawai);
        return allDataKinerjaPegawai;
    }
	
}
