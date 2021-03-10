package com.cisco.testdata.insights.compliance;

import com.cisco.testdata.RccConstants;
import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.DNAC_DropdownValue;
import com.cisco.testdata.StaticData.FilterValue;
import org.testng.annotations.DataProvider;

import static com.cisco.testdata.RccConstants.*;
import static com.cisco.testdata.RccConstants.SEVERITY_FILTER_PARAM;

public class ViolationWithSystemsPageData {

    public static final FilterValue RG_MEDIUM_FILTER=new FilterValue(RccConstants.HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE,RccConstants.SEVERITY_MED_FILTER_VALUE);
    public static final FilterValue RG_CRITICAL_FILTER=new FilterValue(RccConstants.HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE,RccConstants.SEVERITY_CRITICAL_FILTER_VALUE);
    public static final FilterValue RG_HIGH_FILTER=new FilterValue(RccConstants.HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE,RccConstants.SEVERITY_HIGH_FILTER_VALUE);
    public static String totalViolationCount="data.ruleViolatedCount";
    public static String totalViolatedDevices="data.totalCount";
    public static String pageCount="data.totalCount";
    public static String hostName="data.assetList.hostName";
    public static String ipAddress="data.assetList.ipAddress";
    public static String osVersion="data.assetList.osVersion";
    public static String mgmtSystemAddr="data.assetList.mgmtSystemAddr";
    public static String lastScan="data.assetList.lastScan";
    public static String violationsCount="data.assetList.violationCount";
    public static String highestSeverity="data.assetList.ruleSeverity";

    @DataProvider(name = "ViolationSummaryFilter", parallel = true)
    public static Object[][] getFilterValues() {
        return new Object[][]
                {
                        {SEVERITY_CRITICAL_FILTER_VALUE,RG_CRITICAL_FILTER,6, SEVERITY_FILTER_PARAM},
                        { SEVERITY_HIGH_FILTER_VALUE, RG_HIGH_FILTER,6, SEVERITY_FILTER_PARAM},
                };
    }

    @DataProvider(name = "ViolationSummarySearch", parallel=true)
    public static Object[][] getSearchValue() {
        return new Object[][]
                { { hostName, DNAC_DropdownValue.ALL_CATEGORIES,false},
                        {ipAddress,DNAC_DropdownValue.ALL_CATEGORIES,false},
                        {osVersion,DNAC_DropdownValue.ALL_CATEGORIES,false},
                        {mgmtSystemAddr,DNAC_DropdownValue.ALL_CATEGORIES,false},
                        {highestSeverity,DNAC_DropdownValue.ALL_CATEGORIES,false},
                        {mgmtSystemAddr,DNAC_DropdownValue.DNA_CENTER_APPLIANCES,false},
                        {hostName,DNAC_DropdownValue.ALL_CATEGORIES,true},
                        {ipAddress,DNAC_DropdownValue.ALL_CATEGORIES,true},
                        {osVersion,DNAC_DropdownValue.ALL_CATEGORIES,true},
                        {mgmtSystemAddr,DNAC_DropdownValue.ALL_CATEGORIES,true},
                        {highestSeverity,DNAC_DropdownValue.ALL_CATEGORIES,true},
                        {mgmtSystemAddr,DNAC_DropdownValue.DNA_CENTER_APPLIANCES,true}
                };
    }

