package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import connection.DbConnection;
import model.dao.C45AlgorithmDAO;

public class C45Algorithm {

	// tahap 1 get class per label (field hasil)
	public List<Map<String, Object>> getClassPerLabel(List<Map<String, Object>> listData, String dataClass) {
		List<Map<String, Object>> listClassPerLabel = new ArrayList<>();
		Map<String, Object> dataClassPerLabel = new LinkedHashMap<>();
		int all = listData.size();

		// get data from class jadi listofDataClass
		List<String> listOfDataClass = new ArrayList<>();
		for (Map<String, Object> data : listData) {
			listOfDataClass.add((String) data.get(dataClass));
		}

		// get unik value from listofdataClass
		Set<String> uniqueSet = new HashSet<String>(listOfDataClass);
		for (String temp : uniqueSet) {
			int count = Collections.frequency(listOfDataClass, temp);

			// BigDecimal val = new BigDecimal(count).divide(new
			// BigDecimal(all), 8, RoundingMode.CEILING);
			BigDecimal val = divide(new BigDecimal(count), new BigDecimal(all));

			dataClassPerLabel = new LinkedHashMap<>();
			dataClassPerLabel.put("KEY", temp);
			dataClassPerLabel.put("VALUE", val);
			dataClassPerLabel.put("COUNT", count);
			dataClassPerLabel.put("RUMUS", count + "/" + all);
			dataClassPerLabel.put("LABEL", "P(Y=" + temp + ")=" + count + "/" + all);
			listClassPerLabel.add(dataClassPerLabel);
		}

		return listClassPerLabel;
	}

	// tahap 2 get total entropi
	// count rumus entropi
	public BigDecimal getTotalEntropi(List<Map<String, Object>> listClassPerLabel) {
		// Math.log10(x) === Math.log(x) / Math.log(10)
		BigDecimal result = new BigDecimal(0);
		String rumus = "";

		for (Map<String, Object> classPerLabel : listClassPerLabel) {
			BigDecimal val = (BigDecimal) classPerLabel.get("VALUE");
			// kaga bisa log0
			BigDecimal log = new BigDecimal(val.doubleValue() == 0 ? 0 : Math.log(val.doubleValue()) / Math.log(2));
			rumus = rumus + "(-(" + classPerLabel.get("RUMUS") + ") X log2(" + classPerLabel.get("RUMUS") + ")) + ";

			BigDecimal count = multiply(val.negate(), log);
			result = result.add(count);
		}

		// System.out.println("rumus : " + rumus);
		// System.out.println("Result : " + result);

		return result;
	}

