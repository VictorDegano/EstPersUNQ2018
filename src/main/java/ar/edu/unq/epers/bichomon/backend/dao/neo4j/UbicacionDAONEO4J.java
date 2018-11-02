package ar.edu.unq.epers.bichomon.backend.dao.neo4j;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import javax.swing.plaf.nimbus.State;
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
            String query = "MERGE (u:Ubicacion {tipo: {elTipo}})" +
                    "SET u.nombre = {elNombre} ";
            session.run(query, Values.parameters(
                    "elId", ubicacion.getId(),
                    "elNombre", ubicacion.getNombre(),
                    "elTipo",ubicacion.getClass().getSimpleName()));

        } finally {
            session.close();
        }
    }


    public Boolean existeUbicacion(String nombre){
        Session session = this.driver.session();
        try{
            String query = "MATCH (u:Ubicacion{nombre: {elNombre}})" +
                    "return u";


            StatementResult result = session.run(query,Values.parameters("elNombre",nombre));

            if (result.hasNext()){
                return true;
            }
            else{
                return false;
            }

        }
        finally{
            session.close();
        }
    }







}


