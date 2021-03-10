package com.cisco.testdata.insights.compliance;

import com.cisco.testdata.StaticData.ChartType;
import com.cisco.testdata.StaticData.DNAC_DropdownValue;
import com.cisco.testdata.StaticData.FilterValue;
import org.testng.annotations.DataProvider;
import static com.cisco.testdata.RccConstants.*;
import static com.cisco.testdata.RccConstants.SEVERITY_FILTER_PARAM;

public class ViolationsPageData {

    public static final FilterValue RG_HIPAA_FILTER = new FilterValue(REGULATORY_TYPE_FILTER.toUpperCase(), ChartType.PIE, RG_HIPAA_FILTER_VALUE);
    public static final FilterValue RG_PCI_FILTER = new FilterValue(REGULATORY_TYPE_FILTER.toUpperCase(), ChartType.PIE, RG_PCI_FILTER_VALUE);
    public static final FilterValue RG_MEDIUM_FILTER = new FilterValue(HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE, SEVERITY_MED_FILTER_VALUE);
    public static final FilterValue RG_CRITICAL_FILTER = new FilterValue(HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE, SEVERITY_CRITICAL_FILTER_VALUE);
    public static final FilterValue RG_HIGH_FILTER = new FilterValue(HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE, SEVERITY_HIGH_FILTER_VALUE);
    public static final FilterValue RG_LOW_FILTER = new FilterValue(HIGHEST_SEVERITY.toUpperCase(), ChartType.PIE, SEVERITY_LOW_FILTER_VALUE);
    public static String violationCount = "data.totalCount";
    public static String impactedAssetCount = "data.assetCount";
    public static String pageCount = "data.totalCount";
    public static String highestSeverity = "data.summary.ruleSeverity";
    public static String regulatoryType = "data.summary.policyGroupName";
    public static String category = "data.summary.policyCategory";
    public static String ruleViolted = "data.summary.ruleTitle";
    public static String violations = "data.summary.violationCount";
    public static String source = "data.summary.mgmtSystemAddr";
    public static String affectedSystems = "data.summary.impactedAssetCount";
    static public String sourceHostName = "data.summary.mgmtSystemHostname";


    @DataProvider(name = "ViolationSummaryFilter", parallel = true)
    public static Object[][] getFilterValues() {
        return new Object[][]
                {
//                        {RG_HIPAA_FILTER_VALUE, RG_HIPAA_FILTER, 2, POLICY_FILTER_PARAM},
//                        {RG_PCI_FILTER_VALUE, RG_PCI_FILTER, 2, POLICY_FILTER_PARAM},
                        {SEVERITY_CRITICAL_FILTER_VALUE, RG_CRITICAL_FILTER, 1, SEVERITY_FILTER_PARAM},
                        {SEVERITY_HIGH_FILTER_VALUE, RG_HIGH_FILTER, 1, SEVERITY_FILTER_PARAM},
                        {SEVERITY_LOW_FILTER_VALUE, RG_LOW_FILTER, 1, SEVERITY_FILTER_PARAM}
                };
    }

    //No search functionality  available on the aggregrated rows violation and affeected systems
    @DataProvider(name = "ViolationSummarySearch", parallel=true)
    public static Object[][] getSearchValue() {
        return new Object[][]
                {{highestSeverity, DNAC_DropdownValue.ALL_CATEGORIES, false},
                        {regulatoryType, DNAC_DropdownValue.ALL_CATEGORIES, false},
                        {category, DNAC_DropdownValue.ALL_CATEGORIES, false},
                        {ruleViolted, DNAC_DropdownValue.ALL_CATEGORIES, false},
                        //{violations, DNAC_DropdownValue.ALL_CATEGORIES,false},
                        {source, DNAC_DropdownValue.ALL_CATEGORIES, false},
                        {source, DNAC_DropdownValue.DNA_CENTER_APPLIANCES, false},
                        {highestSeverity, DNAC_DropdownValue.ALL_CATEGORIES, true},
                        {regulatoryType, DNAC_DropdownValue.ALL_CATEGORIES, true},
                        {category, DNAC_DropdownValue.ALL_CATEGORIES, true},
                        {ruleViolted, DNAC_DropdownValue.ALL_CATEGORIES, true},
                        {source, DNAC_DropdownValue.ALL_CATEGORIES, true},
                        {source, DNAC_DropdownValue.DNA_CENTER_APPLIANCES, true}
                };
    }

    @DataProvider(name = "ViolationSummarySearchFilterCombo", parallel = true)
    public static Object[][] getSearchFilterComboValue() {
        return new Object[][]
                {
                        {highestSeverity, DNAC_DropdownValue.ALL_CATEGORIES, RG_HIGH_FILTER, RG_PCI_FILTER, SEVERITY_HIGH_FILTER_VALUE, RG_PCI_FILTER_VALUE},
                        {regulatoryType, DNAC_DropdownValue.ALL_CATEGORIES, RG_LOW_FILTER, RG_PCI_FILTER, SEVERITY_LOW_FILTER_VALUE, RG_PCI_FILTER_VALUE},
                        {category, DNAC_DropdownValue.ALL_CATEGORIES, RG_CRITICAL_FILTER, RG_PCI_FILTER, SEVERITY_CRITICAL_FILTER_VALUE, RG_PCI_FILTER_VALUE},
                        {source, DNAC_DropdownValue.ALL_CATEGORIES, RG_LOW_FILTER, RG_PCI_FILTER, SEVERITY_LOW_FILTER_VALUE, RG_PCI_FILTER_VALUE},
                        {source, DNAC_DropdownValue.DNA_CENTER_APPLIANCES, RG_CRITICAL_FILTER, RG_PCI_FILTER, SEVERITY_CRITICAL_FILTER_VALUE, RG_PCI_FILTER_VALUE}
                };
    }

