package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

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

    public List<Entrenador> campeones() {
        Session session = Runner.getCurrentSession();
//        String hql = "select bicho.especie from Bicho bicho where bicho.duenio is null group by bicho.especie order by count(bicho.especie)";
        String hql = "Select dojo.campeon.bichoCampeon.duenio from Dojo dojo where dojo.campeon is not null group by dojo.campeon.fechaInicioDeCampeon order by count(dojo.campeon.bichoCampeon.duenio)";
        Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

}
