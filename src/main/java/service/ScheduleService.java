package service;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;

@ApplicationScoped
public class ScheduleService {
    @Inject
            MailService mailService;
    Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Scheduled(every = "10s")
    public void generateKawahEdukasi(){
        logger.info("kawahedukasi {}", LocalDate.now());
    }
    @Scheduled(every = "10s")
    public void scheduleSendEmailListItem() throws IOException {
       mailService.sendExcelToEmail("eldinherdiana86@gmail.com");
       logger.info("SEND EMAIL SUCCESS");
    }
}
