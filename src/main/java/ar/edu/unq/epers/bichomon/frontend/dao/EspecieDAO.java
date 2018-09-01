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
    public void actualizar(Especie especie)
    {   throw new UnsupportedOperationException("Not Yet Implemented"); }

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

    public void limpiarTabla()
    {
        new ConectionService().executeWithConnection (
                conn -> {
                    PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE especie");
                    ps.execute();
                    ps.close();
                    return null;
                }    );
    }

    public void crerDatosIniciales()
    {
        new ConectionService().executeWithConnection (
                conn -> {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO especie (id, nombre, altura, peso, energiaInicial, tipo, urlFoto, cantidadBichos) VALUES (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?), (?,?,?,?,?,?,?,?)");

                    ps.setInt   (1, 1);
                    ps.setString(2, "Rojomon");
                    ps.setInt   (3, 180);
                    ps.setInt   (4, 75);
                    ps.setInt   (5, 100);
                    ps.setString(6, "FUEGO");
                    ps.setString(7, "/image/rojomon.jpg");
                    ps.setInt   (8, 0);

                    ps.setInt   (9, 2);
                    ps.setString(10, "Amarillomon");
                    ps.setInt   (11, 170);
                    ps.setInt   (12, 69);
                    ps.setInt   (13, 300);
                    ps.setString(14, "ELECTRICIDAD");
                    ps.setString(15, "/image/amarillomon.png");
                    ps.setInt   (16, 0);

                    ps.setInt   (17, 3);
                    ps.setString(18, "Verdemon");
                    ps.setInt   (19, 150);
                    ps.setInt   (20, 55);
                    ps.setInt   (21, 5000);
                    ps.setString(22, "PLANTA");
                    ps.setString(23, "/image/verdemon.jpg");
                    ps.setInt   (24, 0);

                    ps.setInt   (25, 4);
                    ps.setString(26, "Tierramon");
                    ps.setInt   (27, 1050);
                    ps.setInt   (28, 99);
                    ps.setInt   (29, 5000);
                    ps.setString(30, "TIERRA");
                    ps.setString(31, "/image/tierramon.jpg");
                    ps.setInt   (32, 0);

                    ps.setInt   (33, 5);
                    ps.setString(34, "Fantasmon");
                    ps.setInt   (35, 1050);
                    ps.setInt   (36, 99);
                    ps.setInt   (37, 5000);
                    ps.setString(38, "AIRE");
                    ps.setString(39, "/image/fantasmon.jpg");
                    ps.setInt   (40, 0);

                    ps.setInt   (41, 6);
                    ps.setString(42, "Vampiron");
                    ps.setInt   (43, 1050);
                    ps.setInt   (44, 99);
                    ps.setInt   (45, 5000);
                    ps.setString(46, "AIRE");
                    ps.setString(47, "/image/vampiron.jpg");
                    ps.setInt   (48, 0);

                    ps.setInt   (49, 7);
                    ps.setString(50, "Fortmon");
                    ps.setInt   (51, 1050);
                    ps.setInt   (52, 99);
                    ps.setInt   (53, 5000);
                    ps.setString(54, "AIRE");
                    ps.setString(55, "/image/fortmon.png");
                    ps.setInt   (56, 0);

                    ps.setInt   (57, 8);
                    ps.setString(58, "Dientemon");
                    ps.setInt   (59, 1050);
                    ps.setInt   (60, 99);
                    ps.setInt   (61, 5000);
                    ps.setString(62, "AGUA");
                    ps.setString(63, "/image/dientemon.jpg");
                    ps.setInt   (64, 0);

                    ps.execute();
                    ps.close();
                    return null;
                }    );
    }
}
