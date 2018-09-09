package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.ConnectionBlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionService
{

    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    public <T> T executeWithConnection(ConnectionBlock<T> bloque)
    {
        Connection connection = this.openConnection();
        try
        {   return bloque.executeWith(connection);  }
        catch (SQLException e)
        {   throw new RuntimeException("Error no esperado", e); }
        finally
        {   this.closeConnection(connection);   }
    }

    /**
     * Establece una conexion a la url especificada
     * @return la conexion establecida
     */
    private Connection openConnection() {
        try {
            //La url de conexion no deberia estar harcodeada aca
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bichomon_tp1_jdbc?user=root&password=root&serverTimezone=UTC&useSSL=false");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     * @param connection - la conexion a cerrar.
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }

}
