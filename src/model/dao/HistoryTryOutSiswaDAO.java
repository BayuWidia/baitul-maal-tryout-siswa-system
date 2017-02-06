package model.dao;

import java.util.List;

import model.dto.HasilTryOutSiswaDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HistoryTryOutSiswaDAO {

	Connection con;

	public HistoryTryOutSiswaDAO(Connection conn) {
		this.con = conn;

	}
	
	public List<HasilTryOutSiswaDTO> findAll(String strNis) {
        List<HasilTryOutSiswaDTO> allDataTryOutSiswa = new ArrayList<HasilTryOutSiswaDTO>();
      
        String query = "select * from nilai_try_out a left join siswa b on a.nis=b.nis where a.nis = '"+strNis+"'";
       
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            HasilTryOutSiswaDTO hslKinSiswaDTO;
            
            while (rs.next()) {
            	hslKinSiswaDTO = new HasilTryOutSiswaDTO();
            	hslKinSiswaDTO.setId(rs.getInt("id"));
				hslKinSiswaDTO.setNis(rs.getString("nis"));
				hslKinSiswaDTO.setNama_siswa(rs.getString("nama_siswa"));
				hslKinSiswaDTO.setKeterangan_1(rs.getString("keterangan_1"));
				hslKinSiswaDTO.setKeterangan_2(rs.getString("keterangan_2"));
				hslKinSiswaDTO.setKeterangan_3(rs.getString("keterangan_3"));
				hslKinSiswaDTO.setKeterangan_4(rs.getString("keterangan_4"));
				hslKinSiswaDTO.setKeterangan_5(rs.getString("keterangan_5"));
				hslKinSiswaDTO.setTahun(rs.getInt("tahun"));
				hslKinSiswaDTO.setStatus_nilai_siswa(rs.getString("klasifikasi"));
				
				allDataTryOutSiswa.add(hslKinSiswaDTO);
            }
        } catch (SQLException | NullPointerException x) {
            x.getMessage();
        }
        return allDataTryOutSiswa;
    }
}