	// tahap 3 paling ribet dah recursive
	public Map<String, Object> getTabel(Map<String, Object> dataToList, Map<String, Object> dataToListMurni,
			List<Map<String, Object>> listClassPerLabel, BigDecimal totalEntropi, boolean printTable,
			List<Map<String, Object>> listResult) {
		// List<Map<String, Object>> listResult = new ArrayList<>();
		// map result
		Map<String, Object> result = new LinkedHashMap<>();

		// map buat liat result dan table nya
		Map<String, Object> newMap = new LinkedHashMap<>();
		Map<String, Object> mapGain = new LinkedHashMap<>();

		// map buat set data tree yg pasti
		Map<String, Object> mapVal = new LinkedHashMap<>();

		// list data yang di keep nilai yg buat next tree
		List<String> listKeep = new ArrayList<>();

		// System.out.println("dataToList : " + dataToList);

		String maxGain = "";
		List<String> dataMaxGain = new ArrayList<>();
		BigDecimal bigMaxGain = BigDecimal.ZERO;
		for (String key : dataToList.keySet()) {
			List<Map<String, Object>> listDataNilai = new ArrayList<>();
			Map<String, Object> dataNilai = new LinkedHashMap<>();

			// data with class value
			Map<String, Object> mapDataVal = new LinkedHashMap<>();
			List<String> listDataKey = (List<String>) dataToList.get(key);
			Set<String> uniqueSet = new HashSet<String>(listDataKey);
			for (String temp : uniqueSet) {
				// sengaja di cast string biar gampang handle value null
				mapDataVal.put(temp, String.valueOf(Collections.frequency(listDataKey, temp)));
			}

			// System.out.println("mapDataVal "+key + " : " + mapDataVal);

			// System.out.println("data to list : " + dataToListMurni.get(key));

			// data murni non class value + set return value
			List<String> listDataKeyMurni = (List<String>) dataToListMurni.get(key);
			Set<String> uniqueSetMurni = new HashSet<String>(listDataKeyMurni);
			for (String temp : uniqueSetMurni) {
				// set data
				List<Map<String, Object>> listVal = new ArrayList<>();
				Map<String, Object> dataVal = new LinkedHashMap<>();
				for (Map<String, Object> classperLabel : listClassPerLabel) {
					dataVal = new LinkedHashMap<>();
					String val = (String) mapDataVal.get(temp + "-" + classperLabel.get("KEY"));

					// put kembail dan di convert ke string setelah di handle
					// null value nya
					dataVal.put((String) classperLabel.get("KEY"), Integer.parseInt(val == null ? "0" : val));
					listVal.add(dataVal);
				}

				dataNilai = new LinkedHashMap<>();
				dataNilai.put("DATA", temp);
				dataNilai.put("SUM", Collections.frequency(listDataKeyMurni, temp));
				dataNilai.put("VAL", listVal);
				dataNilai.put("ENTROPI", countEntropi(Collections.frequency(listDataKeyMurni, temp), listVal));
				listDataNilai.add(dataNilai);

				// System.out.println("DATA NILAI : " + listDataNilai);
			}

			int sizeList = ((List<String>) dataToList.get(key)).size();
			// get gain / field
			BigDecimal getGain = countGain(listDataNilai, totalEntropi, new BigDecimal(sizeList));
			// get max gain berdasarkan field
			if (bigMaxGain.compareTo(getGain) == -1) {
				maxGain = key;
				bigMaxGain = getGain;
			}

			newMap.put(key, listDataNilai);
			mapGain.put(key, getGain);
		}

		if (printTable) {
			System.out.println("TABEL TES : " + newMap);
			System.out.println("DATA GAIN : " + mapGain);
			System.out.println("MAX GAIN : " + maxGain + " - " + bigMaxGain);
		}

		// get data max and set to tree
		List<Map<String, Object>> listmax = (List<Map<String, Object>>) newMap.get(maxGain);
		// System.out.println("MAX NIH : " + listmax);
		for (Map<String, Object> data : listmax) {
			BigDecimal entropi = (BigDecimal) data.get("ENTROPI");
			int sum = (int) data.get("SUM");
			if (entropi.compareTo(BigDecimal.ZERO) == 0) {
				List<Map<String, Object>> val = (List<Map<String, Object>>) data.get("VAL");
				// System.out.println("VALLLL : " + val);
				for (Map<String, Object> dataval : val) {
					for (String key : dataval.keySet()) {
						// System.out.println("KEEEY : " + key + " - " +
						// dataval.get(key));
						if (sum == (int) dataval.get(key)) {
							// System.out.println("INI YANG SAMA : " + key + "
							// -- " + data.get("DATA"));
							mapVal.put((String) data.get("DATA"), key);
						}
					}
				}
			} else {
				listKeep.add((String) data.get("DATA"));
			}

		}

		// buat result yg akan di apus nantinya
		int idx = listResult.size();
		result.put("CEK", idx);
		result.put("KEY", maxGain);
		result.put("KEEP", listKeep);
		result.put("VAL", mapVal);

		// System.out.println("RESULT : " + result);

		return result;
	}

	// tahap 4 bagian dari tahap3 buat itung entropi
	public BigDecimal countEntropi(int sum, List<Map<String, Object>> listClassDataWithVal) {
		BigDecimal result = new BigDecimal(0);
		String rumus = "";

		for (Map<String, Object> data : listClassDataWithVal) {
			for (String classname : data.keySet()) {
				BigDecimal val = divide(new BigDecimal((int) data.get(classname)), new BigDecimal(sum));
				// System.out.println("TES : " + new
				// BigDecimal((int)data.get(classname)) + "/" + sum + " == " +
				// val);

				rumus = rumus + "(-(" + new BigDecimal((int) data.get(classname)) + "/" + sum + ") X log2("
						+ new BigDecimal((int) data.get(classname)) + "/" + sum + ")) + ";
				// kaga bisa log0
				BigDecimal log = new BigDecimal(val.doubleValue() == 0 ? 0 : Math.log(val.doubleValue()) / Math.log(2));

				BigDecimal count = multiply(val.negate(), log);
				result = result.add(count);
			}
		}
		// System.out.println("VAL ENTROPI : " +result+ " RUMUS Entropi : " +
		// rumus);
		return result;
	}

