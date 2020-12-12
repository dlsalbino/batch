package br.com.azusah.batch;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED){
            LOGGER.info("!!! JOB FINISHED! Time to verify the results.");

            String query = "SELECT brand, origin, characteristics FROM coffee";
            jdbcTemplate.query(query, (rs, row) -> Coffee.builder()
                    .brand(rs.getNString(1))
                    .origin(rs.getString(2))
                    .characteristics(rs.getString(3))
                    .build())
            .forEach(coffee -> LOGGER.info("Found < {} > in the database.", coffee));
        }
    }

}
