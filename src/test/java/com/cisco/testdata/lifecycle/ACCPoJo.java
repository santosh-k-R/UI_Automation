package com.cisco.testdata.lifecycle;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ACCPoJo{

	@JsonProperty("pitstop")
	private String pitstop;

	@JsonProperty("usecase")
	private String usecase;

	@JsonProperty("solution")
	private String solution;

    @JsonProperty("buId")
    private String buId;

    @JsonProperty("cavId")
    private String cavId;

    @JsonProperty("customerId")
    private String customerId;

	@JsonProperty("items")
	private List<ItemsItem> items;

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public String getCavId() {
        return cavId;
    }

    public void setCavId(String cavId) {
        this.cavId = cavId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setPitstop(String pitstop){
		this.pitstop = pitstop;
	}

	public String getPitstop(){
		return pitstop;
	}

	public void setUsecase(String usecase){
		this.usecase = usecase;
	}

	public String getUsecase(){
		return usecase;
	}

	public void setSolution(String solution){
		this.solution = solution;
	}

	public String getSolution(){
		return solution;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

    public static class ItemsItem{

        @JsonProperty("remainingRequestCount")
        private int remainingRequestCount;

        @JsonProperty("cumulativeFeedbackAvailable")
        private boolean cumulativeFeedbackAvailable;

        @JsonProperty("description")
        private String description;

        @JsonProperty("title")
        private String title;

        @JsonProperty("url")
        private String url;

        @JsonProperty("cumulativeFeedbackCount")
        private int cumulativeFeedbackCount;

        @JsonProperty("feedbackInfo")
        private Object feedbackInfo;

        @JsonProperty("feedbackEngagement")
        private double feedbackEngagement;

        @JsonProperty("timeRequired")
        private String timeRequired;

        @JsonProperty("prerequisites")
        private String prerequisites;

        @JsonProperty("bookmark")
        private boolean bookmark;

        @JsonProperty("engagementList")
        private Object engagementList;

        @JsonProperty("requestedByCcoId")
        private Object requestedByCcoId;

        @JsonProperty("solution")
        private String solution;

        @JsonProperty("datasheetURL")
        private String datasheetURL;

        @JsonProperty("assetId")
        private Object assetId;

        @JsonProperty("requestId")
        private String requestId;

        @JsonProperty("requestedByStatus")
        private Object requestedByStatus;

        @JsonProperty("assetCompletionCount")
        private int assetCompletionCount;

        @JsonProperty("accId")
        private String accId;

        @JsonProperty("requestedByName")
        private Object requestedByName;

        @JsonProperty("technologyArea")
        private Object technologyArea;

        @JsonProperty("status")
        private String status;

        @JsonProperty("providerInfo")
        private Object providerInfo;

        @JsonProperty("businessOutcome")
        private String businessOutcome;

        @JsonProperty("environment")
        private String environment;

        @JsonProperty("preferredLanguage")
        private String preferredLanguage;

        @JsonProperty("preferredSlot")
        private String preferredSlot;

        @JsonProperty("reasonForInterest")
        private String reasonForInterest;

        @JsonProperty("timezone")
        private String timezone;

        @JsonProperty("providerId")
        private String providerId;

        @JsonProperty("providerName")
        private String providerName;

        @JsonProperty("pitstop")
        private String pitstop;

        @JsonProperty("usecase")
        private String usecase;

        @JsonProperty("buId")
        private String buId;

        @JsonProperty("cavId")
        private String cavId;

        @JsonProperty("customerId")
        private String customerId;

        public String getPitstop() {
            return pitstop;
        }

        public void setPitstop(String pitstop) {
            this.pitstop = pitstop;
        }

        public String getUsecase() {
            return usecase;
        }

        public void setUsecase(String usecase) {
            this.usecase = usecase;
        }

        public String getBuId() {
            return buId;
        }

        public void setBuId(String buId) {
            this.buId = buId;
        }

        public String getCavId() {
            return cavId;
        }

        public void setCavId(String cavId) {
            this.cavId = cavId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getBusinessOutcome() {
            return businessOutcome;
        }

        public void setBusinessOutcome(String businessOutcome) {
            this.businessOutcome = businessOutcome;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getPreferredLanguage() {
            return preferredLanguage;
        }

        public void setPreferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
        }

        public String getPreferredSlot() {
            return preferredSlot;
        }

        public void setPreferredSlot(String preferredSlot) {
            this.preferredSlot = preferredSlot;
        }

        public String getReasonForInterest() {
            return reasonForInterest;
        }

        public void setReasonForInterest(String reasonForInterest) {
            this.reasonForInterest = reasonForInterest;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public List<String> getAdditionalAttendees() {
            return additionalAttendees;
        }

        public void setAdditionalAttendees(List<String> additionalAttendees) {
            this.additionalAttendees = additionalAttendees;
        }

        @JsonProperty("additionalAttendees")
        private List<String> additionalAttendees;



        public void setRemainingRequestCount(int remainingRequestCount){
            this.remainingRequestCount = remainingRequestCount;
        }

        public int getRemainingRequestCount(){
            return remainingRequestCount;
        }

        public void setCumulativeFeedbackAvailable(boolean cumulativeFeedbackAvailable){
            this.cumulativeFeedbackAvailable = cumulativeFeedbackAvailable;
        }

        public boolean isCumulativeFeedbackAvailable(){
            return cumulativeFeedbackAvailable;
        }

        public void setDescription(String description){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }

        public void setUrl(String url){
            this.url = url;
        }

        public String getUrl(){
            return url;
        }

        public void setCumulativeFeedbackCount(int cumulativeFeedbackCount){
            this.cumulativeFeedbackCount = cumulativeFeedbackCount;
        }

        public int getCumulativeFeedbackCount(){
            return cumulativeFeedbackCount;
        }

        public void setFeedbackInfo(Object feedbackInfo){
            this.feedbackInfo = feedbackInfo;
        }

        public Object getFeedbackInfo(){
            return feedbackInfo;
        }

        public void setFeedbackEngagement(double feedbackEngagement){
            this.feedbackEngagement = feedbackEngagement;
        }

        public double getFeedbackEngagement(){
            return feedbackEngagement;
        }

        public void setTimeRequired(String timeRequired){
            this.timeRequired = timeRequired;
        }

        public String getTimeRequired(){
            return timeRequired;
        }

        public void setPrerequisites(String prerequisites){
            this.prerequisites = prerequisites;
        }

        public String getPrerequisites(){
            return prerequisites;
        }

        public void setBookmark(boolean bookmark){
            this.bookmark = bookmark;
        }

        public boolean isBookmark(){
            return bookmark;
        }

        public void setEngagementList(Object engagementList){
            this.engagementList = engagementList;
        }

        public Object getEngagementList(){
            return engagementList;
        }

        public void setRequestedByCcoId(Object requestedByCcoId){
            this.requestedByCcoId = requestedByCcoId;
        }

        public Object getRequestedByCcoId(){
            return requestedByCcoId;
        }

        public void setSolution(String solution){
            this.solution = solution;
        }

        public String getSolution(){
            return solution;
        }

        public void setDatasheetURL(String datasheetURL){
            this.datasheetURL = datasheetURL;
        }

        public String getDatasheetURL(){
            return datasheetURL;
        }

        public void setAssetId(Object assetId){
            this.assetId = assetId;
        }

        public Object getAssetId(){
            return assetId;
        }

        public void setRequestId(String requestId){
            this.requestId = requestId;
        }

        public String getRequestId(){
            return requestId;
        }

        public void setRequestedByStatus(Object requestedByStatus){
            this.requestedByStatus = requestedByStatus;
        }

        public Object getRequestedByStatus(){
            return requestedByStatus;
        }

        public void setAssetCompletionCount(int assetCompletionCount){
            this.assetCompletionCount = assetCompletionCount;
        }

        public int getAssetCompletionCount(){
            return assetCompletionCount;
        }

        public void setAccId(String accId){
            this.accId = accId;
        }

        public String getAccId(){
            return accId;
        }

        public void setRequestedByName(Object requestedByName){
            this.requestedByName = requestedByName;
        }

        public Object getRequestedByName(){
            return requestedByName;
        }

        public void setTechnologyArea(Object technologyArea){
            this.technologyArea = technologyArea;
        }

        public Object getTechnologyArea(){
            return technologyArea;
        }

        public void setStatus(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }

        public void setProviderInfo(Object providerInfo){
            this.providerInfo = providerInfo;
        }

        public Object getProviderInfo(){
            return providerInfo;
        }
    }

    public static class FeedbackInfo{

        @JsonProperty("closedDate")
        private Object closedDate;

        @JsonProperty("available")
        private boolean available;

        @JsonProperty("feedbackId")
        private String feedbackId;

        @JsonProperty("sessionId")
        private Object sessionId;

        @JsonProperty("requestedDate")
        private long requestedDate;

        @JsonProperty("presenterName")
        private Object presenterName;

        @JsonProperty("transactionId")
        private String transactionId;

        @JsonProperty("thumbs")
        private String thumbs;

        public void setClosedDate(Object closedDate){
            this.closedDate = closedDate;
        }

        public Object getClosedDate(){
            return closedDate;
        }

        public void setAvailable(boolean available){
            this.available = available;
        }

        public boolean isAvailable(){
            return available;
        }

        public void setFeedbackId(String feedbackId){
            this.feedbackId = feedbackId;
        }

        public String getFeedbackId(){
            return feedbackId;
        }

        public void setSessionId(Object sessionId){
            this.sessionId = sessionId;
        }

        public Object getSessionId(){
            return sessionId;
        }

        public void setRequestedDate(long requestedDate){
            this.requestedDate = requestedDate;
        }

        public long getRequestedDate(){
            return requestedDate;
        }

        public void setPresenterName(Object presenterName){
            this.presenterName = presenterName;
        }

        public Object getPresenterName(){
            return presenterName;
        }

        public void setTransactionId(String transactionId){
            this.transactionId = transactionId;
        }

        public String getTransactionId(){
            return transactionId;
        }

        public void setThumbs(String thumbs){
            this.thumbs = thumbs;
        }

        public String getThumbs(){
            return thumbs;
        }
    }

    public static class ProviderInfo{

        @JsonProperty("name")
        private String name;

        @JsonProperty("id")
        private String id;

        @JsonProperty("logoURL")
        private Object logoURL;

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getId(){
            return id;
        }

        public void setLogoURL(Object logoURL){
            this.logoURL = logoURL;
        }

        public Object getLogoURL(){
            return logoURL;
        }
    }
}