package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import javax.persistence.PersistenceException;

public class UbicacionDAOHibernate implements UbicacionDAO
{

    /**
     * Guarda una {@link Ubicacion} en la base de datos. Si ya existe la ubicacion da una excepcion.
     * @param ubicacion - La {@link Ubicacion} que representa el nombre de la ubicacion.
     * @throws {@link PersistenceException} - si el objeto a guardar ya existe.
     */
    @Override
    public void guardar(Ubicacion ubicacion)
    {
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
    }

    /**
     * Dado el nombre de una {@link Ubicacion} retorna la ubicacion desde la base de datos.
     * Si la ubicacion no existe retorna null
     * @param nombre - {@link String} que representa el nombre de la ubicacion.
     * @return {@link Ubicacion} si es encontrada o Null en caso contrario.
     */
    @Override
    public Ubicacion recuperar(String nombre)
    {
        Session session = Runner.getCurrentSession();
        return session.get(Ubicacion.class, nombre);
    }

    /**
     * Actualiza una {@link Ubicacion} en la base de datos. Si no existe la ubicacion, no hace nada.
     * @param ubicacion - La {@link Ubicacion} que se va a actualizar.
     * @throws {@link PersistenceException} - si se cambia el nombre de la ubicacion.
     */
    @Override
    public void actualizar(Ubicacion ubicacion)
    {
        Session session = Runner.getCurrentSession();
        session.update(ubicacion);
    }

    public void crerDatosIniciales()
    {
        Ubicacion ubicacion1  = new Ubicacion();
        ubicacion1.setNombre("El Origen");
        Ubicacion ubicacion2  = new Ubicacion();
        ubicacion2.setNombre("Volcano");

        Session session = Runner.getCurrentSession();
        session.save(ubicacion1);
        session.save(ubicacion2);
    }
}
