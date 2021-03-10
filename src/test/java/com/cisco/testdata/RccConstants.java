package com.cisco.testdata;


import io.restassured.RestAssured;

import java.util.Arrays;
import java.util.List;

public class RccConstants {
    //Labels
    public static final String COMPLIANCE_SETTING="COMPLIANCE SETTINGS";
    public static final String COMPLIANCE_COUNT_CAROUSEL="Compliance";
    public static final String SOFTWARE_TAB = "Software";
    public static final String COMPLIANCE_TAB = "Compliance";
    public static final String VIOLATIONS_SUB_VIEW="Rule Violations";
    public static final String SYSTEMS_VIOLATIONS_SUB_VIEW="Assets With Violations";
    public static final String VIOLATIONS_SHOWING="Violations |";
    public static final String REGULATORY_TYPE="Regulatory Type";
    public static final String REGULATORY_TYPE_FILTER="Regulatory Type";
    public static final String HIGHEST_SEVERITY="Highest Severity";
    public static final String HIGHEST_SEVERITY_LANDING="Highest Severity";
    public static final String CATEGORY="Category";
    public static final String RULE_VIOLATED="Rule Violated";
    public static final String VIOLATIONS="Violations";
    public static final String SOURCE="Source";
    public static final String AFFECTED_SYSTEMS="Affected Systems";
    public static final String SOURCE_IP ="Source - IP Address";
    public static final String SOURCE_HOSTNAME="Source - Host Name";
    public static final String POLICY_DESC="Policy Description";
    public static final String RULE_DESC="Rule Description";
    public static final String SYS_AFFECTED="Systems Affected";
    public static final String PRD_FAMILY="Product Family";
    public static final String PRD_ID="Product ID";
    public static final String SYS_NAME="Asset Name";
    public static final String IP_ADDRESS="IP Address";
    public static final String REL="Release";
    public static final String SWF_REL="Software Release";
    public static final String LAST_CHECKED="Last Checked";
    public static final String SN="Serial Number";
    public static final String VIOLATIONS_SYSTEMS_360_LABEL="Violations for";
    public static final String SEVERITY="Severity";
    public static final String MSG="Message";
    public static final String VIOLATION_AGE="Violation Age";
    public static final String RECOM="Recommendation";
    public static final String SEARCH_PLACEHOLDER="Search";
    public static final String PRD_FAMILY_PLACEHOLDER="Select Product Family";
    public static final String PRD_ID_PLACEHOLDER="Select Product ID";
    public static final String RG_TYPE__PLACEHOLDER="Select Regulatory Type";
    public static final String HIGHEST_SEVERITY_PLACEHOLDER= "Select Highest Severity";

    public static final String SOURCE_NAME="Cisco DNA Center";

    //Filter values
    public static final String RG_HIPAA_FILTER_VALUE="HIPAA";
    public static final String RG_PCI_FILTER_VALUE="PCI";

    public static final String SEVERITY_CRITICAL_FILTER_VALUE="Critical";
    public static final String SEVERITY_HIGH_FILTER_VALUE="High";
    public static final String SEVERITY_MED_FILTER_VALUE="Medium";
    public static final String SEVERITY_LOW_FILTER_VALUE="Low";
    public static final String SEVERITY_INFO_FILTER_VALUE="Info";

    //Tooltip values
    public static final String SHOWING_TOOL_TIP_VALUE="The same rule can be violated on systems associated to different Cisco DNA Centers. Hence, the total number of rule violation records shown in this table may be greater than the total number of rules violated.";
    public static final String CATEGORY_TOOL_TIP="Logical grouping of policy rules by device configuration parameters.";
    public static final String VIOLATIONS_TOOL_TIP="When a device configuration does not conform to a regulatory policy rule.";
    public static final String SOURCE_TOOL_TIP="Cisco DNA Center";
    public static final String HIGHEST_SEVERITY_TOOL_TIP_ASSET="The highest severity of all rule violations found on a system.";

// invalid search
    public static final String SEARCH_INVALID_STRING="TEST12367";

//  api param name
    public static final String POLICY_FILTER_PARAM="policyType";
    public static final String SEVERITY_FILTER_PARAM="severity";

    public static final String HIGH_SEVERITY_SORT_PARAM="ruleSeverityId";
    public static final String PG_SORT_PARAM="policyGroupName";
    public static final String CAT_SORT_PARAM="policyCategory";
    public static final String RULE_VIOLATED_SORT_PARAM="ruleTitle";
    public static final String VIOLATIONS_SORT_PARAM="violationCount";
    public static final String SOURCE_SORT_PARAM="mgmtSystemAddr";
    public static final String AS_SORT_PARAM= "impactedAssetCount";

