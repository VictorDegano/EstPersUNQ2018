package ar.edu.unq.epers.bichomon.backend.dao.neo4j;

import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import org.neo4j.driver.v1.*;

public class UbicacionDAONEO4J
{

    private Driver driver;

    public UbicacionDAONEO4J()
    {   this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "root" ) ); }

    public Camino caminoA(String nombreUbicacionOrigen, String nombreUbicacionDestino)
    {
        Session session = this.driver.session();

        try
        {
            String query =  "MATCH (u1:Ubicacion {name: {ubicacionOrigen}}) " +
                            "MATCH (u2:Ubicacion {name: {ubicacionDonde}) " +
                            "MATCH path = (u1)-[*1]->(u2) " +
                            "WITH path, EXTRACT(p in relationships(path) | p.costo) AS costo " +
                            "RETURN path " +
                            "ORDER BY costo " +
                            "LIMIT 1";

            StatementResult result = session.run(query, Values.parameters("ubicacionOrigen", nombreUbicacionOrigen, "ubicacionDonde", nombreUbicacionDestino));

            return result.list(record -> {
                                    Value camino    = record.get(0);
                                    String inicio   = camino.get("name").asString();
                                    String tipo     = camino.get("tipo").asString();
                                    int costo       = camino.get("costo").asInt();;
                                    String donde    = camino.get("name").asString();
                                    return new Camino(inicio, donde, tipo, costo);
                                }).get(0);
        } finally {
            session.close();
        }
    }


}
