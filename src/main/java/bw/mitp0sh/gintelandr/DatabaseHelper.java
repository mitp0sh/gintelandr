package bw.mitp0sh.gintelandr;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.flogger.FluentLogger;

public class DatabaseHelper {
	private static final FluentLogger flog = FluentLogger.forEnclosingClass();
	
	private static Connection connection = null;
	private static final String TEMP_DB = "gintelandr.db.tmp";
	private static boolean isTempDB = true;
	
	private static Connection openTmpDB()  throws SQLException {
		return openDB(new File(TEMP_DB));
	}
	
	private static Connection openDB(File db)  throws SQLException {
		if(connection == null) {
			connection = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
		}
		
		return connection;
	}
	
	public static boolean initDB(boolean isTempDB, File db) {
		DatabaseHelper.isTempDB = isTempDB;
		
		try {
			if(isTempDB) {
				openTmpDB();
			} else {
				openDB(db);
			}
						
			createStatement(DBModelHelper.SQLQUERY_DROP_TABLE_DEX);
			createStatement(DBModelHelper.SQLQUERY_CREATE_TABLE_DEX);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static boolean finalizeDB() {
		try {
			if(isTempDB) {
				finalizeTmpDB();
			} else {
				connection.close();
				while(!connection.isClosed()) {
					Thread.sleep(500);
				}
				
				connection = null;	
			}
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private static void finalizeTmpDB() {		
		if(connection != null) {
			try {
				connection.close();
				while(!connection.isClosed()) {
					Thread.sleep(500);
				}
				
				//File tempDBFile = new File(TEMP_DB);
				//if(tempDBFile.exists()) {
				//	tempDBFile.delete();
				//}
				
				connection = null;				
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static boolean createStatement(String sql) throws SQLException {
		Statement stmt  = connection.createStatement();
		return stmt.execute(sql);
	}
	
	public static boolean insertDex(int index, String source, String path) throws SQLException {		
		String sql = "INSERT INTO dex (idx, source, path)";
		sql += " VALUES(?, ?, ?);";		
		PreparedStatement pstmt = connection.prepareStatement(sql);		
		pstmt.setInt(1, index);
		pstmt.setString(2, source);
		pstmt.setString(3, path);
		return pstmt.executeUpdate() == 1 ? true : false;
	}
	
	public static boolean deleteDex(int index) throws SQLException {
		Statement stmt  = connection.createStatement();
		return stmt.executeUpdate("DELETE FROM dex WHERE idx = " + index + ";") == 1 ? true : false;
	}
}
