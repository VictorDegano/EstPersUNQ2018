package ar.edu.unq.epers.bichomon.backend.service.busqueda;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOBicho;
import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
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
                        SearchHits aciertos = this.elasticSearchDAOEntrenador.buscarEntrenadoresConCiertaExperiencia(desde, hasta)
                                                                             .getHits();

                        List<String> nombreDeEntrenadores = new ArrayList<>();
                        aciertos.forEach((x)->{nombreDeEntrenadores.add(x.getSourceAsMap()
                                                                         .get("nombre")
                                                                         .toString());});

                        return entrenadorDAO.recuperarEntrenadores(nombreDeEntrenadores);
                    });
    }

    @Override
    public List<Entrenador> BuscarEntrenadorPorNombre(String nombre)
    {
        return Runner.runInSession(() -> {
                        SearchHits aciertos = this.elasticSearchDAOEntrenador.buscarPorNombre(nombre)
                                                                             .getHits();

                        List<String> nombreDeEntrenadores = new ArrayList<>();
                        aciertos.forEach((x)->{nombreDeEntrenadores.add(x.getSourceAsMap()
                                                                         .get("nombre")
                                                                         .toString());});

                        return entrenadorDAO.recuperarEntrenadores(nombreDeEntrenadores);
                    });
    }

    @Override
    public List<Bicho> BuscarPorDuenio(String nombreDelEntrenador)
    {
        return Runner.runInSession(() -> {
                        SearchHits aciertos = this.elasticSearchDAOBicho.buscarPorDuenio(nombreDelEntrenador)
                                                                        .getHits();

                        List<Integer> idsDeLosBichos = new ArrayList<>();
                        aciertos.forEach((x)->{ idsDeLosBichos.add(Integer.valueOf(x.getSourceAsMap()
                                                                                    .get("modelId")
                                                                                    .toString()));});

                        return bichoDAO.recuperarBichos(idsDeLosBichos);
                    });
    }
}
