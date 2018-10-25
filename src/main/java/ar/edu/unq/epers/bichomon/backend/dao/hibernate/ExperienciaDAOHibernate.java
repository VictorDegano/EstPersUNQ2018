package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.ExperienciaDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.TipoExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ExperienciaDAOHibernate implements ExperienciaDAO
{
    @Override
    public void guardar(Experiencia unTipoDeExperiencia) { }

    @Override
    public Experiencia recuperar(TipoExperiencia nombre)
    {
        Session session = Runner.getCurrentSession();
        String hql = "FROM Experiencia e " +
                     "WHERE e.nombre = :unNombre";
        Query<Experiencia> query = session.createQuery(hql, Experiencia.class);
        query.setParameter("unNombre", nombre);
        return query.getSingleResult();
    }

    @Override
    public void actualizar(Experiencia unTipoDeExperiencia) {  }
}
