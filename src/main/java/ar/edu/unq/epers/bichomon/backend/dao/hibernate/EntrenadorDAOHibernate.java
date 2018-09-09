package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

public class EntrenadorDAOHibernate implements EntrenadorDAO {

    @Override
    public void guardar(Entrenador entrenador)
    {

    }

    @Override
    public Entrenador recuperar(String nombre) {
        return null;
    }

    public void crerDatosIniciales()
    {
        Entrenador entrenador1  = new Entrenador();
        entrenador1.setNombre("Pepe Pepon");
        entrenador1.setExperiencia(0);
        entrenador1.setNivel(1);

        Session session = Runner.getCurrentSession();
        session.save(entrenador1);
    }
}
