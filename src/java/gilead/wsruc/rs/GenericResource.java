/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.wsruc.rs;

import gilead.wsruc.dao.impl.DaoPadronRUCImpl;
import gilead.wsruc.model.BeanPadronRUC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author Luis
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * gilead.wsruc.rs.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("consultaruc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsultaRUC(@QueryParam("ruc") String ruc) {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        System.out.println("getJson: Inicio");
        DaoPadronRUCImpl cImpl = new DaoPadronRUCImpl();
        BeanPadronRUC padronRUC = cImpl.getPadronRUC(ruc);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(padronRUC);
            System.out.println("json = " + json);
        } catch (JsonProcessingException ex) {
            System.out.println("Error JsonProcessingException: " + ex.getMessage());
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Error IOException: " + ex.getMessage());
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("getJson: Fin");
        return Response.ok(json).build();
    }

    @GET
    @Path("consultadni")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsultaDNI(@QueryParam("dni") String dni) {
        String salida = null;
        System.out.println("ENTRO");
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet("http://aplicaciones007.jne.gob.pe/srop_publico/Consulta/Afiliado/GetNombresCiudadano?DNI=" + dni);
            HttpResponse httpResponse = httpClient.execute(getRequest);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpResponse.getStatusLine().getStatusCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (httpResponse.getEntity().getContent()), "UTF-8"));

            String output;
            String[] aux = null;
            while ((output = br.readLine()) != null) {
                aux = output.split("\\|", 0);
            }

            if (aux != null) {
                salida = aux[2] + " " + aux[0] + " " + aux[1];
            }
        } catch (IOException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(salida).build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void putJson(String content) {
//    }
}
