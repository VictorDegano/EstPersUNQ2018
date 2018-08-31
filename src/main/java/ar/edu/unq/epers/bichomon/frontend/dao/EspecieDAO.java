package ar.edu.unq.epers.bichomon.frontend.dao;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.ConectionService;

import java.sql.*;
import java.util.List;

public class EspecieDAO implements ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO {

    @Override
    public void guardar(Especie especie)
    {
        //Llama al Mensaje de ejecutar conexion y se le pasa el pedazo de codigo a ejecutar con el parametro que va a recibir
        new ConectionService().executeWithConnection (conn -> {
                                            PreparedStatement ps = conn.prepareStatement("INSERT INTO especie (id, nombre, altura, peso, energiaInicial, tipo, urlFoto, cantidadBichos) VALUES (?,?,?,?,?,?,?, ?)");
                                            ps.setInt   (1, especie.getId());
                                            ps.setString(2, especie.getNombre());
                                            ps.setInt   (3, especie.getAltura());
                                            ps.setInt   (4, especie.getPeso());
                                            ps.setInt   (5, especie.getEnergiaInicial());
                                            ps.setString(6, especie.getTipo().name());
                                            ps.setString(7, especie.getUrlFoto());
                                            ps.setInt   (8, especie.getCantidadBichos());

                                            ps.execute();

                                            if (ps.getUpdateCount() != 1)
                                            {   throw new RuntimeException("No se inserto la Especie" + especie.getNombre());  }
                                            ps.close();
                                            return null;
                                           }
                                    );
    }

    @Override
    public void actualizar(Especie especie) {
        new ConectionService().executeWithConnection (conn -> { PreparedStatement ps = conn.prepareStatement("UPDATE especie SET nombre = ?, altura = ?, peso = ?, energiaInicial =?, tipo = ? , urlFoto = ? ,cantidadBichos = ? WHERE id = ? " );

                    ps.setString(1,especie.getNombre());
                    ps.setInt(2,especie.getAltura());
                    ps.setInt(3,especie.getPeso());
                    ps.setInt(4,especie.getEnergiaInicial());
                    ps.setString(5,especie.getTipo().name());
                    ps.setString(6,especie.getUrlFoto());
                    ps.setInt(7,especie.getCantidadBichos());
                    ps.setInt(8,especie.getId());

                    ps.execute();
                    if (ps.getUpdateCount() != 1)
                    {   throw new RuntimeException("No se inserto la Especie" + especie.getNombre());  }
                    ps.close();
                    return null;
                }
        );
    }
    @Override
    public Especie recuperar(String nombreEspecie)
    {
        return new ConectionService().executeWithConnection(
                                    conn ->{
                                            PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, altura, peso, energiaInicial, tipo, urlFoto, cantidadBichos FROM especie WHERE nombre = ?");
                                            ps.setString(1, nombreEspecie); //Se setea el dato a utilizar en la busqueda

                                            ResultSet resultSet = ps.executeQuery();

                                            Especie especieRecuperada = null;
                                            while (resultSet.next())
                                            {   //si personaje no es null aca significa que el while dio mas de una vuelta, eso
                                                //suele pasar cuando el resultado (resultset) tiene mas de un elemento.
                                                if (especieRecuperada != null)
                                                {   throw new RuntimeException("Existe mas de un personaje con el nombre " + nombreEspecie);   }

                                                especieRecuperada = new Especie(    resultSet.getInt("id"),
                                                                                    nombreEspecie,
                                                                                    TipoBicho.valueOf(resultSet.getString("tipo")));

                                                especieRecuperada.setAltura(resultSet.getInt("altura"));
                                                especieRecuperada.setPeso(resultSet.getInt("peso"));
                                                especieRecuperada.setEnergiaIncial(resultSet.getInt("energiaInicial"));
                                                especieRecuperada.setUrlFoto(resultSet.getString("urlFoto"));
                                                especieRecuperada.setCantidadBichos(resultSet.getInt("cantidadBichos"));
                                            }
                                            ps.close();
                                            return especieRecuperada;
                                           }
                                         );
    }

    @Override
    public List<Especie> recuperarTodos()
    {   throw new UnsupportedOperationException("Not Yet Implemented"); }



    public void borrarEspecie(String especieABorrar)
    {

        //Llama al Mensaje de ejecutar conexion y se le pasa el pedazo de codigo a ejecutar con el parametro que va a recibir
        new ConectionService().executeWithConnection (conn -> {
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM especie WHERE nombre = ?");
                    ps.setString(1, especieABorrar); //Se setea el dato a utilizar en la busqueda
                    ps.execute();

                    if (ps.getUpdateCount() != 1)
                    {   throw new RuntimeException("No se elimino la Especie" + especieABorrar);  }
                    ps.close();
                    return null;
                }
        );
    }






}
