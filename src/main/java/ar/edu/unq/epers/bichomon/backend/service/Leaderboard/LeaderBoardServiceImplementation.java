package ar.edu.unq.epers.bichomon.backend.service.Leaderboard;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.List;

public class LeaderBoardServiceImplementation implements LeaderBoardService
{
    private EntrenadorDAO entrenadorDAO;

    public LeaderBoardServiceImplementation(EntrenadorDAO unEntreandorDAO)
    {
        this.entrenadorDAO  = unEntreandorDAO;
    }

    @Override
    public List<Entrenador> campeones()
    {   return Runner.runInSession(() -> { return entrenadorDAO.campeones();}); }

    @Override
    public Especie especieLider() {
        return null;
    }

    @Override
    public List<Entrenador> lideres()
    {   return Runner.runInSession(() -> { return entrenadorDAO.lideres();}); }
}
