package org.matozzo.training.messenger.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorClass {
	

	private int errorCode;
	private String errorDescription;
	private String errorMsg;
	
	public ErrorClass() {
		
	}
	
	public ErrorClass(int errorCode, String errorDescription, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
