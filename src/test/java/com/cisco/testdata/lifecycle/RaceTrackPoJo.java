package com.cisco.testdata.lifecycle;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RaceTrackPoJo{

	@JsonProperty("buId")
	private String buId;

	@JsonProperty("cavId")
	private String cavId;

	@JsonProperty("createdDate")
	private String createdDate;

	@JsonProperty("processedOn")
	private String processedOn;

	@JsonProperty("customerId")
	private String customerId;

	@JsonProperty("items")
	private List<ItemsItem> items;

	@JsonProperty("buName")
	private String buName;

	public void setBuId(String buId){
		this.buId = buId;
	}

	public String getBuId(){
		return buId;
	}

	public void setCavId(String cavId){
		this.cavId = cavId;
	}

	public String getCavId(){
		return cavId;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setProcessedOn(String processedOn){
		this.processedOn = processedOn;
	}

	public String getProcessedOn(){
		return processedOn;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	public void setBuName(String buName){
		this.buName = buName;
	}

	public String getBuName(){
		return buName;
	}

    public static class ItemsItem{

        @JsonProperty("cxLevel")
        private int cxLevel;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("solutionId")
        private String solutionId;

        @JsonProperty("usecases")
        private List<UsecasesItem> usecases;

        public void setCxLevel(int cxLevel){
            this.cxLevel = cxLevel;
        }

        public int getCxLevel(){
            return cxLevel;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public void setDescription(String description){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }

        public void setSolutionId(String solutionId){
            this.solutionId = solutionId;
        }

        public String getSolutionId(){
            return solutionId;
        }

        public void setUsecases(List<UsecasesItem> usecases){
            this.usecases = usecases;
        }

        public List<UsecasesItem> getUsecases(){
            return usecases;
        }
    }

	public static class PitstopsItem{

		@JsonProperty("pitstopActions")
		private List<PitstopActionsItem> pitstopActions;

		@JsonProperty("completionPercentage")
		private int completionPercentage;

		@JsonProperty("sequence")
		private Object sequence;

		@JsonProperty("name")
		private String name;

		@JsonProperty("description")
		private String description;

		@JsonProperty("isPitstopComplete")
		private boolean isPitstopComplete;

		public void setPitstopActions(List<PitstopActionsItem> pitstopActions){
			this.pitstopActions = pitstopActions;
		}

		public List<PitstopActionsItem> getPitstopActions(){
			return pitstopActions;
		}

		public void setCompletionPercentage(int completionPercentage){
			this.completionPercentage = completionPercentage;
		}

		public int getCompletionPercentage(){
			return completionPercentage;
		}

		public void setSequence(Object sequence){
			this.sequence = sequence;
		}

		public Object getSequence(){
			return sequence;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}

		public void setIsPitstopComplete(boolean isPitstopComplete){
			this.isPitstopComplete = isPitstopComplete;
		}

		public boolean isIsPitstopComplete(){
			return isPitstopComplete;
		}
	}

	public static class PitstopActionsItem{

		@JsonProperty("pitstopActionId")
		private String pitstopActionId;

		@JsonProperty("sequenceNumber")
		private int sequenceNumber;

		@JsonProperty("isManualOverride")
		private boolean isManualOverride;

		@JsonProperty("isCompleteManual")
		private boolean isCompleteManual;

		@JsonProperty("isCompleteAuto")
		private boolean isCompleteAuto;

		@JsonProperty("name")
		private String name;

		@JsonProperty("description")
		private String description;

		@JsonProperty("isManualCheckAllowed")
		private boolean isManualCheckAllowed;

		@JsonProperty("tooltips")
		private List<TooltipsItem> tooltips;

		@JsonProperty("updateMethod")
		private Object updateMethod;

		@JsonProperty("isComplete")
		private boolean isComplete;

		public void setPitstopActionId(String pitstopActionId){
			this.pitstopActionId = pitstopActionId;
		}

		public String getPitstopActionId(){
			return pitstopActionId;
		}

		public void setSequenceNumber(int sequenceNumber){
			this.sequenceNumber = sequenceNumber;
		}

		public int getSequenceNumber(){
			return sequenceNumber;
		}

		public void setIsManualOverride(boolean isManualOverride){
			this.isManualOverride = isManualOverride;
		}

		public boolean isIsManualOverride(){
			return isManualOverride;
		}

		public void setIsCompleteManual(boolean isCompleteManual){
			this.isCompleteManual = isCompleteManual;
		}

		public boolean isIsCompleteManual(){
			return isCompleteManual;
		}

		public void setIsCompleteAuto(boolean isCompleteAuto){
			this.isCompleteAuto = isCompleteAuto;
		}

		public boolean isIsCompleteAuto(){
			return isCompleteAuto;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}

		public void setIsManualCheckAllowed(boolean isManualCheckAllowed){
			this.isManualCheckAllowed = isManualCheckAllowed;
		}

		public boolean isIsManualCheckAllowed(){
			return isManualCheckAllowed;
		}

		public void setTooltips(List<TooltipsItem> tooltips){
			this.tooltips = tooltips;
		}

		public List<TooltipsItem> getTooltips(){
			return tooltips;
		}

		public void setUpdateMethod(Object updateMethod){
			this.updateMethod = updateMethod;
		}

		public Object getUpdateMethod(){
			return updateMethod;
		}

		public void setIsComplete(boolean isComplete){
			this.isComplete = isComplete;
		}

		public boolean isIsComplete(){
			return isComplete;
		}
	}

	public static class TooltipsItem{

		@JsonProperty("sequence")
		private int sequence;

		@JsonProperty("tooltip")
		private String tooltip;

		@JsonProperty("label")
		private String label;

		public void setSequence(int sequence){
			this.sequence = sequence;
		}

		public int getSequence(){
			return sequence;
		}

		public void setTooltip(String tooltip){
			this.tooltip = tooltip;
		}

		public String getTooltip(){
			return tooltip;
		}

		public void setLabel(String label){
			this.label = label;
		}

		public String getLabel(){
			return label;
		}
	}

	public static class UsecasesItem{

		@JsonProperty("adoptionPercentage")
		private int adoptionPercentage;

		@JsonProperty("useCaseId")
		private String useCaseId;

		@JsonProperty("name")
		private String name;

		@JsonProperty("description")
		private String description;

		@JsonProperty("pitstops")
		private List<PitstopsItem> pitstops;

		@JsonProperty("currentPitstop")
		private String currentPitstop;

		public void setAdoptionPercentage(int adoptionPercentage){
			this.adoptionPercentage = adoptionPercentage;
		}

		public int getAdoptionPercentage(){
			return adoptionPercentage;
		}

		public void setUseCaseId(String useCaseId){
			this.useCaseId = useCaseId;
		}

		public String getUseCaseId(){
			return useCaseId;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}

		public void setPitstops(List<PitstopsItem> pitstops){
			this.pitstops = pitstops;
		}

		public List<PitstopsItem> getPitstops(){
			return pitstops;
		}

		public void setCurrentPitstop(String currentPitstop){
			this.currentPitstop = currentPitstop;
		}

		public String getCurrentPitstop(){
			return currentPitstop;
		}
	}
}