    @DataProvider(name = "ViolationSummarySearchPaginationReset", parallel = true)
    public static Object[][] getSearchPagValue() {
        return new Object[][]
                {
                        {source, DNAC_DropdownValue.ALL_CATEGORIES},
                        {source, DNAC_DropdownValue.DNA_CENTER_APPLIANCES}
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
                        {DNAC_DropdownValue.ALL_CATEGORIES, REGULATORY_TYPE, PG_SORT_PARAM},
                        {DNAC_DropdownValue.DNA_CENTER_APPLIANCES, REGULATORY_TYPE, PG_SORT_PARAM}
                };
    }

    @DataProvider(name = "ViolationSummarySearchSortNoResult", parallel = true)
    public static Object[][] getSearchSortNoValue() {
        return new Object[][]
                {
                        {DNAC_DropdownValue.ALL_CATEGORIES, REGULATORY_TYPE, PG_SORT_PARAM},
                        {DNAC_DropdownValue.DNA_CENTER_APPLIANCES, REGULATORY_TYPE, PG_SORT_PARAM}
                };
    }

    @DataProvider(name = "ViolationSummarySort")
    public static Object[][] getSortValue() {
        return new Object[][]
                {
                        {regulatoryType, PG_SORT_PARAM, REGULATORY_TYPE, 2},
                        {category, CAT_SORT_PARAM, CATEGORY, 3},
                        {source, SOURCE_SORT_PARAM, SOURCE, 7},
                        {highestSeverity, HIGH_SEVERITY_SORT_PARAM, HIGHEST_SEVERITY_LANDING, 1}
                };
    }

    @DataProvider(name = "ViolationSummarySortFilterCombo")
    public static Object[][] getSortFilterValue() {
        return new Object[][]
                {
                        {regulatoryType, PG_SORT_PARAM, REGULATORY_TYPE, 2, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {category, CAT_SORT_PARAM, CATEGORY, 3, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {highestSeverity, HIGH_SEVERITY_SORT_PARAM, HIGHEST_SEVERITY, 1, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {ruleViolted, RULE_VIOLATED_SORT_PARAM, RULE_VIOLATED, 4, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {source, SOURCE_SORT_PARAM, SOURCE, 6, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE}
                };
    }

    @DataProvider(name = "ViolationSummarySortSearchCombo")
    public static Object[][] getSortSearchValue() {
        return new Object[][]
                {
                        {regulatoryType, PG_SORT_PARAM, REGULATORY_TYPE, 2, regulatoryType, DNAC_DropdownValue.ALL_CATEGORIES},
                        {category, CAT_SORT_PARAM, CATEGORY, 3, category, DNAC_DropdownValue.ALL_CATEGORIES},
                        {highestSeverity, HIGH_SEVERITY_SORT_PARAM, HIGHEST_SEVERITY, 1, highestSeverity, DNAC_DropdownValue.ALL_CATEGORIES},
                        //  {ruleViolted,RccConstants.RULE_VIOLATED_SORT_PARAM,RccConstants.RULE_VIOLATED,4,ruleViolted,DNAC_DropdownValue.ALL_CATEGORIES},
                        {source, SOURCE_SORT_PARAM, SOURCE, 6, source, DNAC_DropdownValue.ALL_CATEGORIES},
                };
    }

    @DataProvider(name = "ViolationSummarySortSearchFilterCombo")
    public static Object[][] getSortSearchFilterValue() {
        return new Object[][]
                {
                        {regulatoryType, PG_SORT_PARAM, REGULATORY_TYPE, 2, regulatoryType, DNAC_DropdownValue.ALL_CATEGORIES, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {category, CAT_SORT_PARAM, CATEGORY, 3, category, DNAC_DropdownValue.ALL_CATEGORIES, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {ruleViolted, RULE_VIOLATED_SORT_PARAM, RULE_VIOLATED, 4, ruleViolted, DNAC_DropdownValue.ALL_CATEGORIES, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {highestSeverity, HIGH_SEVERITY_SORT_PARAM, HIGHEST_SEVERITY, 1, highestSeverity, DNAC_DropdownValue.ALL_CATEGORIES, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},
                        {source, SOURCE_SORT_PARAM, SOURCE, 6, source, DNAC_DropdownValue.ALL_CATEGORIES, RG_MEDIUM_FILTER, RG_HIPAA_FILTER, SEVERITY_MED_FILTER_VALUE, RG_HIPAA_FILTER_VALUE},

                };
    }

    @DataProvider(name = "ViolationSummarySortDesc")
    public static Object[][] getSortDescValue() {
        return new Object[][]
                {
                        {violations, VIOLATIONS_SORT_PARAM, VIOLATIONS, 5},
                        // {affectedSystems,RccConstants.AS_SORT_PARAM,RccConstants.AFFECTED_SYSTEMS,6}
                };
    }
}
