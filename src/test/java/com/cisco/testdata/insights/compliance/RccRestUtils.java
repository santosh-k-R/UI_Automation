package com.cisco.testdata.insights.compliance;

import io.qameta.allure.Step;
import io.restassured.http.Header;

import java.util.ArrayList;
import java.util.List;

import static com.cisco.utils.Commons.constructCaseParams;
import static com.cisco.utils.RestUtils.getSpec;
import static io.restassured.RestAssured.given;

public class RccRestUtils {

    @Step("API Response for Conditions violations")
    public static Object getViolationCount(Header auth , String params, String extractPath, String URL) {
        return given()
                .spec(getSpec())
                .param("pageIndex", "1")
                .param("pageSize", "10")
                .params(constructCaseParams(params))
                .when().log().all()
                .get(URL)
                .then()
                .statusCode(200)
                .extract()
                .path(extractPath).toString();
    }
    @Step("API Response for Conditions violations")
    public static Object getAPIFristValue(Header auth,String params,String extractPath,String URL) {
        return given()
                .header(auth)
                .param("pageIndex", "1")
                .param("pageSize", "10")
                .params(constructCaseParams(params))
                .when()
                .get(URL)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().getList(extractPath).get(0).toString();
    }
    @Step("API Response for list of all values fof particular column ")
    public static List<String> getValuesOfColumn(Header auth, String params, String extractPath, String URL) {
        int pagecount= given()
                //.header(getToken("customerportal32091","Cisco123"))
                .header(auth)
                .param("pageIndex", "1")
                .param("pageSize", "10")
                .params(constructCaseParams(params))
                .when()
                .get(URL)
                .then()
                .statusCode(200)
                .extract()
                .path("data.totalCount");
        pagecount=pagecount/10 + ((pagecount % 10) == 0 ? 0 : 1);
        System.out.println("Pagecount"+ pagecount);
        List<String> apiColumnValues= new ArrayList<String>();

        for(int i =1 ; i<=pagecount ; i++ ) {
            apiColumnValues.addAll(given()
                    .header(auth)
                    .param("pageIndex", i)
                    .param("pageSize", "10")
                    .params(constructCaseParams(params))
                    .when()
                    .get(URL)
                    .then()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .getList(extractPath));
        }
        return apiColumnValues;
    }
}
