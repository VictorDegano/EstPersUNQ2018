package ar.edu.unq.epers.bichomon.backend.service.Leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.List;

public class LeaderBoardServiceImplementation implements LeaderBoardService
{
    private EntrenadorDAO entrenadorDAO;
    private EspecieDAO especieDAO;

    public LeaderBoardServiceImplementation(EntrenadorDAO unEntreandorDAO, EspecieDAO unEspecieDAO)
    {
        this.especieDAO     = unEspecieDAO;
        this.entrenadorDAO  = unEntreandorDAO;
    }

    @Override
    public List<Entrenador> campeones()
    {   return Runner.runInSession(() -> { return entrenadorDAO.campeones();}); }

    @Override
    public Especie especieLider()
    {   return Runner.runInSession(() -> { return especieDAO.especieLider();});   }

    @Override
    public List<Entrenador> lideres()
    {   return Runner.runInSession(() -> { return entrenadorDAO.lideres();}); }
}
