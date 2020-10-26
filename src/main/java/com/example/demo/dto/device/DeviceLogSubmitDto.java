package com.example.demo.dto.device;

import javax.validation.constraints.NotNull;

public class DeviceLogSubmitDto {
	
	@NotNull
    private String deviceId;
	
	@NotNull
    private String userId;
    
	@NotNull
	private int temperature;
	
	@NotNull
    private Long timestamp;
    
	@NotNull
    private String cardType;    

    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
    
}
