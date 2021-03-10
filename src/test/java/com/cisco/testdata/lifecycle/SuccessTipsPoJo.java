package com.cisco.testdata.lifecycle;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessTipsPoJo{

	@JsonProperty("pitstop")
	private String pitstop;

	@JsonProperty("usecase")
	private String usecase;

	@JsonProperty("solution")
	private String solution;

	@JsonProperty("totalCount")
	private int totalCount;

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

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	public static class ItemsItem{

		@JsonProperty("duration")
		private String duration;

		@JsonProperty("bookmark")
		private boolean bookmark;

		@JsonProperty("successByteId")
		private String successByteId;

		@JsonProperty("archetype")
		private String archetype;

		@JsonProperty("description")
		private String description;

		@JsonProperty("title")
		private String title;

		@JsonProperty("type")
		private String type;

		@JsonProperty("url")
		private String url;

		@JsonProperty("buId")
		private String buId;

		@JsonProperty("cavId")
		private String cavId;

		@JsonProperty("customerId")
		private String customerId;

		@JsonProperty("pitstop")
		private String pitstop;

		@JsonProperty("usecase")
		private String usecase;

		@JsonProperty("solution")
		private String solution;

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

		public void setDuration(String duration){
			this.duration = duration;
		}

		public String getDuration(){
			return duration;
		}

		public void setBookmark(boolean bookmark){
			this.bookmark = bookmark;
		}

		public boolean isBookmark(){
			return bookmark;
		}

		public void setSuccessByteId(String successByteId){
			this.successByteId = successByteId;
		}

		public String getSuccessByteId(){
			return successByteId;
		}

		public void setArchetype(String archetype){
			this.archetype = archetype;
		}

		public String getArchetype(){
			return archetype;
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

		public void setUrl(String url){
			this.url = url;
		}

		public String getUrl(){
			return url;
		}
	}
}