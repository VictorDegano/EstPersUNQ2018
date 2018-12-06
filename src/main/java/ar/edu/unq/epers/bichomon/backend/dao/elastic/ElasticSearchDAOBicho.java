package ar.edu.unq.epers.bichomon.backend.dao.elastic;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
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
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchDAOBicho
{

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

    public IndexResponse indexar(Bicho unBichoAIndexar)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        //hacemos lo que tengamos que hacer...
        //Transformar el objeto en JSON, podemos usar un map, serializarlo o utilizar librerias como jakson, O hacer el Json a mano como un string
        Map<String, Object> jsonAIndexar = new HashMap<String, Object>();
        jsonAIndexar.put("modelId", unBichoAIndexar.getId());
        jsonAIndexar.put("especie", unBichoAIndexar.getEspecie().getNombre());
        jsonAIndexar.put("energia", unBichoAIndexar.getEnergia());
        jsonAIndexar.put("duenio", unBichoAIndexar.getDuenio().getNombre());
        jsonAIndexar.put("victorias", unBichoAIndexar.getVictorias());
        jsonAIndexar.put("fechaDeCaptura", unBichoAIndexar.getFechaDeCaptura().toString());
        jsonAIndexar.put("entrenadoresAntiguos", unBichoAIndexar.nombresDeEntrenadoresAntiguos());

        //Indexa                                      //Indice,Tipo,Id(El Id es Opcional)
        IndexResponse respuesta = client.prepareIndex("bichosindex","bicho").setSource(jsonAIndexar).get();

        client.close();

        return respuesta;
    }


    public DeleteResponse borrar(String id)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        DeleteResponse respuesta    = client.prepareDelete("bichosindex","bicho", id).get();

        client.close();

        return respuesta;
    }

    public GetResponse get(String id)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        GetResponse respuesta    = client.prepareGet("bichosindex","bicho", id).get();

        client.close();

        return respuesta;
    }


    public SearchResponse buscarPorDuenio(String nombre)
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        SearchResponse respuesta    = client.prepareSearch("bichosindex")
                                            .setQuery(QueryBuilders.matchQuery("duenio", nombre)) //Esta query da los resultados de cualquier registro cuyo dueno contenga la palabra "nombre
                                            .get();

        client.close();

        return respuesta;
    }


    public void deleteAll()
    {
        //Conexion a la base, como hay un solo nodo solo se agrega ese
        TransportClient client = getClient();

        //Borra el Indice
        AcknowledgedResponse deleteResponse = client.admin().indices().delete(new DeleteIndexRequest("bichosindex")).actionGet();

        //Vuelve a crear el Indice
        client.admin().indices().prepareCreate("bichosindex").get();

        client.close();
    }
}
