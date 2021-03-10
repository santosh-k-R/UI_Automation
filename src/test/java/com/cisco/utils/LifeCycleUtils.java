package com.cisco.utils;

import com.cisco.testdata.StaticData.CarouselName;
import com.cisco.testdata.StaticData.PitStopsName;
import com.cisco.testdata.lifecycle.*;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cisco.testdata.lifecycle.Data.*;
import static com.cisco.utils.Commons.getAPIResponse;

public class LifeCycleUtils extends AppUtils {

    public boolean cancelATXSessionIfRegistered(ATXPoJo atxData, String userRole) {
        if (getATXStatus(atxData).equalsIgnoreCase("Registered")) {
            return postATXSession(atxData, atxData.getCustomerId(), atxData.getSolution(), userRole, "DELETE", "ATX Cancelled Successfully", "Error Encountered while Cancelling ATX");
        }
        return true;
    }

    public boolean cancelATXSessionIfRegistered(ATXPoJo atxData) {
        return cancelATXSessionIfRegistered(atxData, DEFAULT_USER_ROLE);
    }

    public String getATXStatus(ATXPoJo atxData, String userRole) {
        String params = getParams(atxData.getCustomerId(), atxData.getSolution(), atxData.getUsecase(), atxData.getPitstop());
        Response response = getAPIResponse("GET", ATX_URL, "", params, "", userRole);
        if (response.statusCode() == 200) {
            List<Map<String, String>> sessions = response.getBody().jsonPath().getList("items.sessions.flatten()");
            return sessions.stream()
                    .filter(session -> session.get("sessionId").equals(atxData.getItems().get(0).getSessions().get(0).getSessionId()))
                    .map(session -> session.get("status"))
                    .collect(Collectors.joining());
        } else {
            System.out.println("Unable to fetch the status");
            return "";
        }
    }

    public String getATXStatus(ATXPoJo atxData) {
        return getATXStatus(atxData, DEFAULT_USER_ROLE);
    }

    public boolean postATXSessionIfNotRegistered(ATXPoJo atxData, String userRole) {
        if (!getATXStatus(atxData).equalsIgnoreCase("Registered")) {
            return postATXSession(atxData, atxData.getCustomerId(), atxData.getSolution(), userRole, "POST", "ATX Posted Successfully", "Error Encountered while Posting ATX");
        }
        return true;

    }

    public void postATXSessionIfNotRegistered(ATXPoJo atxData) {
        postATXSessionIfNotRegistered(atxData, DEFAULT_USER_ROLE);
    }

