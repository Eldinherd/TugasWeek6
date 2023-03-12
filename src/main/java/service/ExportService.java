package service;

import com.opencsv.CSVWriter;
import id.kawahEdukasi.model.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ExportService {
    // export pdf by jasper
    public Response exportPDFItem() throws JRException {

        // Load template Jasper
        File file = new File("src/main/resources/TemplateItem.jrxml");

        //build object jasper report dari load template
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // create datasource jasper for all Item
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Item.listAll());

        //create object jasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>() , jrBeanCollectionDataSource);

//        Map<String, Object> param = new HashMap<>();
//        param.put("DATASOURCE", jrBeanCollectionDataSource);


        //export jasperPrint to byte array
        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        //return response dengan content type pdf
        return Response.ok().type("application/pdf").entity(jasperResult).build();

    }
    public Response exportExcelItem() throws IOException {

        ByteArrayOutputStream outputStream = excelItem();

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=\"peserta_list_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();

    }


    public ByteArrayOutputStream excelItem() throws IOException {

        //get all data peserta
        List<Item> itemList = Item.listAll();

        //create new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //create sheet
        XSSFSheet sheet = workbook.createSheet("data");

        //set header
        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("count");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("type");
        row.createCell(5).setCellValue("description");
        row.createCell(6).setCellValue("createdAt");
        row.createCell(7).setCellValue("updateAt");

        //set data
        for(Item item : itemList){
            row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(item.id);
            row.createCell(1).setCellValue(item.name);
            row.createCell(2).setCellValue(item.count);
            row.createCell(3).setCellValue(item.price);
            row.createCell(4).setCellValue(item.type);
            row.createCell(5).setCellValue(item.description);
            row.createCell(6).setCellValue(item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
            row.createCell(7).setCellValue(item.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

    public Response exportCsvItem() throws IOException {
        //get all data peserta
        List<Item> ItemList = Item.listAll();

        File file = File.createTempFile("temp", "");

        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        String[] headers = {"id", "name", "count", "price", "type", "description", "createAt", "updateAt"};
        writer.writeNext(headers);
        for(Item item : ItemList){
            String[] data = {
                    String.valueOf(item.id),
                    item.name,
                    String.valueOf(item.count),
                    String.valueOf(item.price),
                    item.type,
                    item.description,
                    String.valueOf(item.createdAt),
                    String.valueOf(item.updatedAt),
            };
            writer.writeNext(data);
        }

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment; filename=\"Item List.csv\"")
                .entity(file).build();

    }

}

