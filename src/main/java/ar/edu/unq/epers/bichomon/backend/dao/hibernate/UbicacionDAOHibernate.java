package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;

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
        String hql      = "FROM Ubicacion u " +
                          "WHERE u.nombre = :unNombre";
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

    public Bicho recuperarCampeonHistoricoDe(String dojo)
    {
        Session session = Runner.getCurrentSession();
        String hql      = "SELECT c.bichoCampeon "+
                          "FROM Campeon AS c " +
                          "WHERE c.dojo.nombre = :unNombre " +
                          "ORDER BY DATEDIFF(c.fechaInicioDeCampeon, ifnull(c.fechaFinDeCampeon, now()))";
        Query<Bicho> query = session.createQuery(hql, Bicho.class);
        query.setParameter("unNombre", dojo);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public List<Ubicacion> recuperarUbicaciones(List<String> nombresDeUbicaciones) {
        List<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
        for(String nombreUbicacion: nombresDeUbicaciones){
            ubicaciones.add(this.recuperar(nombreUbicacion));
        }
        return ubicaciones;
    }

}