    public static final String HOST_NAME_SORT_PARAM="hostName";
    public static final String IP_SORT_PARAM="ipAddress";
    public static final String OS_VER_SORT_PARAM="osVersion";
    public static final String SOURCE_IP_SORT_PARAM="mgmtSystemAddr";
    public static final String LAST_SCAN_SORT_PARAM="lastScan";
    public static final String VIOLATION_SORT_PARAM="violationCount";
    public static final String HS_SORT_PARAM="ruleSeverityId";

    public static final String ENT_REQ_BANNER="";
    public static final String VIEW_VIOLATIONS_BANNER="View Violations";
    public static final String PENDING_BANNER="Pending Data Collection";

    public static final String ENT_REQ_BANNER_CXLEVEL_BANNER_MSG ="";
    public static final String RCC_OPT_OUT_ADMIN_BANNER_MSG ="Regulatory Compliance Insights must be enabled to view compliance results";
    public static final String RCC_OPT_OUT_USER_BANNER_MSG="";
    public static final String PG_NOT_CONFIGURED_ADMIN_BANNER_MSG="";
    public static final String PG_NOT_CONFIGURED_USER_BANNER_MSG="";
    public static final String PENDING_BANNER_MSG="Regulatory Compliance results will be available upon the completion of the next system data collection";

    public static final String USER_INITAITIVE_TITLE_MODAL="Compliance Check";

    //Table order
    public static final List<String> VIOLATION_SUMMARY_TABLE= Arrays.asList("Highest Severity","Regulatory Type", "Category", "Rule Violated","Violations","Affected Assets","Source");
    public static final List<String> ASSET_SUMMARY_TABLE= Arrays.asList("Asset Name","IP Address", "Software Release","Last Checked","Violations","Highest Severity","Source");
    public static final List<String> VIOLATION_360_TABLE=Arrays.asList("System Name","IP Address","Product Family","Product ID","Release","Last Checked","Violations");
    public static final List<String> ASSET_360_TABLE=Arrays.asList("Highest Severity","Regulatory Type","Rule Violated","Violations");


    public static final int VIOLATION_LANDING_COL_INDEX_VIO=5;
    public static final int ASSET_LANDING_COL_INDEX_VIO=5;
    public static final int VIOLATION_360_COL_INDEX_VIO=8;

    public static final String BASE_URI = "https://api-test.us.csco.cloud";
//    public static final String BASE_URI = "https://api-stage.us.csco.cloud";
    public static final String VIOLATION_LANDING_API="/rcc/cp-rcc-compliance-api/service/violation-summary";
    public static final String ASSET_LANDING_API="/rcc/cp-rcc-compliance-api/service/asset-summary";
    public static final String VIOLATION_LANDING_FILTER_API="/rcc/cp-rcc-compliance-api/service/violation-filters";
    public static final String UPDATE_OPT_IN_API="/compliance/v1/service/updateOptInStatus";

    //Admin
    public static final String INFO_MESSAGE="The Regulatory Compliance feature provides insights and analytics designed to assist the customer with its decisions and actions.";
    public static final String TITLE="REGULATORY COMPLIANCE";
    public static final String MENUE_TITLE="Insights";
    public static final String POLICY_TITLE="policy Profile";
    public static final String RUN_COMPLIANCE_BUTTON="Run compliance check";
    public static final String POLICY_GROUP_TITLE="Policy Group";
    public static final String POLICY_ASEET_SELECTION_TITLE="Assets Selection";
    public static final String OPTIN_MODAL_TITLE="Confirm Compliance Off";
    public static final String OPTIN_MODAL_BOADY="Are you sure you want to opt-out";
    public static final String OPTOUT_BANNER_TITLE="View Violations";
    public static final String OPTOUT_BANNER_BODY="Regulatory Compliance Insights must be enabled to view compliance results";
    public static final String ENABLE_BANNER_BODY="Regulatory Compliance Policy Profiles must be enabled to view compliance results";
    public static final String PENDING_BANNER_TITLE="Pending Data Collection";
    public static final String PENDING_BANNER_BODY="Regulatory Compliance results will be available upon the completion of the next asset data collection";

    //Login
    public static final String TRACKNAME="insights/solution/campus/compliance";
    public static final String TRACK_UI= "Campus Network";
    public static final String TRACK= "IBN";
    public static final String USECASE="All";
//    public static final String SMARTACCOUNT="OGC LTD";
    public static final String SMARTACCOUNT="PRINCETON LTD";
//    public static final String SMARTACCOUNT="AMERICAN NATIONAL INSURANCE COMPANY INC";
//    public static final String CUSTOMERID="KZWQZCPMnrtM4w";
    public static final String CUSTOMERID="nzR27HKkADs92O";
//    public static final String SAID="337175";
    public static final String SAID="692837";
    public static final String CX_LEVEL="2";
}