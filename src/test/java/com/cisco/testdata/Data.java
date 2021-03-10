package com.cisco.testdata;

import com.cisco.utils.Commons;
import com.cisco.utils.ExcelReader;

import java.util.Map;

import org.testng.annotations.DataProvider;

public class Data {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String PATH_SEPARATOR = System.getProperty("file.separator");
    private static final String ENV = System.getProperty("env");
    public static final String DIR = USER_DIR + PATH_SEPARATOR + "test-data" + PATH_SEPARATOR + ENV + PATH_SEPARATOR;

    //**********************************************************Lifecycle Data*************************************************************************
    public static final Map<String, String> ACCELERATOR_DATA = Commons.getTestData(DIR + "LifeCycle.xlsx", "Accelerator", false);
    public static final Map<String, String> LIFECYCLE_COMMON_DATA = Commons.getTestData(DIR + "LifeCycle.xlsx", "Common", false);
    public static final Map<String, String> ASK_THE_EXPERTS_DATA = Commons.getTestData(DIR + "LifeCycle.xlsx", "AskTheExperts", false);
    public static final Map<String, String> LEARNING_DATA = Commons.getTestData(DIR + "LifeCycle.xlsx", "ELearning", false);

    //*********************************************************Problem Resolution Data*****************************************************************
    public static final Map<String, String> CASES_COMMON_DATA = Commons.getTestData(DIR + "Cases.xlsx", "Common", false);
    public static final Map<String, String> CASES_TESTCASE_DATA = Commons.getTestData(DIR + "Cases.xlsx", "CP9404", false);

    //*******************************************************Risk Mitigation Data************************************************************************
    public static final Map<String, String> RISK_MITIGATION_HOME_DATA = Commons.getTestData(DIR + "RiskMitigation.xlsx", "Home", false);
    public static final Map<String, String> RISK_MITIGATION_TRACK_DATA = Commons.getTestData(DIR + "RiskMitigation.xlsx", "RMC Track", false);

    /* @DataProvider(name = "multiUseCases", parallel = false)
     public static Object[][] searchTerms() {
         return ExcelReader.readTestData(DIR + "RiskMitigation.xlsx", "Home", true);
     }
     */
    //*******************************************************Assets old Data*****************************************************************************
    public static final Map<String, String> ASSETS_AND_DIAGNOSTICS_COMMON_DATA = Commons.getTestData(DIR + "AssetsAndDiagnostics.xlsx", "Common", false);
    public static final Map<String, String> ASSETS_AND_DIAGNOSTICS_TRACK_DATA = Commons.getTestData(DIR + "AssetsAndDiagnostics.xlsx", "Assets Data", false);

    //*******************************************************Assets Data************************************************************************
    public static final String[][] ASSETS_AND_DIAGNOSTICS_ASSETS_DATA = ExcelReader.readTestDataForModule(DIR + "AssetsAndDiagnostics.xlsx", "AssetDiagData", true);
    public static final String[][] ASSETS_AND_DIAGNOSTICS_GENERIC_DATA = ExcelReader.readTestDataForModule(DIR + "AssetsAndDiagnostics.xlsx", "GenericInputs", true);
    public static final String[][] ASSETS_AND_DIAGNOSTICS_EXPECTED_DATA = ExcelReader.readTestDataForModule(DIR + "AssetsAndDiagnostics.xlsx", "ExpectedResults", true);
    public static final String[][] ASSETS_AND_DIAGNOSTICS_VISUALFILTER_DATA = ExcelReader.readTestDataForModule(DIR + "AssetsAndDiagnostics.xlsx", "VisualFilter", true);

    /*@DataProvider(name = "searchTerms", parallel = false)
    public static Object[][] searchTerms() {
        return ExcelReader.readTestData(DIR + "SearchPage.xlsx", "dp", true);
    }*/

    //*******************************************************Software Data************************************************************************
    public static final  String[][] SOFTWARE_RELEASE_COLUMN_DATA = ExcelReader.readTestData(DIR+"OSV_Automation.xlsx", "Software Releases", true);
    public static final  String[][] SOFTWARE_GROUP_COLUMN_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx","Software Grouop Sorting", true);
    public static final  String[][] BUG_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx", "Bugs", true);
    public static final  String[][] SECURITY_ADVISORIES_SORTING_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx","Security_Advisories", true);
    public static final  String[][] FIELD_NOTICES_SORTING_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx", "Field_Notices",true);
    public static final  String[][] SYSTEM_SORTING_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx", "System Sorting",true);
    public static final  String[][] CURRENT_RELEASE_SORTING_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx", "Current Release",true);
    public static final  String[][] ACTIVE_FEATURES_SORTING_DATA = ExcelReader.readTestData(DIR + "OSV_Automation.xlsx", "Active Features",true);
    public static final Map<String, String> HOME_DATA = Commons.getTestData(DIR + "OSV_Automation.xlsx", "Home", false);
    public static final Map<String, String> ERROR_DATA = Commons.getTestData(DIR + "OSV_Automation.xlsx", "Error", false);
    public static final Map<String, String> SEARCH_DATA = Commons.getTestData(DIR + "OSV_Automation.xlsx", "Search", false);
    public static final Map<String, String> CONTROL_POINT_DATA = Commons.getTestData(DIR + "ControlPoint.xlsx", "CoPStaticData", false);


}