    @DataProvider(name = "ViolationSummarySearchFilterCombo", parallel=true)
    public static Object[][] getSearchFilterComboValue()
    {
        return new Object[][]
                {
                        {hostName,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {ipAddress,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {osVersion,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {mgmtSystemAddr,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {highestSeverity,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {mgmtSystemAddr,DNAC_DropdownValue.DNA_CENTER_APPLIANCES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE}
                };
    }

    @DataProvider(name = "ViolationSummarySearchPaginationReset")
    public static Object[][] getSearchPagValue() {
        return new Object[][]
                {
                        {mgmtSystemAddr,DNAC_DropdownValue.ALL_CATEGORIES},
                        {mgmtSystemAddr,DNAC_DropdownValue.DNA_CENTER_APPLIANCES}
                };
    }

    @DataProvider(name = "ViolationSummarySearchNoResult", parallel = true)
    public static Object[][] getSearchNoValue() {
        return new Object[][]
                {
                        {DNAC_DropdownValue.ALL_CATEGORIES},
                        {DNAC_DropdownValue.DNA_CENTER_APPLIANCES}
                };
    }

    @DataProvider(name = "ViolationSummarySearchSortNoResult", parallel = true)
    public static Object[][] getSearchSortNoValue() {
        return new Object[][]
                {
                        {DNAC_DropdownValue.ALL_CATEGORIES, SYS_NAME},
                        {DNAC_DropdownValue.DNA_CENTER_APPLIANCES, SYS_NAME}
                };
    }

    @DataProvider(name = "ViolationSummaryFilterSearchNoResult", parallel = true)
    public static Object[][] getSearchFilterNoValue() {
        return new Object[][]
                {
                        {DNAC_DropdownValue.ALL_CATEGORIES},
                        {DNAC_DropdownValue.DNA_CENTER_APPLIANCES}
                };
    }

    @DataProvider(name = "ViolationSummaryFilterSearchSortNoResult", parallel = true)
    public static Object[][] getSearchFilterSortNoValue() {
        return new Object[][]
                {

                        {DNAC_DropdownValue.ALL_CATEGORIES, SYS_NAME},
                        {DNAC_DropdownValue.DNA_CENTER_APPLIANCES, SYS_NAME}

                };
    }

    @DataProvider(name = "ViolationSummarySort", parallel=true)
    public static Object[][] getSortValue() {
        return new Object[][]
                {
                        {ipAddress, IP_SORT_PARAM, IP_ADDRESS,2},
                        {hostName, HOST_NAME_SORT_PARAM, SYS_NAME,1},
                        {osVersion, OS_VER_SORT_PARAM, SWF_REL,3},
                        {mgmtSystemAddr, SOURCE_IP_SORT_PARAM, SOURCE,7},
                        {highestSeverity, HS_SORT_PARAM, HIGHEST_SEVERITY,6},
                        {lastScan, LAST_SCAN_SORT_PARAM, LAST_CHECKED,5},
                };
    }

    @DataProvider(name = "ViolationSummarySortDesc")
    public static Object[][] getSorDesctValue() {
        return new Object[][]
                {
                        {violationsCount, VIOLATION_SORT_PARAM, VIOLATIONS,5}
                };
    }

    @DataProvider(name = "ViolationSummarySortFilterCombo", parallel = true)
    public static Object[][] getSortFilterValue() {
        return new Object[][]
                {       {hostName, HOST_NAME_SORT_PARAM, SYS_NAME,1,RG_HIGH_FILTER, SEVERITY_HIGH_FILTER_VALUE},
                        {ipAddress, IP_SORT_PARAM, IP_ADDRESS,2,RG_HIGH_FILTER, SEVERITY_HIGH_FILTER_VALUE},
                        {osVersion, OS_VER_SORT_PARAM, SWF_REL,3,RG_HIGH_FILTER, SEVERITY_HIGH_FILTER_VALUE},
                        {mgmtSystemAddr, SOURCE_IP_SORT_PARAM, SOURCE,7,RG_HIGH_FILTER, SEVERITY_HIGH_FILTER_VALUE},
                        //   {lastScan,RccConstants.LAST_SCAN_SORT_PARAM,RccConstants.LAST_CHECKED,4,RG_HIGH_FILTER,RccConstants.SEVERITY_HIGH_FILTER_VALUE},
                        {highestSeverity, HS_SORT_PARAM, HIGHEST_SEVERITY,6,RG_HIGH_FILTER, SEVERITY_HIGH_FILTER_VALUE}
                };
    }

    @DataProvider(name = "ViolationSummarySortSearchCombo", parallel = true)
    public static Object[][] getSortSearchValue() {
        return new Object[][]
                {
                        {ipAddress, IP_SORT_PARAM, IP_ADDRESS,2,ipAddress,DNAC_DropdownValue.ALL_CATEGORIES},
                        {osVersion, OS_VER_SORT_PARAM, SWF_REL,3,osVersion,DNAC_DropdownValue.ALL_CATEGORIES},
                        // {lastScan,RccConstants.LAST_SCAN_SORT_PARAM,RccConstants.LAST_CHECKED,4,lastScan,DNAC_DropdownValue.ALL_CATEGORIES},
                        {hostName, HOST_NAME_SORT_PARAM, SYS_NAME,1,hostName,DNAC_DropdownValue.ALL_CATEGORIES},
                        {mgmtSystemAddr, SOURCE_IP_SORT_PARAM, SOURCE,7,mgmtSystemAddr,DNAC_DropdownValue.ALL_CATEGORIES},
                        {highestSeverity, HS_SORT_PARAM, HIGHEST_SEVERITY,6,highestSeverity,DNAC_DropdownValue.ALL_CATEGORIES},
                        {hostName, HOST_NAME_SORT_PARAM, SYS_NAME,1,mgmtSystemAddr,DNAC_DropdownValue.DNA_CENTER_APPLIANCES},
                };
    }

    @DataProvider(name = "ViolationSummarySortSearchFilterCombo", parallel=true)
    public static Object[][] getSortSearchFilterValue() {
        return new Object[][]
                {
                        {ipAddress, IP_SORT_PARAM, IP_ADDRESS,2,ipAddress,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {osVersion, OS_VER_SORT_PARAM, SWF_REL,3,osVersion,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {mgmtSystemAddr, SOURCE_IP_SORT_PARAM, SOURCE,4,mgmtSystemAddr,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {hostName, HOST_NAME_SORT_PARAM, SYS_NAME,1,hostName,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        //   {lastScan,RccConstants.LAST_SCAN_SORT_PARAM,RccConstants.LAST_CHECKED,5,lastScan,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER,RccConstants.SEVERITY_CRITICAL_FILTER_VALUE},
                        {highestSeverity, HS_SORT_PARAM, HIGHEST_SEVERITY,6,highestSeverity,DNAC_DropdownValue.ALL_CATEGORIES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE},
                        {hostName, HOST_NAME_SORT_PARAM, SYS_NAME,1,mgmtSystemAddr,DNAC_DropdownValue.DNA_CENTER_APPLIANCES,RG_CRITICAL_FILTER, SEVERITY_CRITICAL_FILTER_VALUE}
                };
    }


    
}
