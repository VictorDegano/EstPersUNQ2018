package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
        String hql = "FROM Ubicacion u WHERE u.nombre = :unNombre";
        Query<Ubicacion> query = session.createQuery(hql, Ubicacion.class);
        query.setParameter("unNombre", nombre);
        return query.getSingleResult();
    }

    /**
     * Actualiza una {@link Ubicacion} en la base de datos. Si no existe la ubicacion, no hace nada.
     * @param ubicacion - La {@link Ubicacion} que se va a actualizar.
     * @throws {@link PersistenceException} - si se cambia el nombre de la ubicacion por una que ya esta o si se altera la id.
     */
    @Override
    public void actualizar(Ubicacion ubicacion)
    {
        Session session = Runner.getCurrentSession();
        session.update(ubicacion);
    }

    public Bicho recuperarCampeonHistoricoDe(String dojo){

        Session session = Runner.getCurrentSession();
        String hql = "SELECT c.bichoCampeon "
                    +"FROM Dojo as d inner join d.campeonesHistoricos as c "
                    +"WHERE d.nombre = :unNombre "
                    +"order by DATEDIFF(c.fechaInicioDeCampeon, c.fechaFinDeCampeon) asc";
        Query<Bicho> query = session.createQuery(hql, Bicho.class);
        query.setParameter("unNombre", dojo);
        return query.getResultList().get(0);


    }

}