	// tahap 5 itung gain
	public BigDecimal countGain(List<Map<String, Object>> listDataNilai, BigDecimal totalEntropi,
			BigDecimal sizeListData) {
		// System.out.println("DATA NILAI : " + dataNilai);
		BigDecimal result = BigDecimal.ZERO;
		String rumus = totalEntropi + "- (";

		BigDecimal sumCountEntropi = BigDecimal.ZERO;
		for (Map<String, Object> dataNilai : listDataNilai) {
			int sum = (int) dataNilai.get("SUM");
			BigDecimal entropi = (BigDecimal) dataNilai.get("ENTROPI");
			BigDecimal a = divide(new BigDecimal(sum), sizeListData);
			sumCountEntropi = sumCountEntropi.add(multiply(a, entropi));

			rumus = rumus + "(" + sum + "/" + sizeListData + ")X" + entropi + ") +";
		}

		result = totalEntropi.subtract(sumCountEntropi);

		// rumus liat gain
		// System.out.println("RUMUS : " + rumus);
		// System.out.println("GAIN : " + result);

		return result;
	}

	// tahap 6 setelah selesai tahap 3(get data per table percobaan) delete data
	// dan di keep data sesuai result tahap 3
	public List<Map<String, Object>> deleteData(List<Map<String, Object>> listData,
			List<Map<String, Object>> listResult, List<String> listKeep, String key) {
		int del = 0;
		List<Map<String, Object>> listDataKeep = new ArrayList<>();
		Map<String, Object> after = new LinkedHashMap<>();

		// System.out.println("LIST DATA BEFORE : " + listData);

		for (Map<String, Object> data : listData) {
			String val = (String) data.get(key);
			for (String keep : listKeep) {
				if (val.equalsIgnoreCase(keep)) {
					// listData.remove(del);
					// del = del - 1;
					listDataKeep.add(data);
				}
			}

			del++;
		}

		// System.out.println("LIST DATA AFTER : " + listDataKeep);
		return listDataKeep;
	}
	
	public static String getResultValue(List<Map<String, Object>> allLisResultnew, Map<String, Object> kasus){		
		String index = "";
		String result = "";
		
		List<Map<String, Object>> listDataAwal = (List<Map<String, Object>>) allLisResultnew.get(0).get("LIST_TREE");
		for(Map<String, Object> data : listDataAwal){
			if(kasus.get(data.get("KEY")).equals(data.get("VALUE"))){
				if((boolean) data.get("KEEP")){
					index = (String) data.get("IDX");
				}else{
					result = (String) data.get("HASIL");
					return result;
				}
			}
		}
//			System.out.println("index : " + index);
		
		for(int i=1; i < allLisResultnew.size(); i++){
			List<Map<String, Object>> listTree = (List<Map<String, Object>>) allLisResultnew.get(i).get("LIST_TREE");
			
			for(Map<String, Object> data : listTree){
				String idxArr[] = ((String)data.get("IDX")).split("--");
				String idx = idxArr[0];
				for(int j=1; j<idxArr.length - 1; j++){
					idx = idx + "--" + idxArr[j];
				}
				
				
				
				if(index.equals(idx)){
//					System.out.println("idx : " + index + "        real : " + (String)data.get("IDX"));
					if(kasus.get(data.get("KEY")).equals(data.get("VALUE"))){
						if((boolean) data.get("KEEP")){
							index = (String) data.get("IDX");
						}else{
							result = (String) data.get("HASIL");
							return result;
						}
					}
				}
			}
		}
		

		
		return result;
	}

