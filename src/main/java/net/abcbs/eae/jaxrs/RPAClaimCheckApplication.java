package net.abcbs.eae.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/***********************************************************************************************************************************************************************
 * @Author ABCBS Resource
 * 
 * Description: RPAClaimCheckApplication class will be used as the application driver
 * 
 * Project: FRM InterPlan Invoice
 ***********************************************************************************************************************************************************************/
@ApplicationPath("resources")
public class RPAClaimCheckApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(RPAClaimCheckResource.class);
		return classes;	
	}
}