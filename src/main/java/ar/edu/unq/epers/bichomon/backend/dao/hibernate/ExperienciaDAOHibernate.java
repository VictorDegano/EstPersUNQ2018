package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.ExperienciaDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ExperienciaDAOHibernate implements ExperienciaDAO
{
    @Override
    public void guardar(String nombre, int valor) { }

    @Override
    public int recuperar(String nombre)
    {
        Session session = Runner.getCurrentSession();
        Query query = session.createNativeQuery("SELECT e.experiencia from experiencia where e.nombre = :nombre");
//        query.setParameter("?", nombre);
//        String hql =  "SELECT experiencia from Experiencia where nombre = :nombre;";
//        String hql =  "FROM experiencia i WHERE i.nombre = :unNombre ";
//        Query query = session.createNativeQuery(hql, int.class);

        query.setParameter("nombre", nombre);
        return query.getFirstResult();

    }

    @Override
    public void actualizar(String nombre, int valor) {  }
}
