package ar.edu.unq.epers.bichomon.backend.dao.elastic;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchDAOEntrenador {

    private TransportClient getClient(){
        try
        {
            return  new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        }
        catch (UnknownHostException e)
        {   e.printStackTrace();    }
        return null;
    }

    public IndexResponse indexar(Entrenador unEntrenadorAIndexar)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        //hacemos lo que tengamos que hacer...
        //Transformar el objeto en JSON, podemos usar un map, serializarlo o utilizar librerias como jakson, O hacer el Json a mano como un string
        Map<String, Object> jsonAIndexar = new HashMap<String, Object>();
        jsonAIndexar.put("modelId", unEntrenadorAIndexar.getId());
        jsonAIndexar.put("nombre", unEntrenadorAIndexar.getNombre());
        jsonAIndexar.put("exp", unEntrenadorAIndexar.getExperiencia());
        jsonAIndexar.put("nivel", unEntrenadorAIndexar.getNivel().getNroDeNivel());
        jsonAIndexar.put("ubicacion", unEntrenadorAIndexar.getUbicacion().getNombre());
        jsonAIndexar.put("bichosCapturados", unEntrenadorAIndexar.nombreDeBichos());
        jsonAIndexar.put("billetera", unEntrenadorAIndexar.getBilletera());

        //Indexa                                      //Indice,Tipo,Id(El Id es Opcional)
        IndexResponse respuesta = client.prepareIndex("entrenadorindex","entrenador")
                                        .setSource(jsonAIndexar)
                                        .get();

        client.close();

        return respuesta;
    }

    public DeleteResponse borrar(String id)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        DeleteResponse respuesta    = client.prepareDelete("entrenadorindex","entrenador", id)
                                            .get();

        client.close();

        return respuesta;
    }


    public GetResponse get(String id)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        GetResponse respuesta    = client.prepareGet("entrenadorindex","entrenador", id)
                                         .get();

        client.close();

        return respuesta;
    }

    public SearchResponse buscarPorNombre(String nombre)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        SearchResponse respuesta    = client.prepareSearch("entrenadorindex")
                .setQuery(QueryBuilders.matchQuery("nombre",nombre))
                .get();

        client.close();

        return respuesta;
    }


    public SearchResponse buscarEntrenadoresConCiertaExperiencia(int desde , int hasta){
        TransportClient client = getClient();
        SearchResponse respuesta = client.prepareSearch("entrenadorindex")
                                         .setSearchType(SearchType.DEFAULT)
                                         .setQuery(QueryBuilders.matchQuery("_type","entrenador"))
                                         .setPostFilter(QueryBuilders.rangeQuery("exp")
                                                                     .from(desde)
                                                                     .to(hasta))
                                         .addSort("exp", SortOrder.ASC)
                                         .get();

        client.close();
        return respuesta;

    }


    public void deleteAll()
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        //Borra el Indice
        AcknowledgedResponse deleteResponse = client.admin()
                                                    .indices()
                                                    .delete(new DeleteIndexRequest("entrenadorindex"))
                                                    .actionGet();

        //Vuelve a crear el Indice
        client.admin().indices().prepareCreate("entrenadorindex").get();

        client.close();
    }

    public SearchResponse buscarEntrenadoresDeNivel(int level) {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        SearchResponse respuesta    = client.prepareSearch("entrenadorindex")
                .setQuery(QueryBuilders.matchQuery("nivel",level))
                .get();

        client.close();

        return respuesta;
    }
}