	// tahap inti recursive dari tahap3
	public void getAllTableAndValue(Map<String, Object> kasus, String dataClass, List<String> listDelData, int step,
			boolean print, List<Map<String, Object>> alllistResultnew, String index,
			Map<String, Object> mapDataListIndex, List<String> listIndex) {
		// if(listData.size() > 0){
		if (print) {
			System.out.println("=============== TAHAP " + step + " ===============");
			// System.out.println("DATA : " + listData);
		}

		List<String> listIndexNew = new ArrayList<>();
		boolean recursive = false;
		Map<String, Object> mapResultnew = new LinkedHashMap<>();
		List<Map<String, Object>> listResultnew = new ArrayList<>();

		for (String indexxx : listIndex) {
			System.out.println("DATA " + indexxx + " : " + (List<Map<String, Object>>) mapDataListIndex.get(indexxx));
			// convert listmap to liststring
			// Map<String, Object> dataToList = (Map<String, Object>)
			// convertToListData(listData, kasus, dataClass, listDelData);
			Map<String, Object> dataToList = (Map<String, Object>) convertToListData(
					(List<Map<String, Object>>) mapDataListIndex.get(indexxx), kasus, dataClass, listDelData);
			System.out.println("dataToList : " + dataToList);
			// get list data murni
			Map<String, Object> dataToListMurni = (Map<String, Object>) dataToList.get("REAL");
			// System.out.println("dataToListMurni : " + dataToListMurni);
			// remove data murni biar get sama class value nya)
			dataToList.remove("REAL");

			// get class per label
			List<Map<String, Object>> listClassPerLabel = getClassPerLabel(
					(List<Map<String, Object>>) mapDataListIndex.get(indexxx), dataClass);
			// System.out.println("listClassPerLabel : " + listClassPerLabel);

			BigDecimal totalEntropi = getTotalEntropi(listClassPerLabel);
			Map<String, Object> mapTable = getTabel(dataToList, dataToListMurni, listClassPerLabel, totalEntropi, print,
					alllistResultnew);

			if (print)
				System.out.println("GET TREE : " + mapTable);

			// add result dari tiap2 table biar jadi data tree
			// listResult.add(mapTable);

			List<Map<String, Object>> newListData = new ArrayList<>();

			// baru

			List<String> lstkeep = (List<String>) mapTable.get("KEEP");
			Map<String, Object> mapval = (Map<String, Object>) mapTable.get("VAL");

			int idx = 1;
			for (String keydata : mapval.keySet()) {
				Map<String, Object> resultNew = new LinkedHashMap<>();
				// resultNew.put("KEEP", mapTable.get("CEK"));
				resultNew.put("IDX", indexxx + "--" + String.valueOf(idx));
				resultNew.put("KEY", mapTable.get("KEY"));
				resultNew.put("VALUE", keydata);
				resultNew.put("HASIL", mapval.get(keydata));
				resultNew.put("KEEP", false);
				listResultnew.add(resultNew);
				idx++;
			}

			for (String data : lstkeep) {
				Map<String, Object> resultNew = new LinkedHashMap<>();
				resultNew.put("IDX", indexxx + "--" + String.valueOf(idx));
				resultNew.put("KEY", mapTable.get("KEY"));
				resultNew.put("VALUE", data);
				resultNew.put("HASIL", "");
				resultNew.put("KEEP", true);
				listResultnew.add(resultNew);

				// System.out.println( "resultNew____ = " + resultNew);

				if (lstkeep.size() > 1) {
					// set data diapus
					listDelData.add((String) mapTable.get("KEY"));

					// System.out.println("LIST DATA BEFORE : " + listData);
					// newListData = deleteData(listData, listResult, lstkeep,
					// (String) mapTable.get("KEY"));

					List<Map<String, Object>> lstkeepData = new ArrayList<>();
					lstkeepData = deleteDataByIdx(mapDataListIndex, (String) resultNew.get("IDX"),
							(Integer) mapTable.get("CEK"), (String) mapTable.get("KEY"), data);
					mapDataListIndex.put((String) resultNew.get("IDX"), lstkeepData);

					index = indexxx + "--" + String.valueOf(idx);

				} else {
					// set data diapus
					listDelData.add((String) mapTable.get("KEY"));

					// newListData = deleteData(listData, listResult, lstkeep,
					// (String) mapTable.get("KEY"));

					List<Map<String, Object>> lstkeepData = new ArrayList<>();
					lstkeepData = deleteDataByIdx(mapDataListIndex, (String) resultNew.get("IDX"),
							(Integer) mapTable.get("CEK"), (String) mapTable.get("KEY"), data);
					mapDataListIndex.put((String) resultNew.get("IDX"), lstkeepData);

					index = indexxx + "--" + String.valueOf(idx);
				}

				// baru
				// index = index + "--" + String.valueOf(idx);
				listIndexNew.add(index);
				recursive = true;
				idx++;
			}

			mapResultnew.put("CEK", mapTable.get("CEK"));
			// mapResultnew.put("KEY", mapTable.get("KEY"));
			mapResultnew.put("LIST_TREE", listResultnew);

			System.out.println("NEW MAP RESULT : " + mapResultnew);
			// alllistResultnew.add(mapResultnew);
		}

		alllistResultnew.add(mapResultnew);

		// boolean recursive = false;
		// List<Map<String, Object>> listTree = (List<Map<String, Object>>)
		// mapResultnew.get("LIST_TREE");
		// for(Map<String, Object> data : listTree){
		// if((boolean) data.get("KEEP") == true){
		// recursive = true;
		// break;
		// }
		// }
		System.out.println("listIndexNew : " + listIndexNew);
		System.out.println("mapDataListIndex : " + mapDataListIndex);

		if (recursive) {
			step++;
			getAllTableAndValue(kasus, dataClass, listDelData, step, print, alllistResultnew, index, mapDataListIndex,
					listIndexNew);
		}
		// }
	}

