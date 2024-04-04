package id.co.ist.mobile.servicename.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Slf4j
@DependsOn(value = {"httpClientESBNonTransactional", "httpClientESBTransactional"})
@Configuration
public class SoapConfig {

    @Value("${esb.base.url:http://jump-box-dev.app.mylab.local/WSProxyWeb/ProxyServlet/}")
    String esbBaseUrl;


    private HttpComponentsMessageSender constructMessageSender(CloseableHttpClient httpClient) {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setHttpClient(httpClient);
        return messageSender;
    }

    @Bean(name = "esbMarshallerProcessor")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("id.co.ist.mobile");
        return marshaller;
    }

    @Bean("soapConnectorTransactional")
    public SOAPConnector soapConnectorTransactional(@Qualifier("esbMarshallerProcessor") Jaxb2Marshaller marshaller,
                                                    @Qualifier("httpClientESBTransactional") CloseableHttpClient httpClientTransactional) {
        SOAPConnector client = new SOAPConnector();
        client.setDefaultUri(esbBaseUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(constructMessageSender(httpClientTransactional));
        return client;
    }

    @Bean
    public SOAPConnector soapConnector(@Qualifier("esbMarshallerProcessor") Jaxb2Marshaller marshaller,
                                       @Qualifier("httpClientESBNonTransactional") CloseableHttpClient httpClientNonTransactional) {
        SOAPConnector client = new SOAPConnector();
        client.setDefaultUri(esbBaseUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(constructMessageSender(httpClientNonTransactional));
        return client;
    }

}
