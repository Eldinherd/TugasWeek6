package service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MailService {
    @Inject
    Mailer mailer;

    @Inject
    ExportService exportService;

    public void sendEmail(String email){
        mailer.send(
                Mail.withHtml(email,
                        "CRUD API Quarkus Batch 6",
                        "<h1>Hello,</h1> this is Quarkus Peserta-Service"));
    }

    public void sendExcelToEmail(String email) throws IOException {
        mailer.send(
                Mail.withHtml(email,
                                "Excel Peserta Batch 6",
                                "<h1>Hello,</h1> this is your excel file")
                        .addAttachment("list-peserta.xlsx",
                                exportService.excelItem().toByteArray(),
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}
