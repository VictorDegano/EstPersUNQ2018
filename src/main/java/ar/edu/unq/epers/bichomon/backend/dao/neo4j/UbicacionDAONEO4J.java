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
            String query = "MERGE (u:Ubicacion :" + ubicacion.getClass().getSimpleName() + ")" +
                    "SET u.nombre = {elNombre} ";
            session.run(query, Values.parameters(
                    "elNombre", ubicacion.getNombre()));


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


    public void conectar(String ubicacion1 , String ubicacion2 , String tipoCamino){

        Session session = this.driver.session();
        try{
            String query = "MATCH (ubicacion1:Ubicacion {nombre:{elNombre1} }) "+
                           "MATCH (ubicacion2:Ubicacion {nombre:{elNombre2}}) " +
                           "MERGE (ubicacion1) -[:" + tipoCamino +"] -> (ubicacion2) ";

            session.run(query,Values.parameters(
                    "elNombre1",ubicacion1,
                                  "elNombre2",ubicacion2));
        }

        finally {
            session.close();
        }

       /* MATCH (u:Ubicacion{nombre: "Guarderia No te quiere nadie"})
        MATCH (u2:Ubicacion{nombre:"Dojo"})
        MATCH (u)-[r]->(u2)
        return r*/



    }




}


