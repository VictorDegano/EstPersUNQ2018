package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

public class CondicionDeEvolucionDAOHibernate implements CondicionDeEvolucionDAO
{
    @Override
    public void guardar(CondicionEvolucion condicion)
    {
        Session session = Runner.getCurrentSession();
        session.save(condicion);
    }

    @Override
    public CondicionEvolucion recuperar(int idCondicion)
    {
        Session session = Runner.getCurrentSession();
        return session.get(CondicionEvolucion.class, idCondicion);
    }
}