	public static List<Map<String, Object>> deleteDataByIdx(Map<String, Object> mapDataListIndex, String index, int cek,
			String key, String keep) {
		// System.out.println("INDEXXX : " + index);
		// System.out.println("CEK : " + cek);
		String[] idx = index.split("--");

		// System.out.println("mapDataList : " + mapDataListIndex);

		String idxdata = "0";
		for (int i = 1; i <= cek; i++) {
			idxdata = idxdata + "--" + idx[i];
		}

		List<Map<String, Object>> listData = (List<Map<String, Object>>) mapDataListIndex.get(idxdata);

		int del = 0;
		List<Map<String, Object>> listDataKeep = new ArrayList<>();
		Map<String, Object> after = new LinkedHashMap<>();

		// System.out.println("LIST DATA BEFORE : " + listData);

		for (Map<String, Object> data : listData) {
			String val = (String) data.get(key);
			if (val.equalsIgnoreCase(keep)) {
				listDataKeep.add(data);
			}
			del++;
		}

		// System.out.println("LIST DATA AFTER : " + listDataKeep);
		return listDataKeep;
	}

	// convert data dari list map jadi list string sesuai kasus
	public static Map<String, Object> convertToListData(List<Map<String, Object>> listData, Map<String, Object> kasus,
			String dataClass, List<String> delfieldNextTahap) {
		Map<String, Object> dataToList = new LinkedHashMap<>();
		Map<String, Object> dataToListMurni = new LinkedHashMap<>();

		for (String key : kasus.keySet()) {
			List<String> listOfData = new ArrayList<>();
			List<String> listOfDataMurni = new ArrayList<>();
			for (Map<String, Object> data : listData) {
				listOfData.add((String) data.get(key) + "-" + data.get(dataClass));
				listOfDataMurni.add((String) data.get(key));
			}

			// buang field jika sudah next tahap
			boolean input = true;
			// for(String data : delfieldNextTahap){
			// if(key.equalsIgnoreCase(data)) input = false;
			// }

			// input jika ga ada data yg di apus dari tabel sebelumnya
			if (input) {
				dataToList.put(key, listOfData);
				dataToListMurni.put(key, listOfDataMurni);
			}

		}

		// System.out.println("ALL : " + dataToList);
		// System.out.println("MURNI : " + dataToListMurni);

		dataToList.put("REAL", dataToListMurni);
		return dataToList;
	}

	// function bigdecimal
	public static BigDecimal divide(BigDecimal penyebut, BigDecimal pembilang) {
		int rounding = 10; // ketilitian perhitungan (koma)
		BigDecimal hasil = new BigDecimal(0);

		if (pembilang.compareTo(new BigDecimal(0)) == 0)
			return hasil;
		if (penyebut.compareTo(new BigDecimal(0)) == 0)
			return hasil;

		hasil = penyebut.divide(pembilang, rounding, RoundingMode.CEILING);

		return hasil;
	}

	public static BigDecimal multiply(BigDecimal A, BigDecimal B) {
		BigDecimal hasil = new BigDecimal(0);

		if (A.compareTo(new BigDecimal(0)) == 0)
			return hasil;
		if (B.compareTo(new BigDecimal(0)) == 0)
			return hasil;

		hasil = A.multiply(B);

		return hasil;
	}

	public static void disticntDataFromListMap(List<Map<String, Object>> listData, String key) {
		List<String> listtest = new ArrayList<>();
		for (Map<String, Object> data : listData) {
			listtest.add((String) data.get(key));
		}

		System.out.println("\n Disticnt Data ");
		Set<String> uniqueSet = new HashSet<String>(listtest);
		for (String temp : uniqueSet) {
			System.out.println(temp + ": " + Collections.frequency(listtest, temp));
		}

	}

}
