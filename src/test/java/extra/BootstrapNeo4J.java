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
            String query =  "CREATE (u1:Ubicacion :Pueblo {name:'Pueblo Lavanda'}) " +
                            "CREATE (u2:Ubicacion :Dojo {name:'Dojo Lavanda'}) " +
                            "CREATE (u3:Ubicacion :Pueblo {name:'Pueblo Origen'}) " +
                            "CREATE (u4:Ubicacion :Dojo {name:'Dojo Origen'}) " +
                            "CREATE (u5:Ubicacion :Guarderia {name:'La Guarderia'}) " +

                            "CREATE (u1)-[camino:CaminoA]->(u2) " +
                            "SET camino.tipo = 'AEREO' " +
                            "SET camino.costo = 5 " +

                            "CREATE (u4)-[camino2:CaminoA]->(u1) " +
                            "SET camino2.tipo = 'AEREO' " +
                            "SET camino2.costo = 5 " +

                            "CREATE (u1)-[camino3:CaminoA]->(u3) " +
                            "SET camino3.tipo = 'TERRESTRE' " +
                            "SET camino3.costo = 1 " +

                            "CREATE (u3)-[camino4:CaminoA]->(u1) " +
                            "SET camino4.tipo = 'TERRESTRE' " +
                            "SET camino4.costo = 1 " +

                            "CREATE (u4)-[camino5:CaminoA]->(u3) " +
                            "SET camino5.tipo = 'TERRESTRE' " +
                            "SET camino5.costo = 1 " +

                            "CREATE (u3)-[camino6:CaminoA]->(u4) " +
                            "SET camino6.tipo = 'TERRESTRE' " +
                            "SET camino6.costo = 1 " +

                            "CREATE (u2)-[camino7:CaminoA]->(u1) " +
                            "SET camino7.tipo = 'TERRESTRE' " +
                            "SET camino7.costo = 1 " +

                            "CREATE (u2)-[camino8:CaminoA]->(u4) " +
                            "SET camino8.tipo = 'MARITIMO' " +
                            "SET camino8.costo = 2 " +

                            "CREATE (u2)-[camino9:CaminoA]->(u3) " +
                            "SET camino9.tipo = 'MARITIMO' " +
                            "SET camino9.costo = 2 " +

                            "CREATE (u2)-[camino10:CaminoA]->(u4) " +
                            "SET camino10.tipo = 'AEREO' " +
                            "SET camino10.costo = 5 " +

                            "CREATE (u1)-[camino11:CaminoA]->(u2) " +
                            "SET camino11.tipo = 'TERRESTRE' " +
                            "SET camino11.costo = 1 " +

                            "CREATE (u3)-[camino12:CaminoA]->(u5) " +
                            "SET camino12.tipo = 'AEREO' " +
                            "SET camino12.costo = 5 ";

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