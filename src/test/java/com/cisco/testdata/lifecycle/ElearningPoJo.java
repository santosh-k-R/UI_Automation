package com.cisco.testdata.lifecycle;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ElearningPoJo{

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

        public void setRequestedDate(Object requestedDate){
            this.requestedDate = requestedDate;
        }

        public Object getRequestedDate(){
            return requestedDate;
        }

        public void setPresenterName(Object presenterName){
            this.presenterName = presenterName;
        }

        public Object getPresenterName(){
            return presenterName;
        }

        public void setTransactionId(Object transactionId){
            this.transactionId = transactionId;
        }

        public Object getTransactionId(){
            return transactionId;
        }

        public void setThumbs(String thumbs){
            this.thumbs = thumbs;
        }

        public String getThumbs(){
            return thumbs;
        }
    }

    public static class ItemsItem{

        @JsonProperty("upvotes")
        private String upvotes;

        @JsonProperty("cumulativeFeedbackAvailable")
        private boolean cumulativeFeedbackAvailable;

        @JsonProperty("rating")
        private String rating;

        @JsonProperty("description")
        private String description;

        @JsonProperty("title")
        private String title;

        @JsonProperty("type")
        private String type;

        @JsonProperty("feedbackInfo")
        private Object feedbackInfo;

        @JsonProperty("labOutline")
        private Object labOutline;

        @JsonProperty("duration")
        private String duration;

        @JsonProperty("usersCompleted")
        private int usersCompleted;

        @JsonProperty("reviewCount")
        private int reviewCount;

        @JsonProperty("assetCompletionCount")
        private int assetCompletionCount;

        @JsonProperty("id")
        private String id;

        @JsonProperty("courseId")
        private String courseId;

        @JsonProperty("cxlevel")
        private int cxlevel;

        @JsonProperty("percentagecompleted")
        private int percentagecompleted;

        @JsonProperty("overview")
        private Overview overview;

        @JsonProperty("deliveryType")
        private String deliveryType;

        @JsonProperty("courseOutline")
        private List<CourseOutlineItem> courseOutline;

        @JsonProperty("url")
        private String url;

        @JsonProperty("feedbackEngagement")
        private double feedbackEngagement;

        @JsonProperty("bookmark")
        private boolean bookmark;

        @JsonProperty("remoteLabLastLaunched")
        private Object remoteLabLastLaunched;

        @JsonProperty("ranking")
        private int ranking;

        @JsonProperty("remoteLabLaunched")
        private boolean remoteLabLaunched;

        @JsonProperty("launchUrl")
        private String launchUrl;

        @JsonProperty("status")
        private String status;

        @JsonProperty("pitstop")
        private String pitstop;

        @JsonProperty("usecase")
        private String usecase;

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

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
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

        @JsonProperty("solution")
        private String solution;

        @JsonProperty("buId")
        private String buId;

        @JsonProperty("cavId")
        private String cavId;

        @JsonProperty("customerId")
        private String customerId;

        public void setUpvotes(String upvotes){
            this.upvotes = upvotes;
        }

        public String getUpvotes(){
            return upvotes;
        }

        public void setCumulativeFeedbackAvailable(boolean cumulativeFeedbackAvailable){
            this.cumulativeFeedbackAvailable = cumulativeFeedbackAvailable;
        }

        public boolean isCumulativeFeedbackAvailable(){
            return cumulativeFeedbackAvailable;
        }

        public void setRating(String rating){
            this.rating = rating;
        }

        public String getRating(){
            return rating;
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

        public void setType(String type){
            this.type = type;
        }

        public String getType(){
            return type;
        }

        public void setFeedbackInfo(Object feedbackInfo){
            this.feedbackInfo = feedbackInfo;
        }

        public Object getFeedbackInfo(){
            return feedbackInfo;
        }

        public void setLabOutline(Object labOutline){
            this.labOutline = labOutline;
        }

        public Object getLabOutline(){
            return labOutline;
        }

        public void setDuration(String duration){
            this.duration = duration;
        }

        public String getDuration(){
            return duration;
        }

        public void setUsersCompleted(int usersCompleted){
            this.usersCompleted = usersCompleted;
        }

        public int getUsersCompleted(){
            return usersCompleted;
        }

        public void setReviewCount(int reviewCount){
            this.reviewCount = reviewCount;
        }

        public int getReviewCount(){
            return reviewCount;
        }

        public void setAssetCompletionCount(int assetCompletionCount){
            this.assetCompletionCount = assetCompletionCount;
        }

        public int getAssetCompletionCount(){
            return assetCompletionCount;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getId(){
            return id;
        }

        public void setCourseId(String courseId){
            this.courseId = courseId;
        }

        public String getCourseId(){
            return courseId;
        }

        public void setCxlevel(int cxlevel){
            this.cxlevel = cxlevel;
        }

        public int getCxlevel(){
            return cxlevel;
        }

        public void setPercentagecompleted(int percentagecompleted){
            this.percentagecompleted = percentagecompleted;
        }

        public int getPercentagecompleted(){
            return percentagecompleted;
        }

        public void setOverview(Overview overview){
            this.overview = overview;
        }

        public Overview getOverview(){
            return overview;
        }

        public void setDeliveryType(String deliveryType){
            this.deliveryType = deliveryType;
        }

        public String getDeliveryType(){
            return deliveryType;
        }

        public void setCourseOutline(List<CourseOutlineItem> courseOutline){
            this.courseOutline = courseOutline;
        }

        public List<CourseOutlineItem> getCourseOutline(){
            return courseOutline;
        }

        public void setUrl(String url){
            this.url = url;
        }

        public String getUrl(){
            return url;
        }

        public void setFeedbackEngagement(double feedbackEngagement){
            this.feedbackEngagement = feedbackEngagement;
        }

        public double getFeedbackEngagement(){
            return feedbackEngagement;
        }

        public void setBookmark(boolean bookmark){
            this.bookmark = bookmark;
        }

        public boolean isBookmark(){
            return bookmark;
        }

        public void setRemoteLabLastLaunched(Object remoteLabLastLaunched){
            this.remoteLabLastLaunched = remoteLabLastLaunched;
        }

        public Object getRemoteLabLastLaunched(){
            return remoteLabLastLaunched;
        }

        public void setRanking(int ranking){
            this.ranking = ranking;
        }

        public int getRanking(){
            return ranking;
        }

        public void setRemoteLabLaunched(boolean remoteLabLaunched){
            this.remoteLabLaunched = remoteLabLaunched;
        }

        public boolean isRemoteLabLaunched(){
            return remoteLabLaunched;
        }

        public void setLaunchUrl(String launchUrl){
            this.launchUrl = launchUrl;
        }

        public String getLaunchUrl(){
            return launchUrl;
        }

        public void setStatus(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }
    }

    public static class LabOutlineItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class ObjectivesItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class Overview{

        @JsonProperty("duration")
        private List<DurationItem> duration;

        @JsonProperty("targetAudience")
        private List<TargetAudienceItem> targetAudience;

        @JsonProperty("coursePrerequisites")
        private List<CoursePrerequisitesItem> coursePrerequisites;

        @JsonProperty("description")
        private List<DescriptionItem> description;

        @JsonProperty("objectives")
        private List<ObjectivesItem> objectives;

        public void setDuration(List<DurationItem> duration){
            this.duration = duration;
        }

        public List<DurationItem> getDuration(){
            return duration;
        }

        public void setTargetAudience(List<TargetAudienceItem> targetAudience){
            this.targetAudience = targetAudience;
        }

        public List<TargetAudienceItem> getTargetAudience(){
            return targetAudience;
        }

        public void setCoursePrerequisites(List<CoursePrerequisitesItem> coursePrerequisites){
            this.coursePrerequisites = coursePrerequisites;
        }

        public List<CoursePrerequisitesItem> getCoursePrerequisites(){
            return coursePrerequisites;
        }

        public void setDescription(List<DescriptionItem> description){
            this.description = description;
        }

        public List<DescriptionItem> getDescription(){
            return description;
        }

        public void setObjectives(List<ObjectivesItem> objectives){
            this.objectives = objectives;
        }

        public List<ObjectivesItem> getObjectives(){
            return objectives;
        }
    }

    public static class TargetAudienceItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class DurationItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class DescriptionItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class CoursePrerequisitesItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }

    public static class CourseOutlineItem{

        @JsonProperty("html_element")
        private String htmlElement;

        @JsonProperty("sort_order")
        private String sortOrder;

        @JsonProperty("content")
        private String content;

        public void setHtmlElement(String htmlElement){
            this.htmlElement = htmlElement;
        }

        public String getHtmlElement(){
            return htmlElement;
        }

        public void setSortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder(){
            return sortOrder;
        }

        public void setContent(String content){
            this.content = content;
        }

        public String getContent(){
            return content;
        }
    }
}