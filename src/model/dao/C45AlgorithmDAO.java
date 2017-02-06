package model.dao;

import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class C45AlgorithmDAO {

	Connection con;

	public C45AlgorithmDAO(Connection conn) {
		this.con = conn;

	}

	public List<Map<String, Object>> findByC45Algoritm() {
        List<Map<String, Object>> allDataTryOutSiswa = new ArrayList<Map<String, Object>>();
        String query = "select * from nilai_try_out a left join siswa b on a.nis=b.nis";
//        if (!statusNilaiSiswa.isEmpty()) {
//			query = "select * from nilai_try_out a left join siswa b on a.nis=b.nis where a.status_nilai_siswa = '"+statusNilaiSiswa+"'";
//		} else {
//			query = "select * from nilai_try_out a left join siswa b on a.nis=b.nis";
//		}
       
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Map<String, Object> mapData = new LinkedHashMap<>();
            
            while (rs.next()) {
            	mapData = new LinkedHashMap<>();
            	mapData.put("KETERANGAN_1", rs.getString("keterangan_1"));
            	mapData.put("KETERANGAN_2", rs.getString("keterangan_2"));
            	mapData.put("KETERANGAN_3", rs.getString("keterangan_3"));
            	mapData.put("KETERANGAN_4", rs.getString("keterangan_4"));
            	mapData.put("KETERANGAN_5", rs.getString("keterangan_5"));
            	mapData.put("KLASIFIKASI", rs.getString("klasifikasi"));
            	
				allDataTryOutSiswa.add(mapData);
//				System.out.println("AAAAAAAAAAAAAAAAAA :: " + allDataTryOutSiswa);
            }
        } catch (SQLException | NullPointerException x) {
            x.getMessage();
        }
        return allDataTryOutSiswa;
    }
}
