package com.umbrafactions.umbracore;

import java.sql.*;

public class SQLInterface {
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_HOST = "209.105.225.66";
    private final String DB_USER = "mc27774";
    private final String DB_NAME = "mc27774";
    private final String DB_PASS = "16fa154dd2";
    private final String DB_URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;

    private Connection dbCon = null;
    private PreparedStatement dbStmt = null;
    private ResultSet dbRs = null;

    public ResultSet queryDatabase(String query) {
        try {
            Class.forName(JDBC_DRIVER);
            dbCon = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            dbStmt = dbCon.prepareStatement(query);
            dbRs = dbStmt.executeQuery();
            return dbRs;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanSql() {
        try {
            if (dbRs != null) {
                dbRs.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if (dbStmt != null) {
                dbStmt.close();
            }
        } catch (SQLException se2) {
            // nothing can be done
        }
        try {
            if (dbCon != null) {
                dbCon.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void updateDatabase(String query) {
        try {
            Class.forName(JDBC_DRIVER);
            dbCon = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            dbStmt = dbCon.prepareStatement(query);
            dbStmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbRs != null) {
                    dbRs.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (dbStmt != null) {
                    dbStmt.close();
                }
            } catch (SQLException se2) {
                // nothing can be done
            }
            try {
                if (dbCon != null) {
                    dbCon.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void softUpdateDatabase(String query) {
        try {
            Class.forName(JDBC_DRIVER);
            dbCon = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            dbStmt = dbCon.prepareStatement(query);
            dbStmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
