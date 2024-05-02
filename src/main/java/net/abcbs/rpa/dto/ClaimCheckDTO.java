package net.abcbs.rpa.dto;

import java.sql.Date;

/***********************************************************************************************************************************************************************
 * @author mfribeiro
 * 
 * Description: AmisysDTO class used to perform core task such as create and construct the object used to in java beans
 * 
 * Project: RPA Amisys Claim Service
 ***********************************************************************************************************************************************************************/

public class ClaimCheckDTO {
	
	//local variables
	private String cpt4Cd;
	private String hcpcProcCode; //cpt4
	private String peerGroupNbr;
	private Date effectiveDt;
	private Date terminationDt;
	private String activateFlag;
	private String paymentAmt;
	private String paymentFlag;
	private String changeDt;
	private String scheduleNbr;
	private String error;
	
	
	//getters and setters
	public String getCpt4Cd() {
		return cpt4Cd;
	}
	public void setCpt4Cd(String cpt4Cd) {
		this.cpt4Cd = cpt4Cd;
	}
	public String getPeerGroupNbr() {
		return peerGroupNbr;
	}
	public void setPeerGroupNbr(String peerGroupNbr) {
		this.peerGroupNbr = peerGroupNbr;
	}
	public Date getEffectiveDt() {
		return effectiveDt;
	}
	public void setEffectiveDt(Date effectiveDt) {
		this.effectiveDt = effectiveDt;
	}
	public Date getTerminationDt() {
		return terminationDt;
	}
	public void setTerminationDt(Date terminationDt) {
	}
	public String getActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(String activateFlag) {
		this.activateFlag = activateFlag;
	}
	public String getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public String getChangeDt() {
		return changeDt;
	}
	public void setChangeDt(String changeDt) {
		this.changeDt = changeDt;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getHcpcProcCode() {
		return hcpcProcCode;
	}
	public void setHcpcProcCode(String hcpcProcCode) {
		this.hcpcProcCode = hcpcProcCode;
	}
	public String getScheduleNbr() {
		return scheduleNbr;
	}
	public void setScheduleNbr(String scheduleNbr) {
		this.scheduleNbr = scheduleNbr;
	}
	
}
