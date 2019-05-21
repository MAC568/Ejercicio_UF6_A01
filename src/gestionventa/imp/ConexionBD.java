package gestionventa.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConexionBD {

    private static ConexionBD myConnection;
    private static final String HOST = "localhost";
    private static final String CATALOG = "GestionVenta";
    private static final String USER = "multinacional_user";
    private static final String PASSWORD = "multinacional_pass";
    private Connection cn = null;

    // El constructor se encarga de cargar el driver a la base de datos    
    private ConexionBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static ConexionBD getInstance() {
        if (myConnection == null) {
            myConnection = new ConexionBD();
        }
        return myConnection;
    }

    public void openConnection() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + CATALOG, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void closeConnection() {
        try {
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public boolean testConnection() {
        boolean b = false;
        try {
            openConnection();
            b = cn != null && !cn.isClosed();
        } catch (SQLException ex) {
            System.out.println("Error al probar conexion");
            System.out.println(ex);
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return b;
    }

    //Ejecuta un comando SQL
    public boolean executeSql(String sqlCommand) {
        return executeSql(sqlCommand, "");
    }

    // Ejecuta un comando a la base de datos con 1 parámetro
    public boolean executeSql(String sqlCommand, String param) {
        return executeSql(sqlCommand, new String[]{param});
    }

    // Ejecuta un comando a la base de datos 
    public boolean executeSql(String sqlCommand, String[] params) {
        PreparedStatement pst;
        int afectados = 0;
        try {
            openConnection();
            pst = cn.prepareStatement(sqlCommand);
            if (params != null && sqlCommand.contains("?")) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            afectados = pst.executeUpdate();
            if (!pst.isClosed()) {
                pst.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar sentencia: " + sqlCommand);
            System.out.println("Parametros: ");
            if (params != null) {
                for (String param : params) {
                    System.out.println(param);
                }
            }
            ex.printStackTrace(System.out);
        } finally {
            closeConnection();
        }
        return afectados > 0;
    }

    // Aqui insertamos un registro en la base de datos y nos devuelve el id generado por la bdd 
    public Integer executeInsert(String sqlCommand, String[] params) {
        PreparedStatement pst;
        int key = -1;
        try {
            openConnection();
            pst = cn.prepareStatement(sqlCommand, PreparedStatement.RETURN_GENERATED_KEYS);
            if (params != null && sqlCommand.contains("?")) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            if (pst.executeUpdate() > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    key = rs.getInt(1);
                }
            }
            if (!pst.isClosed()) {
                pst.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar sentencia: " + sqlCommand);
            System.out.println("Parametros: ");
            if (params != null) {
                for (String param : params) {
                    System.out.println(param);
                }
            }
            ex.printStackTrace(System.out);
        } finally {
            closeConnection();
        }
        return key;
    }

    // Ejecutamos una consulta a la bbd para que devuelva un "mapa" con los datos de la consulta.
    public List<String[]> executeQuery(String sqlCommand) {
        return executeQuery(sqlCommand, "");
    }

    public List<String[]> executeQuery(String sqlCommand, String param) {
        return executeQuery(sqlCommand, new String[]{param});
    }

    public List<String[]> executeQuery(String sqlCommand, String[] params) {
        PreparedStatement pst;
        ResultSet rs;
        List<String[]> rows = null;
        try {
            openConnection();
            pst = cn.prepareStatement(sqlCommand);
            if (params != null && sqlCommand.contains("?")) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            rs = pst.executeQuery();
            int cols = rs.getMetaData().getColumnCount();
            String[] row;
            rows = new ArrayList();
            while (rs.next()) {
                row = new String[cols];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getString(i + 1);
                }
                rows.add(row);
            }
            if (!pst.isClosed()) {
                pst.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar query: " + sqlCommand);
            ex.printStackTrace(System.out);
        } finally {
            closeConnection();
        }
        return rows;
    }
}
