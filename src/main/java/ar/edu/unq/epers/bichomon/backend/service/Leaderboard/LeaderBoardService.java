package ar.edu.unq.epers.bichomon.backend.service.Leaderboard;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface LeaderBoardService
{

    /**
     * Da una lista de los entrenadores que poseen actualmente un campeon. Esta lista retorna los
     * entrenadores ordenados por el mayor tiempo que un campeon lleva en el dojo.
     * @return Una lista de {@link Entrenador}
     */
    List<Entrenador> campeones();

    /**
     * Devuelve la especie que mas bichos haya tenido como campeones. Si un bicho ha sido mas
     * de una vez campeon, esto se cuenta como 1.
     * @return La {@link Especie} que ha tenido la mayor cantidad de campeones
     */
    Especie especieLider();

    /**
     * Da los 10 primeros entrenadores donde la suma del poder de combate de todos sus bichos sea superior
     * @return Una lista de {@link Entrenador}es mas poderosos.
     */
    List<Entrenador> lideres();
}
