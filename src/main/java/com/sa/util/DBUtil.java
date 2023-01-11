package com.sa.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author XA04162
 *
 */
public class DBUtil {

    /**
     * Metodo que cierra un Resultset dado
     *
     * @param rs
     * @return true si el resultset finaliza cerrado, false en cualquier otro
     * caso
     */
    public static boolean closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Metodo que cierra un PreparedStatement dado
     *
     * @param rs
     * @return true si el PreparedStatement finaliza cerrado, false en cualquier
     * otro caso
     */
    public static boolean closePreparedStatement(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Metodo que cierra un Conexion dada
     *
     * @param conn
     * @return true si la conexion finaliza cerrada, false en cualquier otro
     * caso
     */
    public static boolean closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
