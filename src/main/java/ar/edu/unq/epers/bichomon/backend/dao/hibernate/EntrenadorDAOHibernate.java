package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class EntrenadorDAOHibernate implements EntrenadorDAO {

    @Override
    public void guardar(Entrenador entrenador)
    {
        Session session = Runner.getCurrentSession();
        session.save(entrenador);
    }

    @Override
    public Entrenador recuperar(String nombre)
    {
        Session session = Runner.getCurrentSession();
        String hql = "FROM Entrenador e WHERE e.nombre = :unNombre";
        Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
        query.setParameter("unNombre", nombre);
        return query.getSingleResult();
    }

    @Override
    public void actualizar(Entrenador entrenador)
    {
        Session session = Runner.getCurrentSession();
        session.update(entrenador);
    }

}
