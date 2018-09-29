package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import org.hibernate.Session;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@SuppressWarnings("ALL")
public class EspecieDAOHibernate implements ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO {


    @Override
    public void guardar(Especie especie) {
        Session session = Runner.getCurrentSession();
        session.save(especie);
    }
    // falta HQL
    @Override
    public Especie recuperar(String nombreEspecie) {
        Session session = Runner.getCurrentSession();
        String hql =  "FROM Especie i "
                + "WHERE i.nombre = :unNombre ";
        Query<Especie> query = session.createQuery(hql,  Especie.class);
        query.setParameter("unNombre", nombreEspecie);
        return query.getSingleResult();
    }

    @Override
    public void actualizar(Especie especie) {
        Session session = Runner.getCurrentSession();
        session.update(especie);

    }

    @Override
    public List<Especie> recuperarTodos() {
        Session session = Runner.getCurrentSession();
        String hql = "FROM Especie e order by e.nombre asc";
        Query<Especie> query = session.createQuery(hql,  Especie.class);
        return query.getResultList();
    }

    public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
        Especie especie = this.recuperar(nombreEspecie);
        especie.setCantidadBichos(especie.getCantidadBichos() + 1);
        this.actualizar(especie);
        Bicho nuevoBicho = new Bicho(especie,nombreBicho);
        return nuevoBicho;
    }

    public List<Especie> populares(){
        Session session = Runner.getCurrentSession();
        String hql = ""
    }

}
