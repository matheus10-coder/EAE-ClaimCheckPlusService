package net.abcbs.eae.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLStatementHandler {
	
	
	public PreparedStatement preparedStatement (Connection connection, String query) throws SQLException{
        return connection.prepareStatement(query);
    }

    //overloaded method to handle queries with parameters
    public PreparedStatement preparedStatementParams (Connection connection, String query, ArrayList<String> params) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.size(); i++){
            preparedStatement.setString(i+1, params.get(i));
        }
        return preparedStatement;
    }

}
