package ar.edu.unq.epers.test.bichomon.dao.neo4j;

import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import extra.BootstrapNeo4J;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UbicacionDAONEO4JTest
{
    private BootstrapNeo4J bootstrapNeo4j;
    private UbicacionDAONEO4J ubicacionDAONEO4JSUT;

    @Before
    public void setUp() throws Exception
    {
        this.ubicacionDAONEO4JSUT   = new UbicacionDAONEO4J();
        this.bootstrapNeo4j         = new BootstrapNeo4J();
        this.bootstrapNeo4j.crearDatos();
    }

    @After
    public void tearDown() throws Exception {   this.bootstrapNeo4j.limpiarTabla(); }

    @Test
    public void SiLePidoElCaminoADojoLavandaDesdePuebloOrigenMeDevuelveElCaminoMasBaratocaminoA()
    {
        //Setup (Given)
        Camino camino;
        //Exercise (When)
        this.ubicacionDAONEO4JSUT.caminoA("Pueblo Origen", "Dojo Lavanda");
        //Test (Then)
    }
}