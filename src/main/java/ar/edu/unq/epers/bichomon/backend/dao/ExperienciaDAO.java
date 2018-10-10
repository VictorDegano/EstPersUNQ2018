package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.TipoExperiencia;

public interface ExperienciaDAO
{
    void guardar(Experiencia unTipoDeExperiencia);

    Experiencia recuperar(TipoExperiencia nombre);

    void actualizar(Experiencia unTipoDeExperiencia);
}
