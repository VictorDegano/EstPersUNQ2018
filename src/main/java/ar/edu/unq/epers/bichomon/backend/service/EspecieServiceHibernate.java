package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;

import java.util.Collection;
import java.util.List;

public class EspecieServiceHibernate implements EspecieService {


    private EspecieDAOHibernate especieDAO;

    public EspecieServiceHibernate(EspecieDAOHibernate especieDAO) {
        this.especieDAO = especieDAO;
    }

    public void obtenerEspecie(String nombreEspecie) {
        Runner.runInSession(() -> {
            Especie personaje = this.especieDAO.recuperar(nombreEspecie);
            return null;
        });
    }

    @Override
    public void crearEspecie(Especie especie) {
        Runner.runInSession(() -> {
            especieDAO.guardar(especie);
            return null;
        });
    }

    @Override
    public Especie getEspecie(String nombreEspecie) {
        return Runner.runInSession(() -> {
            return this.especieDAO.recuperar(nombreEspecie);
        });
    }

    @Override
    public List<Especie> getAllEspecies() {
        return null;
    }

    @Override
    public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
        return null;
    }
}