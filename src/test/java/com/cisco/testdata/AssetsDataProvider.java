package com.cisco.testdata;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.*;

import com.cisco.utils.Commons;

import static com.cisco.testdata.AssetsExcelDataReader.visualFilterDataSetterUI;
import static com.cisco.testdata.Data.*;

public class AssetsDataProvider {
		
	private AssetsUIPojo pageType = AssetsExcelDataReader.genericDataSetterUI(ASSETS_AND_DIAGNOSTICS_GENERIC_DATA).get("PAGETYPE");
	private AssetsUIPojo systemData= AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ASSETS_SYSTEM");
	private AssetsUIPojo contractData= AssetsExcelDataReader.dataSetterUI(ASSETS_AND_DIAGNOSTICS_ASSETS_DATA).get("ASSETS_CONTRACTS");



	public static List<String> getKeyByType(String type, String[][] excel_data) {
		List<String> keyList = new ArrayList<String>();
		Set<String> keys = visualFilterDataSetterUI(excel_data).keySet();
		Map<String, AssetsUIPojo> data = visualFilterDataSetterUI(excel_data);
		for (String key : keys) {
			if (data.get(key).getVisualFilter().equalsIgnoreCase(type)) {
				keyList.add(key);
				Collections.sort(keyList);
			}
		}
		return keyList;
	}
	@DataProvider(name="genericValues")
	public Iterator<String> getGenericData() {
		   List<String> data = Commons.parseStringtoList(pageType.getFilterNames());
		    return data.iterator();
	}

	@DataProvider(name="visualFiltersAll")
	public Iterator<String> getVisualFilterData() {
		List<String> data = Commons.parseStringtoList(systemData.getVisualFilter());
		return data.iterator();
	}
	
	@DataProvider(name="visualFiltersAllContracts")
	public Iterator<String> getVisualFilterContractsData() {
		List<String> data = Commons.parseStringtoList(contractData.getVisualFilter());
		return data.iterator();
	}

	@DataProvider(name = "visualFiltersValues")
	public Iterator<String> getVisualFiltersValues(Method M) {
		String visualFilterName = M.getName().split("__")[1];
		List<String> data = getKeyByType(visualFilterName, ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA);
		System.out.println("list Size: " + data.size() + " for method: " + M.getName());
		return data.iterator();
	}
	
	}
