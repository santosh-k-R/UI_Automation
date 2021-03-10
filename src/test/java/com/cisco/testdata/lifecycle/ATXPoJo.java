package com.cisco.testdata.lifecycle;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ATXPoJo {

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

	public String getPitstop(){
		return pitstop;
	}

	public String getUsecase(){
		return usecase;
	}

	public String getSolution(){
		return solution;
	}

	public void setPitstop(String pitstop) {
		this.pitstop = pitstop;
	}

	public void setUsecase(String usecase) {
		this.usecase = usecase;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setItems(List<ItemsItem> items) {
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	public static class ItemsItem{

		@JsonProperty("sessions")
		private List<SessionsItem> sessions;

		@JsonProperty("cumulativeFeedbackAvailable")
		private boolean cumulativeFeedbackAvailable;

		@JsonProperty("description")
		private String description;

		@JsonProperty("title")
		private String title;

		@JsonProperty("atxId")
		private String atxId;

		@JsonProperty("cumulativeFeedbackCount")
		private int cumulativeFeedbackCount;

		@JsonProperty("feedbackInfo")
		private FeedbackInfo feedbackInfo;

		@JsonProperty("feedbackEngagement")
		private double feedbackEngagement;

		@JsonProperty("duration")
		private int duration;

		@JsonProperty("bookmark")
		private boolean bookmark;

		@JsonProperty("videoURL")
		private VideoURL videoURL;

		@JsonProperty("assetId")
		private Object assetId;

		@JsonProperty("imageURL")
		private String imageURL;

		@JsonProperty("recordingURL")
		private String recordingURL;

		@JsonProperty("assetCompletionCount")
		private int assetCompletionCount;

		@JsonProperty("technologyArea")
		private String technologyArea;

		@JsonProperty("status")
		private String status;

		@JsonProperty("providerInfo")
		private Object providerInfo;

		@JsonProperty("pitstop")
		private String pitstop;

		@JsonProperty("usecase")
		private String usecase;

		@JsonProperty("buId")
		private String buId;

		@JsonProperty("cavId")
		private String cavId;

		public String getSolution() {
			return solution;
		}

		public void setSolution(String solution) {
			this.solution = solution;
		}

		@JsonProperty("solution")
		private String solution;

		public void setSessions(List<SessionsItem> sessions) {
			this.sessions = sessions;
		}

		public void setCumulativeFeedbackAvailable(boolean cumulativeFeedbackAvailable) {
			this.cumulativeFeedbackAvailable = cumulativeFeedbackAvailable;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setAtxId(String atxId) {
			this.atxId = atxId;
		}

		public void setCumulativeFeedbackCount(int cumulativeFeedbackCount) {
			this.cumulativeFeedbackCount = cumulativeFeedbackCount;
		}

		public void setFeedbackInfo(FeedbackInfo feedbackInfo) {
			this.feedbackInfo = feedbackInfo;
		}

		public void setFeedbackEngagement(double feedbackEngagement) {
			this.feedbackEngagement = feedbackEngagement;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public void setBookmark(boolean bookmark) {
			this.bookmark = bookmark;
		}

		public void setVideoURL(VideoURL videoURL) {
			this.videoURL = videoURL;
		}

		public void setAssetId(Object assetId) {
			this.assetId = assetId;
		}

		public void setImageURL(String imageURL) {
			this.imageURL = imageURL;
		}

		public void setRecordingURL(String recordingURL) {
			this.recordingURL = recordingURL;
		}

		public void setAssetCompletionCount(int assetCompletionCount) {
			this.assetCompletionCount = assetCompletionCount;
		}

		public void setTechnologyArea(String technologyArea) {
			this.technologyArea = technologyArea;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public void setProviderInfo(Object providerInfo) {
			this.providerInfo = providerInfo;
		}

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

		@JsonProperty("customerId")
		private String customerId;

		public List<SessionsItem> getSessions(){
			return sessions;
		}

		public boolean isCumulativeFeedbackAvailable(){
			return cumulativeFeedbackAvailable;
		}

		public String getDescription(){
			return description;
		}

		public String getTitle(){
			return title;
		}

		public String getAtxId(){
			return atxId;
		}

		public int getCumulativeFeedbackCount(){
			return cumulativeFeedbackCount;
		}

		public FeedbackInfo getFeedbackInfo(){
			return feedbackInfo;
		}

		public double getFeedbackEngagement(){
			return feedbackEngagement;
		}

		public int getDuration(){
			return duration;
		}

		public boolean isBookmark(){
			return bookmark;
		}

		public VideoURL getVideoURL(){
			return videoURL;
		}

		public Object getAssetId(){
			return assetId;
		}

		public String getImageURL(){
			return imageURL;
		}

		public String getRecordingURL(){
			return recordingURL;
		}

		public int getAssetCompletionCount(){
			return assetCompletionCount;
		}

		public String getTechnologyArea(){
			return technologyArea;
		}

		public String getStatus(){
			return status;
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
		private Object requestedDate;

		@JsonProperty("presenterName")
		private Object presenterName;

		@JsonProperty("transactionId")
		private Object transactionId;

		@JsonProperty("thumbs")
		private String thumbs;

		public Object getClosedDate(){
			return closedDate;
		}

		public boolean isAvailable(){
			return available;
		}

		public String getFeedbackId(){
			return feedbackId;
		}

		public Object getSessionId(){
			return sessionId;
		}

		public Object getRequestedDate(){
			return requestedDate;
		}

		public Object getPresenterName(){
			return presenterName;
		}

		public Object getTransactionId(){
			return transactionId;
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

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}

		public Object getLogoURL(){
			return logoURL;
		}
	}

	public static class SessionsItem{

		@JsonProperty("sessionStartDate")
		private long sessionStartDate;

		@JsonProperty("eventNumber")
		private String eventNumber;

		@JsonProperty("scheduled")
		private boolean scheduled;

		@JsonProperty("presenterJobTitle")
		private Object presenterJobTitle;

		@JsonProperty("sessionId")
		private String sessionId;

		@JsonProperty("presenterInfoText")
		private Object presenterInfoText;

		@JsonProperty("presenterName")
		private String presenterName;

		@JsonProperty("presenterImageURL")
		private Object presenterImageURL;

		@JsonProperty("region")
		private Object region;

		@JsonProperty("presenterDomain")
		private Object presenterDomain;

		@JsonProperty("registrationURL")
		private Object registrationURL;

		@JsonProperty("status")
		private String status;

		public long getSessionStartDate(){
			return sessionStartDate;
		}

		public String getEventNumber(){
			return eventNumber;
		}

		public boolean isScheduled(){
			return scheduled;
		}

		public Object getPresenterJobTitle(){
			return presenterJobTitle;
		}

		public String getSessionId(){
			return sessionId;
		}

		public Object getPresenterInfoText(){
			return presenterInfoText;
		}

		public String getPresenterName(){
			return presenterName;
		}

		public Object getPresenterImageURL(){
			return presenterImageURL;
		}

		public Object getRegion(){
			return region;
		}

		public Object getPresenterDomain(){
			return presenterDomain;
		}

		public Object getRegistrationURL(){
			return registrationURL;
		}

		public String getStatus(){
			return status;
		}
	}

	public static class VideoURL{

		@JsonProperty("accountId")
		private String accountId;

		@JsonProperty("videoId")
		private String videoId;

		@JsonProperty("playerId")
		private String playerId;

		public String getAccountId(){
			return accountId;
		}

		public String getVideoId(){
			return videoId;
		}

		public String getPlayerId(){
			return playerId;
		}
	}
}