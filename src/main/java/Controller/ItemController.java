package Controller;

import com.opencsv.exceptions.CsvValidationException;
import dto.FileFormDTO;
import net.sf.jasperreports.engine.JRException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import service.ExportService;
import service.ImportService;
import service.ItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {
    @Inject
    ItemService itemService;

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    // Detail Item by id
    @GET
    public Response get(){
        return itemService.get();
    }
    // Detail ExportItem
    @GET
    @Path("/export")
    @Produces("application/pdf")
    // export pdf by jasper
    public Response exportPdf() throws JRException {
        return exportService.exportPDFItem();
    }

    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws IOException {
        return exportService.exportExcelItem();
    }
    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCSV() throws IOException {
        return exportService.exportCsvItem();
    }


    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm FileFormDTO request) {
        try{
            return importService.importExcel(request);
        } catch (IOException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm FileFormDTO request) {
        try{
            return importService.importCSV(request);
        } catch (IOException | CsvValidationException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        return itemService.get(id);
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
