package ord.sid.springcouldstreamskafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Component
@Slf4j
public class PageViewEventSource {
    private MessageChannel pageViewsOutChannel;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public PageViewEventSource(AnalyticsBinding analyticsBinding) {
        this.pageViewsOutChannel = analyticsBinding.pageViewsOut();
    }

    public void run(ApplicationArguments args) throws Exception {
        System.out.println("..................................................;");
        List<String> names= Arrays.asList("med","Mohamed","simo","messoudi","messaoudi","simohamed");
        List<String> pages= Arrays.asList("blog","chat","profile","about","contact","vote","search");
        Runnable runnable=()->{
            String rPage=pages.get(new Random().nextInt(pages.size()));
            String rName=names.get(new Random().nextInt(names.size()));
            PageViewEvent pageViewEvent=new PageViewEvent(
                    rName,rPage,100+(int)(Math.random()*1000));
            Message<PageViewEvent> eventMessage = MessageBuilder

                    .withPayload(pageViewEvent)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, pageViewEvent.getUserId().getBytes())
                    .build();

            try {
                this.pageViewsOutChannel.send(eventMessage); log.info("Sending"+eventMessage.toString());
            } catch (Exception e) { e.printStackTrace();
            }
            log.info("Sending message =>"+pageViewEvent.toString());
        };
        System.out.println("--------------------------------------------");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                runnable,1000,1000, TimeUnit.MILLISECONDS);
    }
}
