package com.cisco.testdata.lifecycle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityListPoJo {

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

	@JsonProperty("searchSnippet")
	private String searchSnippet;

	@JsonProperty("postTimeFriendly")
	private String postTimeFriendly;

	@JsonProperty("author")
	private Author author;

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("language")
	private String language;

	@JsonProperty("body")
	private String body;

	@JsonProperty("viewHref")
	private String viewHref;

	@JsonProperty("bookmark")
	private boolean bookmark;

	@JsonProperty("postTime")
	private String postTime;

	@JsonProperty("topicId")
	private String topicId;

	@JsonProperty("popularity")
	private double popularity;

	@JsonProperty("topic")
	private Topic topic;

	@JsonProperty("id")
	private String id;

	@JsonProperty("metrics")
	private Metrics metrics;

	@JsonProperty("conversation")
	private Conversation conversation;

	@JsonProperty("teaser")
	private String teaser;

	public String getSearchSnippet(){
		return searchSnippet;
	}

	public String getPostTimeFriendly(){
		return postTimeFriendly;
	}

	public Author getAuthor(){
		return author;
	}

	public String getSubject(){
		return subject;
	}

	public String getLanguage(){
		return language;
	}

	public String getBody(){
		return body;
	}

	public String getViewHref(){
		return viewHref;
	}

	public boolean isBookmark(){
		return bookmark;
	}

	public String getPostTime(){
		return postTime;
	}

	public String getTopicId(){
		return topicId;
	}

	public double getPopularity(){
		return popularity;
	}

	public Topic getTopic(){
		return topic;
	}

	public String getId(){
		return id;
	}

	public Metrics getMetrics(){
		return metrics;
	}

	public Conversation getConversation(){
		return conversation;
	}

	public String getTeaser(){
		return teaser;
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

	public void setSearchSnippet(String searchSnippet) {
		this.searchSnippet = searchSnippet;
	}

	public void setPostTimeFriendly(String postTimeFriendly) {
		this.postTimeFriendly = postTimeFriendly;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setViewHref(String viewHref) {
		this.viewHref = viewHref;
	}

	public void setBookmark(boolean bookmark) {
		this.bookmark = bookmark;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	public static class Metrics{

        @JsonProperty("type")
        private String type;

        @JsonProperty("views")
        private double views;

        public String getType(){
            return type;
        }

        public double getViews(){
            return views;
        }
    }

	public static class Topic{

		@JsonProperty("viewHref")
		private String viewHref;

		@JsonProperty("id")
		private String id;

		@JsonProperty("href")
		private String href;

		@JsonProperty("type")
		private String type;

		public String getViewHref(){
			return viewHref;
		}

		public String getId(){
			return id;
		}

		public String getHref(){
			return href;
		}

		public String getType(){
			return type;
		}
	}

	public static class Conversation{

		@JsonProperty("lastPostTime")
		private String lastPostTime;

		@JsonProperty("viewHref")
		private String viewHref;

		@JsonProperty("threadStyle")
		private String threadStyle;

		@JsonProperty("solved")
		private boolean solved;

		@JsonProperty("style")
		private String style;

		@JsonProperty("lastPostTimeFriendly")
		private String lastPostTimeFriendly;

		@JsonProperty("id")
		private String id;

		@JsonProperty("messagesCount")
		private double messagesCount;

		@JsonProperty("type")
		private String type;

		public String getLastPostTime(){
			return lastPostTime;
		}

		public String getViewHref(){
			return viewHref;
		}

		public String getThreadStyle(){
			return threadStyle;
		}

		public boolean isSolved(){
			return solved;
		}

		public String getStyle(){
			return style;
		}

		public String getLastPostTimeFriendly(){
			return lastPostTimeFriendly;
		}

		public String getId(){
			return id;
		}

		public double getMessagesCount(){
			return messagesCount;
		}

		public String getType(){
			return type;
		}
	}

	public static class Author{

		@JsonProperty("viewHref")
		private String viewHref;

		@JsonProperty("id")
		private String id;

		@JsonProperty("href")
		private String href;

		@JsonProperty("type")
		private String type;

		@JsonProperty("login")
		private String login;

		public String getViewHref(){
			return viewHref;
		}

		public String getId(){
			return id;
		}

		public String getHref(){
			return href;
		}

		public String getType(){
			return type;
		}

		public String getLogin(){
			return login;
		}
	}
}