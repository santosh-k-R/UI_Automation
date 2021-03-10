package com.cisco.testdata;
	
import java.util.HashMap;
import java.util.Map;

import static com.cisco.testdata.Data.ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA;

public class AssetsExcelDataReader{
		static AssetsUIPojo xlsPojo;
		static Map<String, AssetsUIPojo> uiMetaData;
	private static AssetsUIPojo visualFilter= AssetsExcelDataReader.visualFilterDataSetterUI(ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA).get("CONNECTIVITY_ALL");

	public static Map<String, AssetsUIPojo> dataSetterUI(String[][] excelData)  {
			uiMetaData = new HashMap<String, AssetsUIPojo>();
			for(int i = 0; i < excelData.length; i++)	{
				xlsPojo = new AssetsUIPojo();
				xlsPojo.setModule(excelData[i][0]);
				xlsPojo.setTrack(excelData[i][1]);
				xlsPojo.setUseCase(excelData[i][2]);
				xlsPojo.setRowNum(excelData[i][3]);
				xlsPojo.setColNum(excelData[i][4]);
				xlsPojo.setColInUse(excelData[i][5]);
				xlsPojo.setGridColCount(excelData[i][6]);
				xlsPojo.setColumnsToSelect(excelData[i][7]);
				xlsPojo.setColumnsToDeSelect(excelData[i][8]);
				xlsPojo.setVisualFilter(excelData[i][9]);
				xlsPojo.setSystemCount(excelData[i][10]);
			
				uiMetaData.put(excelData[i][0], xlsPojo);
			}
			return uiMetaData;
		}	
	
	public static Map<String, AssetsUIPojo> genericDataSetterUI(String[][] excelData)  {
		uiMetaData = new HashMap<String, AssetsUIPojo>();
			for(int i = 0; i < excelData.length; i++)	{
				xlsPojo = new AssetsUIPojo();
				xlsPojo.setField(excelData[i][0]);
				xlsPojo.setValues(excelData[i][1]);
			
				uiMetaData.put(excelData[i][0], xlsPojo);
			}
		return uiMetaData;
		}

		public static Map<String, AssetsUIPojo> expectedDataSetterUI(String[][] excelData)  {
			uiMetaData = new HashMap<String, AssetsUIPojo>();
			for(int i = 0; i < excelData.length; i++)	{
				xlsPojo = new AssetsUIPojo();
				xlsPojo.setModule(excelData[i][0]);
				xlsPojo.setSystemCount(excelData[i][1]);
				xlsPojo.setConnectedCount(excelData[i][2]);
				xlsPojo.setNonConnectedCount(excelData[i][3]);
				xlsPojo.setCoveredCount(excelData[i][4]);
				xlsPojo.setUncoveredCount(excelData[i][5]);

				uiMetaData.put(excelData[i][0], xlsPojo);
			}
			return uiMetaData;
		}

		public static Map<String, AssetsUIPojo> visualFilterDataSetterUI(String[][] excelData)  {
			uiMetaData = new HashMap<String, AssetsUIPojo>();
			for(int i = 0; i < excelData.length; i++)	{
				xlsPojo = new AssetsUIPojo();
				xlsPojo.setVisualFilter(excelData[i][0]);
				xlsPojo.setFilterNames(excelData[i][1]);
				xlsPojo.setFilterValues(excelData[i][2]);
				xlsPojo.setAllAssetFilterCount(excelData[i][3]);
				xlsPojo.setCampusFilterCount(excelData[i][4]);
				uiMetaData.put(excelData[i][1], xlsPojo);
			}
			return uiMetaData;
		}

	}
