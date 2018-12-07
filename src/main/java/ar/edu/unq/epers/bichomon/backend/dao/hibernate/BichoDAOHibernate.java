package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class BichoDAOHibernate implements BichoDAO
{
    /**
     * Guarda un {@link Bicho} en la base de datos. Si ya existe el Bicho da una excepcion.
     * @param bicho - El {@link Bicho} a guardar.
     * @throws {@link PersistenceException} - si el objeto a guardar ya existe.
     */
    @Override
    public void guardar(Bicho bicho)
    {
        Session session = Runner.getCurrentSession();
        session.save(bicho);
    }

    /**
     * Dado el Id de un {@link Bicho} lo retorna desde la base de datos.
     * Si el bicho no existe retorna null
     * @param id - el id del {@link Bicho} que se quiere recuperar.
     * @return {@link Bicho} si es encontrado o Null en caso contrario.
     */
    @Override
    public Bicho recuperar(int id)
    {
        Session session = Runner.getCurrentSession();
        return session.get(Bicho.class,id);
    }

    /**
     * Actualiza el {@link Bicho} en la base de datos. Si no existe el bicho, no hace nada.
     * @param bicho - El {@link Bicho} que se va a actualizar.
     * @throws {@link PersistenceException} - si se cambia el id del bicho por una que ya esta o si se altera la id.
     */
    @Override
    public void actualizar(Bicho bicho)
    {
        Session session = Runner.getCurrentSession();
        session.update(bicho);
    }

    @Override
    public List<Bicho> recuperarBichos(List<Integer> idsDeLosBichos)
    {
        {
            Session session = Runner.getCurrentSession();
            String hql      = "FROM Bicho bicho " +
                              "WHERE bicho.id IN :listaDeIds";

            Query<Bicho> query = session.createQuery(hql, Bicho.class);
            query.setParameter("listaDeIds", idsDeLosBichos);

            return query.getResultList();
        }
    }
}
