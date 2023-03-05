package Controller;

import id.kawahEdukasi.model.Item;
import service.ItemService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {
    @Inject
    ItemService itemService;

    // Detail Item by id

    @GET
    public Response get(){
        return itemService.get();
    }
    @Path("/{id}")
    @GET
    public Response get(@PathParam("id") Long id){
        return itemService.get();
    }



    @POST
    public Response post(Map<String, Object> request){

        return itemService.post(request);
    }


    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request){

        return itemService.put(id, request);
    }



    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){

        return itemService.delete(id);
    }
}
