package net.abcbs.eae.jaxrs;
import javax.ws.rs.GET;

import java.util.ArrayList;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import net.abcbs.issh.util.pub.common.IsSharedApplicationDataObject;
import net.abcbs.issh.util.pub.common.ValidateUtilities;
import net.abcbs.rpa.dto.ClaimCheckDTO;
import net.abcbs.rpa.javabeans.ClaimCheckJavaBean;

/***********************************************************************************************************************************************************************
 * @Author mfribeiro
 * 
 * Body for all the methods used for the REST Web service
 * 
 * Description: ClaimCheckPlusResource class is the application resource level which the main methods will be called in order to return the message to the user 
 * 
 * Project: NP Pended Claims
 ***********************************************************************************************************************************************************************/
@Path("/mainframe")
@OpenAPIDefinition(
		servers = {
				@Server(
					description = "localhost",
					url = "localhost:9080/RPAClaimCheckPlusService/resources"),
				@Server(
					description = "development",
					url = "https://isshareddev.abcbs.net/RPAClaimCheckPlusService/resources"),
				@Server(
						description = "test",
						url = "https://issharedtst.abcbs.net/RPAClaimCheckPlusService/resources"),
				@Server(
					description = "stage",
					url = "https://issharedstg.abcbs.net/RPAClaimCheckPlusService/resources"),
				@Server(
					description = "production",
					url = "https://isshared.abcbs.net/RPAClaimCheckPlusService/resources")
		})
@SecurityScheme(name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic",
		description = "Username and Password are used for authorization")

public class RPAClaimCheckResource {
	
    /**
     * Private method
     * 
     * Data object to get database information
     * 
     * Utilizing isSharedApplication class
     * 
     */
	private static Logger logger = LogManager.getLogger(RPAClaimCheckResource.class);
	private static IsSharedApplicationDataObject isSharedApplicationDataObject = null;

	static {
		try {
			isSharedApplicationDataObject = new IsSharedApplicationDataObject(Constants.SYSTEM_NAME, Constants.AUTH_KEY, Constants.AUTH_PASS_PHRASE_DEV);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
     * Public method
     * 
     * Method to output successful message from the server
     * 
     * Includes brief instruction on how to use this service
     * 
     * @return string value
     * 
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Service base endpoint that you can hit to get a response from the server",
			security = @SecurityRequirement(name = "basicAuth"),
			description = "A base endpoint for this service",
			responses = {
					@ApiResponse(
							description = "JSON response",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = RPAClaimCheckMessage.class))),
					@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)	
	public String serviceLineMessage(){
		return "{\"message\": \"This web service is designed to retrieve data values from mainframe databases. IssharedApplication object is responsible to establish the dbconnection. Db2jndiName: " + isSharedApplicationDataObject.getDb2JndiName() + ". Db2SchemaName: " + isSharedApplicationDataObject.getDb2Schema() + ". Please use claim number as the unique identifier during the request.  \"}";
	}
	
	/**
     * Public method
     * 
     * Peer Group CPT4 Code
     * 
     * @return returns the total count of lines for *COVID* given a claim number
     * 
     */
	@GET
	@Path("/peer-group/{cptCd}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Purpose for this GET method is to retrieve total count of service lines for COVID benefit claims only",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Returns counter",
	responses = {
		@ApiResponse(
				description = "A method to simply test your credentials against the web service to see if you are authorized",
				content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)
	public ArrayList<ClaimCheckDTO> peerGroup (
			@Parameter(description = "Idetify the string and perform input validation. {messageId} refers to the 'claim number' in BAOracle database",
			required = true)
			@PathParam("cptCd") String id) {
		RPAClaimCheckMessage info = new RPAClaimCheckMessage();
		String cptCode = info.getMessage(id);
		ClaimCheckJavaBean claimCheckPlusJavaBean = new ClaimCheckJavaBean();
		
		if (!invalidInput(cptCode)){
			
			return claimCheckPlusJavaBean.peerGroupQuery(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), cptCode);
			//"db2NodeDB","BCBSDB21",isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema()
			
		}
		else {
			return claimCheckPlusJavaBean.exceptionMessage("Invalid CPT4 Code!");
		}
		
	}
	
	
	/**
     * Public method
     * 
     * Fee Schedule ProcCode
     * 
     * @return returns the total count of lines for *COVID* given a claim number
     * 
     */
	@GET
	@Path("/fee-schedule/{procCd}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Purpose for this GET method is to retrieve total count of service lines for COVID benefit claims only",
	security = @SecurityRequirement(name = "basicAuth"),
	description = "Returns counter",
	responses = {
		@ApiResponse(
				description = "A method to simply test your credentials against the web service to see if you are authorized",
				content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "401", description = "Credentials not authorized") }
	)
	public ArrayList<ClaimCheckDTO> covidServiceLineTotal (
			@Parameter(description = "Idetify the string and perform input validation. {messageId} refers to the 'claim number' in BAOracle database",
			required = true)
			@PathParam("procCd") String id) {
		RPAClaimCheckMessage info = new RPAClaimCheckMessage();
		String procCd = info.getMessage(id);
		ClaimCheckJavaBean claimCheckPlusJavaBean = new ClaimCheckJavaBean();
		
		if (!invalidInput(procCd)){
			
			return claimCheckPlusJavaBean.feeScheduleQuery(isSharedApplicationDataObject.getDb2JndiName(), isSharedApplicationDataObject.getDb2Schema(), procCd);
			//"db2NodeDB","BCBSDB21"
		}
		else {
			return claimCheckPlusJavaBean.exceptionMessage("Invalid HCPC Proc code!");
		}
		
	}
	
	
	
	/**
     * Private method
     * 
     * Performs input validation following basic java guidelines. 
     * 
     * Validate a claim number before it sends the request for data base connections 
     * 
     * @return boolean for true has error or false
     * 
     */
	private boolean invalidInput(String input) {
		boolean hasError = false; 
	       if ("".equalsIgnoreCase(input) || input == null) {
	           hasError = true;
	           logger.info(input,"{} Field Required!");
	       }
	       if (!hasError) {
	           if (!ValidateUtilities.isValidateMaxStringLength(input, 15)) {
	               hasError = true;
	               logger.info("Invalid {} Length! ",input);   
	           }
	           else if (!input.matches("[a-zA-Z0-9]+")){
		           	hasError = true;
		           	logger.info(input, "{} It is not Alphanumeric!");
		           }
	           else if (!ValidateUtilities.isValidateCharactersWithHyphen(input)) {
	               hasError = true;
	               logger.info(input,"{} Contains Invalid Characters!");                
	           }
	       }
       return hasError; 	    
       }

	/**
     * Public method
     * 
     * Performs credential validation 
     * 
     * If user gets a 401 they are unauthorized
     * 
     * @return ClaimCheckPlusMessage type message 
     * 
     */
	@GET
	@Path("/test/authorization") 
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Simple test authorization method",
		security = @SecurityRequirement(name = "basicAuth"),
		description = "Test your credentials",
		responses = {
			@ApiResponse(
					description = "A method to simply test your credentials against the web service to see if you are authorized",
					content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Credentials not authorized")}
	)
	public RPAClaimCheckMessage testAuthorization() {
		RPAClaimCheckMessage message = new RPAClaimCheckMessage();
		message.setMessage("authorization valid");
		return message;
	}
	
	
	
}
