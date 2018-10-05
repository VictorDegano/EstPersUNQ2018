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
        String hql = "FROM Experiencia e WHERE e.nombre = :unNombre";
        Query<Experiencia> query = session.createQuery(hql, Experiencia.class);
//        Query query = session.createNativeQuery("SELECT experiencia from experiencia where nombre = :nombre");
        query.setParameter("unNombre", nombre);
        return query.getSingleResult();
//        return session.get(Experiencia.class, nombre);

    }

    @Override
    public void actualizar(Experiencia unTipoDeExperiencia) {  }
}
