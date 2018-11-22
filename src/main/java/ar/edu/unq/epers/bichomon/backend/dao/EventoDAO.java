package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;

import java.util.List;

public interface EventoDAO
{
    void guardar(Evento evento);

    Evento recuperar(String objectId);

    void actualizar(Evento evento);

    List<Evento> feedDeEntrenador(String entrenador);

    List<Evento> feedDeUbicaciones(List<String> ubicaciones);

    void deleteAll();
}
