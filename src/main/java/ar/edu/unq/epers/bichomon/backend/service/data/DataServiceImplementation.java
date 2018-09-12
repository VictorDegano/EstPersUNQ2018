package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.dao.jdbc.EspecieDAOJDBC;

public class DataServiceImplementation implements DataService
{
    private EspecieDAOJDBC especieDAOJDBC;

    public DataServiceImplementation(EspecieDAOJDBC unEspecieDAOJDBC)
    {   especieDAOJDBC = unEspecieDAOJDBC;  }

    @Override
    public void eliminarDatos()
    {   especieDAOJDBC.limpiarTabla();  }

    @Override
    public void crearSetDatosIniciales()
    {   especieDAOJDBC.crerDatosIniciales();    }
}
