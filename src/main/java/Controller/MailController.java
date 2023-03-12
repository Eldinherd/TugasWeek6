package Controller;

import service.MailService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/mail")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MailController {
    @Inject
    MailService mailService;

    @POST
    public Response sendEmail(Map<String, Object> request) throws IOException {
        mailService.sendEmail(request.get("email").toString());
        return Response.ok(new HashMap<>()).build();
    }
    @GET
    @Path("/excel")
    public Response sendExcelToEmail(Map<String, Object> request) throws IOException {
        mailService.sendExcelToEmail(request.get("email").toString());
        return Response.ok(new HashMap<>()).build();
    }
}
