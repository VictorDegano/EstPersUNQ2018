package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDAO
{
    void guardar(Bicho unBicho);

    Bicho recuperar(int idBicho);

    void actualizar(Bicho unBicho);
}
