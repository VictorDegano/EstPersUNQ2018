package ar.edu.unq.epers.bichomon.backend.service.busqueda;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.List;

public interface BusquedaService
{
    List<Entrenador> BuscarEntrenadoresPorExperiencia(int desde, int hasta);

    List<Entrenador> BuscarEntrenadorPorNombre(String nombre);

    List<Bicho> BuscarPorDuenio(String nombreDelEntrenador);

    List<Entrenador>BuscarEntrenadoresPorNivel(int nivel);

    List<Bicho>TopTres();
}
