package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;

public interface EventoDAO {
    void guardar();

    Evento recuperar(String objectId);

    void actualizar(Evento evento);




}
