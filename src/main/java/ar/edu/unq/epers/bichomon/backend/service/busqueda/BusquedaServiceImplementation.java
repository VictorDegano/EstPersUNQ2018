package ar.edu.unq.epers.bichomon.backend.service.busqueda;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOBicho;
import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import java.util.List;

public class BusquedaServiceImplementation implements BusquedaService
{
    private EntrenadorDAO entrenadorDAO;
    private BichoDAO bichoDAO;
    private ElasticSearchDAOBicho elasticSearchDAOBicho;
    private ElasticSearchDAOEntrenador elasticSearchDAOEntrenador;

    public BusquedaServiceImplementation(EntrenadorDAO entrenadorDAO,
                                         BichoDAO bichoDAO,
                                         ElasticSearchDAOBicho elasticSearchDAOBicho,
                                         ElasticSearchDAOEntrenador elasticSearchDAOEntrenador)
    {
        this.entrenadorDAO              = entrenadorDAO;
        this.bichoDAO                   = bichoDAO;
        this.elasticSearchDAOBicho      = elasticSearchDAOBicho;
        this.elasticSearchDAOEntrenador = elasticSearchDAOEntrenador;
    }


    @Override
    public List<Entrenador> BuscarEntrenadoresPorExperiencia(int desde, int hasta)
    {
        return Runner.runInSession(() -> {
                        return this.entrenadorDAO
                                   .recuperarEntrenadores(this.elasticSearchDAOEntrenador
                                                              .nombresDeEntrenadoresConCiertaExperiencia(desde,hasta));
                    });
    }

    @Override
    public List<Entrenador> BuscarEntrenadorPorNombre(String nombre)
    {
        return Runner.runInSession(() -> {
                        return this.entrenadorDAO
                                   .recuperarEntrenadores(this.elasticSearchDAOEntrenador
                                                              .nombresDeEntrenadoresConCiertoNombre(nombre));
                    });
    }

    @Override
    public List<Bicho> BuscarPorDuenio(String nombreDelEntrenador)
    {
        return Runner.runInSession(() -> {
                       return this.bichoDAO
                                  .recuperarBichos(this.elasticSearchDAOBicho
                                                       .idsDeBichosConDuenioDeCiertoNombre(nombreDelEntrenador));
                    });
    }


    @Override
    public List<Entrenador> BuscarEntrenadoresPorNivel(int nivel){
        return Runner.runInSession(()->{
                        return this.entrenadorDAO
                                   .recuperarEntrenadores(this.elasticSearchDAOEntrenador
                                                              .nombresDeEntrenadoresConCiertoNivel(nivel));
                    });

    }

    @Override
    public List<Bicho>TopTres(){
        return Runner.runInSession(()->{
                        return this.bichoDAO
                                   .recuperarBichos(this.elasticSearchDAOBicho
                                                        .idsDeBichosEnElTopTresDeVictorias());
                    });
    }
}
