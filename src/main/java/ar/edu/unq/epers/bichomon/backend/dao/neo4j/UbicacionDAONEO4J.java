package ar.edu.unq.epers.bichomon.backend.dao.neo4j;

import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionMuyLejanaException;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Path.Segment;
import org.neo4j.driver.v1.types.Relationship;

import java.util.ArrayList;
import java.util.List;

public class UbicacionDAONEO4J
{

    private Driver driver;

    public UbicacionDAONEO4J() {
        this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "root" ) );
    }

    public void create(Ubicacion ubicacion) {
        Session session = this.driver.session();
        try {
            String query =  "CREATE (u:Ubicacion :" + ubicacion.getClass().getSimpleName() + " {nombre:{elNombre}})";
            session.run(query, Values.parameters("elNombre", ubicacion.getNombre()));
        } finally {
            session.close();
        }
    }


    public Boolean existeUbicacion(String nombre){
        Session session = this.driver.session();
        try{
            String query =  "MATCH (u:Ubicacion{nombre: {elNombre}})" +
                            "return u";

            StatementResult result = session.run(query,Values.parameters("elNombre",nombre));

            return result.hasNext();
        }
        finally
        {   session.close();    }
    }


    public void conectar(Ubicacion ubicacion1 , Ubicacion ubicacion2 , TipoCamino tipoCamino){

        Session session = this.driver.session();
        try{
            String query = "MATCH (ubicacion1:Ubicacion {nombre:{elNombre1} }) "+
                           "MATCH (ubicacion2:Ubicacion {nombre:{elNombre2}}) " +
                           "CREATE (ubicacion1) -[:CaminoA{tipo:{tipoDeCamino}, costo:{unCosto}}] -> (ubicacion2) ";

            session.run(query,Values.parameters(
                    "elNombre1",ubicacion1.getNombre(),
                                   "elNombre2",ubicacion2.getNombre(),
                                   "tipoDeCamino", tipoCamino.name(),
                                   "unCosto", tipoCamino.costo()));
        }
        finally
        {   session.close();    }
    }
    public Boolean estanConectados(Ubicacion ubicacion1,Ubicacion ubicacion2){
            Session session = this.driver.session();
            try{
                String query = "MATCH (u:Ubicacion{nombre:{laUbicacion1}})" +
                               "MATCH (u2 : Ubicacion{nombre:{laUbicacion2}})"+
                               "MATCH (u)-[r]->(u2)"+
                               "return r";
                StatementResult result =  session.run(  query,
                                                        Values.parameters(  "laUbicacion1",
                                                                            ubicacion1.getNombre(),
                                                                            "laUbicacion2",
                                                                            ubicacion2.getNombre()));
               return result.hasNext();
            }
            finally {
                session.close();
            }
        }


    public List<Camino> caminoA(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino)
    {
        Session session = this.driver.session();
        try
        {
            String query =  "MATCH (u1:Ubicacion {nombre: {ubicacionOrigen}}) " +
                            "MATCH (u2:Ubicacion {nombre: {ubicacionDonde}}) " +
                            "MATCH path = (u1)-[*1]->(u2) " +
                            "WITH path, " +
                            "REDUCE(costo=0, x IN EXTRACT(p IN RELATIONSHIPS(path) | p.costo) | costo + x ) AS costo " +
                            "RETURN path " +
                            "ORDER BY costo " +
                            "LIMIT 1";

            StatementResult result = session.run(query, Values.parameters("ubicacionOrigen", ubicacionOrigen.getNombre(), "ubicacionDonde", ubicacionDestino.getNombre()));

            if (! result.hasNext())
            {   throw new UbicacionMuyLejanaException(ubicacionDestino.getNombre());  }

            Value resultRecord  = result.single().get(0);
            Path ruta           = resultRecord.asPath();
            List<Camino> caminos= new ArrayList<>();
            for (Segment segmento : ruta)
            {
                String inicio = segmento.start().get("nombre").asString();
                String tipo = segmento.relationship().get("tipo").asString();
                int costo = segmento.relationship().get("costo").asInt();
                String donde = segmento.end().get("nombre").asString();
                caminos.add(new Camino(inicio, donde, tipo, costo));
            }
            return caminos;
        }
        finally
        {   session.close();    }

//            if (result.hasNext())
//                return result.list(record -> {
//                                        Value ruta          = record.get(0);
//                                        Relationship camino = ruta.asPath().relationships().iterator().next();
//
//                                        String inicio   = ruta.asPath().start().get("nombre").asString();
//                                        String tipo     = camino.get("tipo").asString();
//                                        int costo       = camino.get("costo").asInt();
//                                        String donde    = ruta.asPath().end().get("nombre").asString();
//                                        return new Camino(inicio, donde, tipo, costo);
//                                    }).get(0);
//            else
//            {   throw new UbicacionMuyLejanaException(ubicacionDestino.getNombre());  }
//        }
//        finally
//        {   session.close();    }
    }


    public List<Camino> caminoMasCortoA(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino) {
        Session session = this.driver.session();
        try {
            String query =  "MATCH (u1:Ubicacion {nombre: {ubicacionOrigen}}) " +
                            "MATCH (u2:Ubicacion {nombre: {ubicacionDonde}}) " +
                            "RETURN shortestPath( (u1)-[*1..]->(u2))";

            StatementResult result = session.run(query, Values.parameters("ubicacionOrigen", ubicacionOrigen.getNombre(), "ubicacionDonde", ubicacionDestino.getNombre()));

            Value resultRecord  = result.single().get(0);
            if (resultRecord.isNull())
            {   throw new UbicacionMuyLejanaException(ubicacionDestino.getNombre());  }

            Path ruta           = resultRecord.asPath();
            List<Camino> caminos= new ArrayList<>();
            for (Segment segmento : ruta)
            {
                String inicio = segmento.start().get("nombre").asString();
                String tipo = segmento.relationship().get("tipo").asString();
                int costo = segmento.relationship().get("costo").asInt();
                String donde = segmento.end().get("nombre").asString();
                caminos.add(new Camino(inicio, donde, tipo, costo));
            }
            return caminos;
        }
        finally
        {   session.close();    }
    }


    public List<String> conectados(Ubicacion ubicacion, TipoCamino tipoDeCamino)
    {
        Session session = this.driver.session();
        try {
            String query =  "MATCH (u1:Ubicacion {nombre: {ubicacion}}) " +
                            "MATCH (ubicacion:Ubicacion)-[:CaminoA{tipo:{tipoDeCamino}}]->(u1) " +
                            "RETURN ubicacion";

            StatementResult result = session.run(query, Values.parameters("tipoDeCamino", tipoDeCamino.name(), "ubicacion", ubicacion.getNombre()));

            return result.list(record -> {  return record.get(0).asNode().get("nombre").asString();  });
        }
        finally
        {   session.close();    }
    }
}


