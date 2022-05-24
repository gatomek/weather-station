package pl.gatomek.airport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger( MyRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info( "MyRunner");
    }
}