    public static Map<String, String> getUserCurrentPitStopForAllUseCase(String customerId, String userRole) {
        return getRaceTrackResponse(customerId, userRole).getItems().stream()
                .map(itemsItem -> itemsItem.getUsecases()
                        .stream()
                        .collect(Collectors.toMap(RaceTrackPoJo.UsecasesItem::getName, RaceTrackPoJo.UsecasesItem::getCurrentPitstop)))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public static Map<String, String> getUserNextPitStopForAllUseCase(Map<String, String> currentPiStopForAllUseCase) {
        Map<String, String> nextPitStops = new LinkedHashMap<>();
        PitStopsName[] pitStops = PitStopsName.values();
        for (String useCase : currentPiStopForAllUseCase.keySet()) {
            for (int i = 0; i < pitStops.length; i++) {
                if (pitStops[i].getPitStopName().contains(currentPiStopForAllUseCase.get(useCase))) {
                    nextPitStops.put(useCase, String.valueOf(pitStops[i + 1]));
                    break;
                }
            }
        }
        return nextPitStops;
    }

    public static RaceTrackPoJo getRaceTrackResponse(String customerId, String userRole) {
        String params = "customerId=" + customerId;
        Response response = getAPIResponse("GET", RACETRACK_URL, "", params, "", userRole);
        if (response.statusCode() == 200)
            return response.as(RaceTrackPoJo.class, ObjectMapperType.JACKSON_2);
        else return new RaceTrackPoJo();
    }

    public static ATXPoJo getATXResponse(String customerId, String solution, String useCase, String pitStop, String userRole, String additionalParam) {
        String params = getParams(customerId, solution, useCase, pitStop) + additionalParam;
        Response response = getAPIResponse("GET", ATX_URL, "", params, "", userRole);
        if (response.statusCode() == 200) {
            return response.as(ATXPoJo.class, ObjectMapperType.JACKSON_2);
        }
        return new ATXPoJo();
    }

    public static ATXPoJo getATXResponse(ATXPoJo atxPoJo, String userRole, String additionalParam) {
        return getATXResponse(atxPoJo.getCustomerId(), atxPoJo.getSolution(), atxPoJo.getUsecase(), atxPoJo.getPitstop(), userRole, additionalParam);
    }

    private boolean postATXSession(ATXPoJo atxData, String customerId, String solution, String userRole, String delete, String msg, String errorMsg) {
        String params = getParams(customerId, solution, atxData.getUsecase(), atxData.getPitstop());
        params = params + "&atxId=" + atxData.getItems().get(0).getAtxId() + "&eventNumber=" + atxData.getItems().get(0).getSessions().get(0).getEventNumber() +
                "&sessionId=" + atxData.getItems().get(0).getSessions().get(0).getSessionId();

        Response response = getAPIResponse(delete, ATX_URL + "/registration", "", params, "", userRole);
        if (response.statusCode() == 200) {
            System.out.println(msg);
            return true;
        } else {
            System.out.println(errorMsg);
            return false;
        }
    }

    private static String getParams(String customerId, String solution, String useCase, String pitStop) {
        return "solution=" + solution + "&pitstop=" + pitStop + "&usecase=" + useCase + "&customerId="
                + customerId;
    }


    @Step("Get Current Pitstop Name")
    public String getCurrentPitStopName() {
        return getWebElement("[data-auto-id='CurrentPitstopTitle']").getText();
    }

    @Step("Change the Pitstop to {0}")
    public AppUtils changePitStop(PitStopsName toPitStop) {
        PitStopsName currentPiStop = PitStopsName.valueOf(getCurrentPitStopName());
        if (!currentPiStop.equals(toPitStop)) {
            System.out.println(
                    "Changing the pistop from " + currentPiStop.getPitStopName() + " to " + toPitStop.getPitStopName());
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='circle' and @data-auto-id='" + toPitStop.getPitStopName() + "']")));
            getWebElement("//*[name()='circle' and @data-auto-id='" + toPitStop.getPitStopName() + "']").click();
        } else
            System.out.println("Pitstop " + toPitStop + " is already selected");
        return this;
    }

