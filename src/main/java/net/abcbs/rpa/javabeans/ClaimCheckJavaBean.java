package net.abcbs.rpa.javabeans;
/***********************************************************************************************************************************************************************
 * @author ABCBS resource
 * 
 * Description: ClaimCheckPlusJavaBean class will be used to perform the proper connection with Oracle database and query the correct value required by the user 
 * 
 * Project: NP Pended Claims
 ***********************************************************************************************************************************************************************/

import net.abcbs.rpa.dto.*;
import net.abcbs.eae.sql.SQLStatementHandler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.abcbs.issh.util.pub.javabeans.IsSharedJavaBean;


public class ClaimCheckJavaBean extends IsSharedJavaBean {
	//Local constants
	private static Logger logger = LogManager.getLogger(ClaimCheckJavaBean.class);
	SQLStatementHandler statementHandler = new SQLStatementHandler();
	
	public ArrayList<ClaimCheckDTO> peerGroupQuery(String dataSource, String scheme, String cptCode)
	{
		ArrayList<ClaimCheckDTO> jsonFinal = new ArrayList<>();
		this.setDbFunctionDelete(dbFunctionDelete);
		String effectiveDt = new String();
		
		try {
			this.initializeConnection(dataSource, "");
			sqlStatement.append(" SELECT MAX(EFFECTIVE_DATE) AS EFF_DATE");
			sqlStatement.append(" FROM  " + scheme + ".CPT4_PEERGROUP_AMT");
			sqlStatement.append(" WHERE CPT4_CD =  ?");
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, cptCode);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				effectiveDt = resultSet.getString(1);
			}
			else {
				logger.info("Error: {}", "Latest effective date has not been found to cpt4_cd: " + cptCode);
				ClaimCheckDTO peerGroupOutput = new ClaimCheckDTO();
				peerGroupOutput.setError("Latest effective date has not been found to cpt4_cd: " + cptCode);
				throw new Exception ("Latest effective date has not been found to cpt4_cd: " + cptCode);
			}
			sqlStatement.setLength(0);
			sqlStatement.append(" SELECT UNIQUE (CPT4_CD), EFFECTIVE_DATE, TERMINATION_DATE, PAYMENT_AMT");
			sqlStatement.append(" FROM  " + scheme + ".CPT4_PEERGROUP_AMT");
			sqlStatement.append(" WHERE CPT4_CD =  ?");
			sqlStatement.append(" AND EFFECTIVE_DATE =  '" + effectiveDt + "'");
			logger.info("SQL: {}", sqlStatement);

			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, cptCode);
			resultSet = preparedStatement.executeQuery();
			
			
			while (resultSet.next()) {
				
				ClaimCheckDTO peerGroupOutput = new ClaimCheckDTO();
				//serv_line
				peerGroupOutput.setCpt4Cd(resultSet.getString(1));
				peerGroupOutput.setEffectiveDt(resultSet.getDate(2));
				peerGroupOutput.setTerminationDt(resultSet.getDate(3));
				peerGroupOutput.setPaymentAmt(resultSet.getString(4));
				jsonFinal.add(peerGroupOutput);
				
			}
			//Just adding a new comment 
			
		}
		catch (SQLException se) {
			this.processException(se);
		}
		catch (Exception e) {
			this.processException(e);
		}
		finally {
			displayResults();
			this.closeConnections();
		}

		return jsonFinal;
	}
	
	
	
	public ArrayList<ClaimCheckDTO> feeScheduleQuery(String dataSource, String scheme, String procCd)
	{
		ArrayList<ClaimCheckDTO> jsonFinal = new ArrayList<>();
		this.setDbFunctionDelete(dbFunctionDelete);
		String effectiveDt = new String();
		
		try {
			this.initializeConnection(dataSource, "");
			sqlStatement.append(" SELECT MAX(C7410_EFF_DATE) AS EFF_DATE");
			sqlStatement.append(" FROM  " + scheme + ".CBVW7410");
			sqlStatement.append(" WHERE C7410_HCPC_PROCCD =  ?");
			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, procCd);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				effectiveDt = resultSet.getString(1);
			}
			else {
				logger.info("Error: {}", "Latest effective date has not been found to cpt4_cd: " + procCd);
				ClaimCheckDTO peerGroupOutput = new ClaimCheckDTO();
				peerGroupOutput.setError("Latest effective date has not been found to cpt4_cd: " + procCd);
				throw new Exception ("Latest effective date has not been found to cpt4_cd: " + procCd);
				
			}
			sqlStatement.delete(0, sqlStatement.length());
			sqlStatement.append(" SELECT UNIQUE(C7410_HCPC_PROCCD) AS HCPC, C7410_SCHED_NBR AS SCHEDULE, C7410_EFF_DATE AS EFF_DATE, C7410_TOT_VAL_AMT AS TOTAL_AMT");
			sqlStatement.append(" FROM " + scheme + ".CBVW7410");
			sqlStatement.append(" WHERE C7410_HCPC_PROCCD = ?");
			sqlStatement.append(" AND C7410_EFF_DATE =  '" + effectiveDt + "'");
			logger.info("SQL: {}", sqlStatement);

			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, procCd);
			resultSet = preparedStatement.executeQuery();
			
			
			while (resultSet.next()) {
				
				ClaimCheckDTO peerGroupOutput = new ClaimCheckDTO();
				
				peerGroupOutput.setHcpcProcCode(resultSet.getString(1));
				peerGroupOutput.setScheduleNbr(resultSet.getString(2));
				peerGroupOutput.setEffectiveDt(resultSet.getDate(3));
				peerGroupOutput.setPaymentAmt(resultSet.getString(4));
				jsonFinal.add(peerGroupOutput);
				
			}
			
		}
		catch (SQLException se) {
			this.processException(se);
		}
		catch (Exception e) {
			this.processException(e);
		}
		finally {
			displayResults();
			this.closeConnections();
		}

		return jsonFinal;
	}
	
	//Connection with db does not work here
	public ArrayList<ClaimCheckDTO> newPeerGroupQuery(String dataSource, String scheme, String cptCode)
	{
		ArrayList<ClaimCheckDTO> jsonFinal = new ArrayList<>();
		this.setDbFunctionDelete(dbFunctionDelete);
		String effectiveDt = new String();
		
		
		try {
			Connection connection = this.initializeConnection(dataSource, "");
			String query = "SELECT MAX(EFFECTIVE_DATE) AS EFF_DATE \n"
					+ "FROM  " + scheme + ".CPT4_PEERGROUP_AMT \n"
					+ "WHERE CPT4_CD = ?";
			
			ArrayList<String> paramsQuery = new ArrayList<>();
			paramsQuery.add(cptCode);
			PreparedStatement paramStatement = statementHandler.preparedStatementParams(connection, query, paramsQuery);
			
			resultSet = paramStatement.executeQuery();
			if(resultSet.next()) {
				
				effectiveDt = resultSet.getString(1);
			}
			else {
				logger.info("Error: {}", "Latest effective date has not been found to cpt4_cd: " + cptCode);
				ClaimCheckDTO peerGroupOutput = new ClaimCheckDTO();
				peerGroupOutput.setError("Latest effective date has not been found to cpt4_cd: " + cptCode);
				throw new Exception ("Latest effective date has not been found to cpt4_cd: " + cptCode);
			}
			sqlStatement.setLength(0);
			sqlStatement.append(" SELECT UNIQUE (CPT4_CD), EFFECTIVE_DATE, TERMINATION_DATE, PAYMENT_AMT");
			sqlStatement.append(" FROM  " + scheme + ".CPT4_PEERGROUP_AMT");
			sqlStatement.append(" WHERE CPT4_CD =  ?");
			sqlStatement.append(" AND EFFECTIVE_DATE =  '" + effectiveDt + "'");
			logger.info("SQL: {}", sqlStatement);

			preparedStatement = connection.prepareStatement(sqlStatement.toString());
			preparedStatement.setString(1, cptCode);
			resultSet = preparedStatement.executeQuery();
			
			
			while (resultSet.next()) {
				
				ClaimCheckDTO peerGroupOutput = new ClaimCheckDTO();
				
				peerGroupOutput.setCpt4Cd(resultSet.getString(1));
				peerGroupOutput.setEffectiveDt(resultSet.getDate(2));
				peerGroupOutput.setTerminationDt(resultSet.getDate(3));
				peerGroupOutput.setPaymentAmt(resultSet.getString(4));
				jsonFinal.add(peerGroupOutput);
				
			}
			
		}
		catch (SQLException se) {
			this.processException(se);
		}
		catch (Exception e) {
			this.processException(e);
		}
		finally {
			displayResults();
			this.closeConnections();
		}

		return jsonFinal;
	}
	
	

	
	
	
	public ArrayList<ClaimCheckDTO> exceptionMessage (String error){
		ArrayList<ClaimCheckDTO> arrayList = new ArrayList<>();
		
		ClaimCheckDTO errorMessage = new ClaimCheckDTO();
		errorMessage.setError(error);
		arrayList.add(errorMessage);
		return arrayList;
	}
	
	
	
	public static String toDecimal (String origStr) {

        if (!"".equals(origStr)){
            int totalSize = origStr.length();
            if (totalSize > 2 ) {
                return origStr.substring(0, totalSize - 2) + "." + origStr.substring(totalSize - 2);
            }
            else {
                return "0." + origStr;
            }
        }
        return "Error: Current string is null, decimal manipulation cannot be done. Please, review input variable";
    }
	
	
	
	public static String toLocalCurrency (String money) {

        if (!"".equals(money)) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            Double value = Double.parseDouble(money);
            return formatter.format(value);
        } else {
            return "Error: Current string is null, currency manipulation cannot be done. Please, review input variable\"";
        }
    }
	

}


