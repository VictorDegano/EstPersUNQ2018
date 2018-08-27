package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.service.ConectionService;

import java.sql.PreparedStatement;

public class DataServiceImplementation implements DataService
{
    @Override
    public void eliminarDatos()
    {
        new ConectionService().executeWithConnection (
                                        conn -> {
                                                    PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE especie");
                                                    ps.execute();
                                                    ps.close();
                                                    return null;
                                                }    );
    }

    @Override
    public void crearSetDatosIniciales()
    {   }
}