    @Step("Move to Next Pitstop")
    public AppUtils moveToNextPitstop() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='g' and @data-auto-id='Racetrack-RightArrow']")));
        getWebElement("//*[name()='g' and @data-auto-id='Racetrack-RightArrow']").click();
        return this;
    }

    @Step("Move to Previous Pitstop")
    public AppUtils moveToPreviousPitstop() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='g' and @data-auto-id='Racetrack-LeftArrow']")));
        getWebElement("//*[name()='g' and @data-auto-id='Racetrack-LeftArrow']").click();
        return this;
    }

    @Step("Move to Current Pitstop")
    public AppUtils moveToCurrentPitstop() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='g' and @id='racecar']")));
        getWebElement("//*[name()='g' and @id='racecar']").click();
        return this;
    }

    @Step("Close View All Screen")
    public AppUtils closeViewAllScreen() {
    	getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-auto-id='ViewAllCloseModal']")));
        getWebElement("ViewAllCloseModal").click();
        return this;
    }

    @Step("Get Highlighted Pitstop Data Auto ID")
    public String getHighlightedPitstopName() {
        if (getWebElements("//*[contains(@data-auto-id,'Racetrack-Point') and @r='12']").size() > 0) {
            String[] pitStopName = getWebElement("//*[contains(@data-auto-id,'Racetrack-Point') and @r='12']")
                    .getAttribute("data-auto-id").split("-");
            return pitStopName[2];
        } else {
            return this.getCurrentPitStopName();
        }
    }

    public static ACCPoJo getACCResponse(String customerId, String solution, String useCase,
                                         String pitStop, String userRole) {
        String params = getParams(customerId, solution, useCase, pitStop);
        Response response = getAPIResponse("GET", ACC_URL, "", params, "", userRole);

        if (response.getStatusCode() == 200)
            System.out.println("ACC retrieved Successfully");
        else
            System.out.println("Failed to retrieve ACC");

        return response.as(ACCPoJo.class, ObjectMapperType.JACKSON_2);
    }
    
    public static ElearningPoJo getELearningResponse(String customerId, String solution, String useCase, String pitStop, String userRole) {
    	String params = getParams(customerId, solution, useCase, pitStop);
    	Response response = getAPIResponse("GET", ELEARNING_URL, "", params, "", userRole);

    	if (response.getStatusCode() == 200)
    		System.out.println("ELearning Courses retrieved Successfully");
    	else
    		System.out.println("Failed to retrieve ELearning Courses");

    	return response.as(ElearningPoJo.class, ObjectMapperType.JACKSON_2);
    }

    public LifeCycleUtils deleteACCRequest(List<ACCPoJo> accPoJos, String userRole) {
        accPoJos.forEach(accPoJo -> {
            String params = getParams(accPoJo.getCustomerId(), accPoJo.getSolution(), accPoJo.getUsecase(), accPoJo.getPitstop());
            Response response = getAPIResponse("GET", ACC_URL, "", params, "", userRole);

            List<Map<String, String>> items = response.jsonPath().getList("items");
            items.stream()
                    .filter(item -> item.get("status").equals("requested"))
                    .forEach(item -> {
                        System.out.println("Running Delete ACC Request...");
                        Response resp = getAPIResponse("DELETE", ACC_URL + item.get("requestId") + "/delete",
                                "",
                                "customerId=" + accPoJo.getCustomerId(),
                                "", userRole);

                        if (resp.getStatusCode() == 200)
                            System.out.println("Requested ACC Deleted Successfully");
                    });


        });
        return this;
    }

    public LifeCycleUtils deleteACCRequest(List<ACCPoJo> accPoJos) {
        deleteACCRequest(accPoJos, DEFAULT_USER_ROLE);
        return this;
    }

    public LifeCycleUtils postACC(ACCPoJo.ItemsItem accItem, String userRole) {
        String URL = ACC_URL + accItem.getAccId() + "/request";
        String params = "customerId=" + accItem.getCustomerId() + "&buId=" + accItem.getBuId() + "&solution=" + accItem.getSolution();
        Response response = getAPIResponse("POST", URL, "", params, constructACCBody(accItem), userRole);
        if (response.getStatusCode() == 200)
            System.out.println("ACC Requested Successfully");
        else
            System.out.println("Failed to POST ACC");
        return this;
    }

    public LifeCycleUtils postACC(ACCPoJo.ItemsItem accItem) {
        postACC(accItem, DEFAULT_USER_ROLE);
        return this;
    }

    public String constructACCBody(ACCPoJo.ItemsItem accItem) {
        return "{\n" +
                "\t\"accTitle\": \"" + accItem.getTitle() + "\",\n" +
                "\t\"additionalAttendees\": " + accItem.getAdditionalAttendees() + ",\n" +
                "\t\"businessOutcome\": \"" + accItem.getBusinessOutcome() + "\",\n" +
                "\t\"customerId\": \"" + accItem.getCustomerId() + "\",\n" +
                "\t\"environment\": \"" + accItem.getEnvironment() + "\",\n" +
                "\t\"pitstop\": \"" + accItem.getPitstop() + "\",\n" +
                "\t\"preferredLanguage\": \"" + accItem.getPreferredLanguage() + "\",\n" +
                "\t\"preferredSlot\": \"" + accItem.getPreferredSlot() + "\",\n" +
                "\t\"reasonForInterest\": \"" + accItem.getReasonForInterest() + "\",\n" +
                "\t\"solution\": \"" + accItem.getSolution() + "\",\n" +
                "\t\"timezone\": \"" + accItem.getTimezone() + "\",\n" +
                "\t\"usecase\": \"" + accItem.getUsecase() + "\",\n" +
                "\t\"providerId\": " + accItem.getProviderId() + ",\n" +
                "\t\"providerName\": " + accItem.getProviderName() + ",\n" +
                "\t\"assetId\": " + accItem.getAssetId() + "\n" +
                "}";
    }

    public LifeCycleUtils addBookMark(ATXPoJo.ItemsItem atxItem, String userRole) {
        String body = constructBookmarkBody(atxItem.getAtxId(), "ATX", atxItem.getPitstop(),
                atxItem.getSolution(), atxItem.getUsecase(), true);

        postBookMark(userRole, body, atxItem.getCustomerId());
        return this;
    }

    public LifeCycleUtils addBookMark(ATXPoJo.ItemsItem atxItem) {
        addBookMark(atxItem, DEFAULT_USER_ROLE);
        return this;
    }

    public LifeCycleUtils addBookMark(ACCPoJo.ItemsItem accItem, String userRole) {
        String body = constructBookmarkBody(accItem.getAccId(), "ACC", accItem.getPitstop(),
                accItem.getSolution(), accItem.getUsecase(), true);

        postBookMark(userRole, body, accItem.getCustomerId());
        return this;
    }

    public LifeCycleUtils addBookMark(ACCPoJo.ItemsItem accItem) {
        addBookMark(accItem, DEFAULT_USER_ROLE);
        return this;
    }

    private void postBookMark(String userRole, String body, String customerId) {
        Response response = getAPIResponse("POST", BOOKMARK_URL, "", "customerId=" + customerId, body, userRole);
        if (response.getStatusCode() == 200)
            System.out.println("Bookmark Successful");
        else
            System.out.println("Failed to Bookmark");
    }

    public String constructBookmarkBody(String id, String category, String pitStop, String solution, String useCase, boolean bookmark) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("lifecycleCategory", category);
        jsonObject.addProperty("pitstop", pitStop);
        jsonObject.addProperty("solution", solution);
        jsonObject.addProperty("usecase", useCase);
        jsonObject.addProperty("bookmark", bookmark);
        return jsonObject.toString();
    }

    /**
     * @param userRole
     * @return
     */
    public LifeCycleUtils clearBookMark(ACCPoJo.ItemsItem accItem, String userRole) {
        String body = constructBookmarkBody(accItem.getAccId(), "ACC", accItem.getPitstop(),
                accItem.getSolution(), accItem.getUsecase(), false);

        postBookMark(userRole, body, accItem.getCustomerId());
        return this;
    }

    public LifeCycleUtils clearBookMark(ACCPoJo.ItemsItem accItem) {
        clearBookMark(accItem, DEFAULT_USER_ROLE);
        return this;
    }

    public LifeCycleUtils clearBookMark(ATXPoJo.ItemsItem atxItem, String userRole) {
        String body = constructBookmarkBody(atxItem.getAtxId(), "ATX", atxItem.getPitstop(),
                atxItem.getSolution(), atxItem.getUsecase(), false);

        postBookMark(userRole, body, atxItem.getCustomerId());
        return this;
    }

    public LifeCycleUtils clearBookMark(ATXPoJo.ItemsItem atxItem) {
        clearBookMark(atxItem, DEFAULT_USER_ROLE);
        return this;
    }

    public LifeCycleUtils deleteACCFeedback(ACCPoJo.ItemsItem accItem, String userRole) {
        String params = getParams(accItem.getCustomerId(), accItem.getSolution(), accItem.getUsecase(), accItem.getPitstop());
        Response response = getAPIResponse("GET", ACC_URL, "", params, "", userRole);
        List<String> feedBackIds = response.jsonPath().getList("items.engagementList.feedbackInfo.feedbackId.flatten()");
        feedBackIds = feedBackIds.stream().filter(id -> !id.isEmpty()).collect(Collectors.toList());
        feedBackIds.forEach(feedBackId -> {
            if (feedBackId.contains(accItem.getAccId())) {
                Response resp = getAPIResponse("DELETE", FEEDBACK_URL + feedBackId, "", "", "", userRole);
                if (resp.getStatusCode() == 200)
                    System.out.println("Feedback Deleted Successfully");
            }
        });

        return this;
    }

    public LifeCycleUtils deleteACCFeedback(ACCPoJo.ItemsItem accItem) {
        deleteACCFeedback(accItem, DEFAULT_USER_ROLE);
        return this;
    }

    public static ElearningPoJo getElearningResponse(String customerId, String solution, String useCase, String pitStop, String userRole) {
        String params = getParams(customerId, solution, useCase, pitStop);
        Response response = getAPIResponse("GET", ELEARNING_URL, "", params, "", userRole);

        if (response.getStatusCode() == 200)
            System.out.println("learning list  retrieved Successfully");
        else
            System.out.println("Failed to retrieve learning");

        return response.as(ElearningPoJo.class, ObjectMapperType.JACKSON_2);
    }
    
    public LifeCycleUtils deleteELearningFeedback(ElearningPoJo.ItemsItem eLearningItem, String userRole) {
        String params = getParams(eLearningItem.getCustomerId(), eLearningItem.getSolution(), eLearningItem.getUsecase(), eLearningItem.getPitstop());
        Response response = getAPIResponse("GET", ELEARNING_URL, "", params, "", userRole);
        List<String> feedBackIds = response.jsonPath().getList("items.feedbackInfo.feedbackId.flatten()");
        feedBackIds = feedBackIds.stream().filter(id -> !id.isEmpty()).collect(Collectors.toList());
        feedBackIds.forEach(feedBackId -> {
            if (feedBackId.contains(eLearningItem.getId())) {
                Response resp = getAPIResponse("DELETE", FEEDBACK_URL + feedBackId, "", "", "", userRole);
                if (resp.getStatusCode() == 200)
                    System.out.println("Feedback Deleted Successfully");
            }
        });

        return this;
    }
    
    public LifeCycleUtils deleteELearningFeedback(ElearningPoJo.ItemsItem eLearningItem) {
    	deleteELearningFeedback(eLearningItem, DEFAULT_USER_ROLE);
        return this;
    }

    public static SuccessTipsPoJo getSuccessTipsResponse(String customerId, String solution, String useCase, String pitStop, String userRole) {
        String params = getParams(customerId, solution, useCase, pitStop) + "&rows=500";
        Response response = getAPIResponse("GET", SUCCESS_TIPS_URL, "", params, "", userRole);

        if (response.getStatusCode() == 200)
            System.out.println("Success Tips list  retrieved Successfully");
        else
            System.out.println("Failed to retrieve Success Tips");

        return response.as(SuccessTipsPoJo.class, ObjectMapperType.JACKSON_2);
    }

    public static CommunityListPoJo getCommunityResponse(String customerId, String solution, String useCase, String pitStop, String userRole, String type) {
        String params = getParams(customerId, solution, useCase, pitStop) + "&limit=1";
        Response response = getAPIResponse("GET", COMMUNITY_URL + type + "/list", "", params, "", userRole);

        if (response.getStatusCode() == 200)
            System.out.println("Community list  retrieved Successfully");
        else
            System.out.println("Failed to retrieve ACC");

        return response.as(CommunityListPoJo[].class, ObjectMapperType.JACKSON_2)[0];
    }

    @Step("Refresh Lifecycle Page")
    public LifeCycleUtils refreshLifecycleTiles() {
        selectCarousel(CarouselName.ASSETS_COVERAGE);
        selectCarousel(CarouselName.LIFECYCLE);
        return this;
    }
}