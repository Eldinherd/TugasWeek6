package service;

import id.kawahEdukasi.model.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ItemService {
        public Response get(){

            return Response.status(Response.Status.OK).entity(Item.findAll().list()).build();
        }

        public Response get(@PathParam("id") Long id) {
        Optional<Item> optionalItem = Item.findByIdOptional(id);
        if (optionalItem.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Can't find Item"))
                    .build();
        }
            Item item = optionalItem.get();

            Map<String, Object> response = new HashMap<>();
            response.put("name", item.name);
            response.put("count", item.count);
            response.put("price", item.price);
            response.put("type", item.type);
            response.put("description", item.description);
            return Response.status(Response.Status.OK).entity(response).build();
        }

        @Transactional
        public Response post(Map<String, Object> request){
            Item item = new Item();
            item.name = request.get("name").toString();
            item.count = Double.valueOf(request.get("count").toString());
            item.price = Double.valueOf(request.get("price").toString());
            item.type = request.get("type").toString();
            item.description = request.get("description").toString();

            // Save to db
            item.persist();

            return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
        }
        @Transactional
        public Response put(@PathParam("id") Long id, Map<String, Object> request){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        item.name = request.get("name").toString();
        item.count = Double.valueOf(request.get("count").toString());
        item.price = Double.valueOf(request.get("price").toString());
        item.type = request.get("type").toString();
        item.description = request.get("description").toString();

        // Save to db
        item.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
        }
    @Transactional
    public Response delete(@PathParam("id") Long id){
        Item item = Item.findById(id);
        if(item == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "ITEM_ALREADY_DELETED"))
                    .build();
        }
        //Item.deleteById(id);
        item.delete();

        return Response.status(Response.Status.NO_CONTENT).entity(Map.of("id", item.id)).build();
    }
}

