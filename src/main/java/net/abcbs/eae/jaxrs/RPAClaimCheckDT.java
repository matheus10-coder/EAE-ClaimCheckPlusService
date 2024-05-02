package net.abcbs.eae.jaxrs;
import java.util.ArrayList;
import java.util.*;
/***********************************************************************************************************************************************************************
 * @author mfribeiro
 * 
 * Description: RPAClaimCheckDT class is used to manually test the web service call to retrieve values out of a data table
 * 
 * Project: FRM InterPlan Invoice
 ***********************************************************************************************************************************************************************/
public class RPAClaimCheckDT {
	
	public List<RPAClaimCheckMessage> getDBServiceLines(){
		RPAClaimCheckMessage servLn1 = new RPAClaimCheckMessage("COVID19",1);
		RPAClaimCheckMessage servLn2 = new RPAClaimCheckMessage("ITS",2);
		RPAClaimCheckMessage servLn3 = new RPAClaimCheckMessage("N/A",3);
		
		List<RPAClaimCheckMessage> serviceLinesLs = new ArrayList<>();
		serviceLinesLs.add(servLn1);
		serviceLinesLs.add(servLn2);
		serviceLinesLs.add(servLn3);
		return serviceLinesLs;
	}

}
