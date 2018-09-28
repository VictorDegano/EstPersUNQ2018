package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/**Clase que implementa los servicios necesarios para la utilizacion de los bichos.*/
public interface BichoService
{
    /**
     * Busca un bicho en la ubicacion actual del entrenador especificado.
     * @param entrenador - Nombre del entrenador que va a buscar un nuevo bicho.
     * @return  El {@link Bicho} que fue capturado si fue exitoso.
     */
    Bicho buscar(String entrenador);

    /**
     * El entrenador abandona al bicho en la ubicacion actual. Si la ubicacion no es una Guarderia tira UbicacionIncorrectaException
     * @param entrenador - Nombre del entrenador que va a reliazar el abandono
     * @param bicho - Id del bicho que va a ser abandonado
     * @throws UbicacionIncorrectaException si la ubicacion no es una guarderia.
     */
    void abandonar(String entrenador, int bicho);

    /**
     * Retorna true si el bicho puede evolucionar.
     * @param entrenador - Nombre del entrenador del bicho.
     * @param bicho - Id del bicho que se va a consultar sobre su estado de evolucion
     * @return true si el bicho puede evolucionar o false en el caso contrario
     */
    boolean puedeEvolucionar(String entrenador, int bicho);

    /**
     * Evoluciona el bicho especificado del entrenador si cumple las condiciones de evolucion.
     * @param entrenador - Nombre del entrenador del bicho
     * @param Bicho - Id del bicho que va a ser evolucionado
     * @return Un nuevo {@link Bicho} que es el resultante de la evolucion del bicho anterior.
     */
    Bicho evolucionar(String entrenador, int Bicho);

    /**
     *  El entrenador desafia al campeon del dojo.
     * @param entrenador
     * @param bicho
     * @return ResultadoCombate un log donde se muestra lo sucedido en el combate
     * @throws UbicacionIncorrectaException si la ubicacion no es un dojo.
     */
    //ResultadoCombate duelo(String entrenador, int bicho)
}
