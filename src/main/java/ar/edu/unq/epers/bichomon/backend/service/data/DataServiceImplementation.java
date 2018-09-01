package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.frontend.dao.EspecieDAO;

public class DataServiceImplementation implements DataService
{
    private EspecieDAO especieDAO;

    public DataServiceImplementation()
    {   especieDAO = new EspecieDAO();  }

    @Override
    public void eliminarDatos()
    {   especieDAO.limpiarTabla();  }

    @Override
    public void crearSetDatosIniciales()
    {   especieDAO.crerDatosIniciales();    }
}
