package bw.mitp0sh.gintelandr;

public class DBModelHelper {	
	public static final String SQLQUERY_DROP_TABLE_ANALYSIS = "DROP TABLE IF EXISTS anlysis;";
	public static final String SQLQUERY_CREATE_TABLE_ANALYSIS = "CREATE TABLE anlysis ( id INTEGER [AUTOINCREMENT], child_id INTEGER [UNIQUE], type INTEGER NOT NULL ON CONFLICT FAIL, content_id INTEGER NOT NULL ON CONFLICT FAIL, PRIMARY KEY (id, child_id) );";
	
	public static final String SQLQUERY_DROP_TABLE_DEX = "DROP TABLE IF EXISTS dex;";
	public static final String SQLQUERY_CREATE_TABLE_DEX = "CREATE TABLE dex ( idx INTEGER [UNIQUE], source STRING [NOT NULL ON CONFLICT FAIL], path STRING [NOT NULL ON CONFLICT FAIL], PRIMARY KEY (idx));";	
}
                                                                                                                                                                                                                                                                                                                                                                                                                                    