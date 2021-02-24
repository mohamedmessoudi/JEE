package ord.sid.springcouldstreamskafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
@EnableBinding(AnalyticsBinding.class)
@Slf4j

@SpringBootApplication
public class SpringcouldStreamsKafkaApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringcouldStreamsKafkaApplication.class, args);
    }

}
