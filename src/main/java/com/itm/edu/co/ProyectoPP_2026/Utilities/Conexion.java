package com.itm.edu.co.ProyectoPP_2026.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    Connection con;
/* en este caso springboot ya me carga el driver de mysql
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
     */

    public Connection obtenerConexion() {
        try {
            //"jdbc:sqlserver://localhost:1433;databaseName=TuBaseDeDatos;user=TuUsuario;password=TuContraseña";
            //Connection connection = DriverManager.getConnection(url)
            //jdbc:oracle:thin:@//localhost:1521/xe
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/labor_social", "root", "admin");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            ex.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        Conexion conection = new Conexion();
        try {
            ResultSet r = conection.obtenerConexion().prepareStatement("select * from usuario").executeQuery();
            if (r.next()) {
                System.out.println("id: " + r.getString(1) + " nombre: " + r.getString(2));
                while (r.next()) {
                    System.out.println("Correo: " + r.getString("correo") + "nombres: " + r.getString("nombres")
                    + " "+ r.getString("apellidos"));
                }
            } else {
                System.out.println("NO HAY DATOS");
            }
        } catch (Exception e) {
            System.out.println("#Excepcion en el main: "+e.getMessage());
        }
    }
}
