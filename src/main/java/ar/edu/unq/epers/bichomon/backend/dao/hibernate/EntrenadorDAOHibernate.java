package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH;

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
        // TODO: 12/10/2018 falta test para que filtre hasta 10 entrenadores
        Session session = Runner.getCurrentSession();
//        String hql3 = "Select dojo.campeonActual.bichoCampeon.duenio from Dojo dojo where dojo.campeonActual is not null group by dojo.campeonActual.bichoCampeon.duenio order by dojo.campeonActual.fechaInicioDeCampeon";

//        String hql = "SELECT dojo.campeonActual.bichoCampeon.duenio AS entrenador " +
//                     "FROM (" +
//                            "SELECT entrenador, MIN(dojo.campeonActual.fechaInicioDeCampeon) AS fechaInicioDeCampeon " +
//                            "FROM Dojo dojo WHERE dojo.campeonActual IS NOT NULL " +
//                            "GROUP BY entrenador )" +
//                     "ORDER BY fechaInicioDeCampeon DESC";

//        String hql = "SELECT dojo.campeonActual.bichoCampeon.duenio " +
//                     "FROM Dojo dojo WHERE dojo.campeonActual IS NOT NULL " +
//                     "GROUP BY dojo.campeonActual.bichoCampeon.duenio " +
//                     "ORDER BY dojo.campeonActual.fechaInicioDeCampeon";

//        String hql = "SELECT dojo.campeonActual.bichoCampeon.duenio " +
//                     "FROM Dojo dojo WHERE dojo.campeonActual IS NOT NULL " +
//                     "ORDER BY dojo.campeonActual.fechaInicioDeCampeon ASC";

        String hql = "SELECT dojo.campeonActual.bichoCampeon.duenio " +
                     "FROM Dojo dojo " +
                     "WHERE dojo.campeonActual IS NOT NULL " +
                     "ORDER BY dojo.campeonActual.fechaInicioDeCampeon ASC";

        Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);
        query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        query.setMaxResults(10);
        return query.getResultList();
    }

}
