package id.co.danamon.dbank.servicename.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SOAPConnector extends WebServiceGatewaySupport {

    @Value("${esb.base.url:http://jump-box-dev.app.mylab.local/WSProxyWeb/ProxyServlet/}")
    String esbBaseUrl;

    public Object callWebService(String path, Object request) {
        String url = esbBaseUrl + path;
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}
