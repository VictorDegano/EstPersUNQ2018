package extra;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class BootstrapNeo4J
{

    private Driver driver;

    public BootstrapNeo4J()
    {   this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "root" ) ); }

    public void crearDatos()
    {
        org.neo4j.driver.v1.Session session = this.driver.session();

        try
        {
            String query =  "CREATE (u1:Ubicacion :Pueblo {nombre:'Pueblo Lavanda'}) " +
                            "CREATE (u2:Ubicacion :Dojo {nombre:'Dojo Lavanda'}) " +
                            "CREATE (u3:Ubicacion :Pueblo {nombre:'Pueblo Origen'}) " +
                            "CREATE (u4:Ubicacion :Dojo {nombre:'Dojo Origen'}) " +
                            "CREATE (u5:Ubicacion :Guarderia {nombre:'La Guarderia'}) " +

                            "CREATE (u1)-[camino:CaminoA {tipo:'AEREO' , costo:5}]->(u2) " +
                            "CREATE (u4)-[camino2:CaminoA{tipo:'AEREO' , costo:5}]->(u1) " +
                            "CREATE (u1)-[camino3:CaminoA {tipo:'TERRESTRE' , costo:1}]->(u3) " +
                            "CREATE (u3)-[camino4:CaminoA {tipo:'TERRESTRE' , costo:1}]->(u1) " +
                            "CREATE (u4)-[camino5:CaminoA {tipo:'TERRESTRE' , costo:1}]->(u3) " +
                            "CREATE (u3)-[camino6:CaminoA {tipo:'TERRESTRE' , costo:1}]->(u4) " +
                            "CREATE (u2)-[camino7:CaminoA {tipo:'TERRESTRE' , costo:1}]->(u1) " +
                            "CREATE (u2)-[camino8:CaminoA {tipo:'MARITIMO' , costo:2}]->(u4) " +
                            "CREATE (u2)-[camino9:CaminoA {tipo:'MARITIMO' , costo:2}]->(u3) " +
                            "CREATE (u2)-[camino10:CaminoA {tipo:'AEREO' , costo:5}]->(u4) " +
                            "CREATE (u1)-[camino11:CaminoA {tipo:'TERRESTRE' , costo:1}]->(u2) " +
                            "CREATE (u3)-[camino12:CaminoA {tipo:'AEREO' , costo:5}]->(u5) ";
            session.run(query);
        }
        finally {   session.close();    }
    }


    public void limpiarTabla()
    {
        org.neo4j.driver.v1.Session session = this.driver.session();

        try
        {
            String query =  "MATCH (n) " +
                            "WITH n LIMIT 10000 " +
                            "DETACH DELETE n";
            session.run(query);
        }
        finally {   session.close();    }
    }
}
