package com.application.disease.model.dto;

public class RequestDto {
    private String id;
    private String diseaseName;
    private String regionName;
    private int numberOfIll = 0;
    private int numberOfRecovered = 0;
    private String userId;

    private RequestDto() {
    }

    public RequestDto(String diseaseName, String regionName, int numberOfIll, int numberOfRecovered, String userId) {
        this.diseaseName = diseaseName;
        this.regionName = regionName;
        this.numberOfIll = numberOfIll;
        this.numberOfRecovered = numberOfRecovered;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getNumberOfIll() {
        return numberOfIll;
    }

    public int getNumberOfRecovered() {
        return numberOfRecovered;
    }

    public String getUserId() {
        return userId;
    }

    public static Builder newBuilder() {
        return new RequestDto().new Builder();
    }

    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setId(String id) {
            RequestDto.this.id = id;

            return this;
        }

        public Builder setDiseaseName(String diseaseName) {
            RequestDto.this.diseaseName = diseaseName;

            return this;
        }

        public Builder setRegionName(String regionName) {
            RequestDto.this.regionName = regionName;

            return this;
        }

        public Builder setNumberOfIll(int numberOfIll) {
            RequestDto.this.numberOfIll = numberOfIll;

            return this;
        }

        public Builder setNumberOfRecovered(int numberOfRecovered) {
            RequestDto.this.numberOfRecovered = numberOfRecovered;

            return this;
        }

        public Builder setUserId(String userId) {
            RequestDto.this.userId = userId;

            return this;
        }

        public RequestDto build() {
            RequestDto requestDto = new RequestDto();
            requestDto.id = RequestDto.this.id;
            requestDto.diseaseName = RequestDto.this.diseaseName;
            requestDto.regionName = RequestDto.this.regionName;
            requestDto.numberOfIll = RequestDto.this.numberOfIll;
            requestDto.numberOfRecovered = RequestDto.this.numberOfRecovered;
            requestDto.userId = RequestDto.this.userId;
            return requestDto;
        }
    }
}
