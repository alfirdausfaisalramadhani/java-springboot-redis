package id.co.danamon.dbank.servicename.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableScheduling
public class ConnectionPoolConfiguration {

    @Value("${esb.transactional.timeout:55}")
    Integer esbTransactionalTimeout;

    @Value("${esb.nontransactional.timeout:25}")
    Integer esbNonTransactionalTimeout;

    // Connection pool
    @Value(value = "${pool.max.connection.per.route:40}")
    private int maxRouteConnections;
    @Value(value = "${pool.max.total.connection:30}")
    private int maxTotalConnections;

    // Keep alive
    @Value(value = "${pool.time.keep.alive:20}")
    private int defaultTimeKeepAlive; // 20 milis * 1000

    // Idle connection monitor
    @Value(value = "${pool.idle.time.wait:30}")
    private int idleConnectionTimeWait; // 30 milis * 1000

    //timed out
    @Value(value = "${rest.client.connect.timeout:60}")
    private int restClientConnectTimeout;

    @Value(value = "${rest.client.read.timeout:60}")
    private int restClientReadTimeout;


    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        // set total amount of connections across all HTTP routes
        poolingConnectionManager.setMaxTotal(maxTotalConnections);
        // set maximum amount of connections for each http route in pool
        poolingConnectionManager.setDefaultMaxPerRoute(maxRouteConnections);

        return poolingConnectionManager;
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
            HeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);

            while (elementIterator.hasNext()) {
                HeaderElement element = elementIterator.nextElement();
                String param = element.getName();
                String value = element.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000; // convert to ms
                }
            }

            return (defaultTimeKeepAlive * 1000);
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 20000) // check every 20s
            public void run() {
                // only if connection pool is initialised
                if (pool != null) {
                    log.debug("-------------------------------------");
                    log.debug("pool routes size: {}", pool.getRoutes().size());
                    log.debug("total active connection: {}", pool.getTotalStats().getLeased());
                    log.debug("total available connection: {}", pool.getTotalStats().getAvailable());
                    log.debug("max num of connection: {}", pool.getTotalStats().getMax());
                    pool.closeExpiredConnections();
                    pool.closeIdleConnections((idleConnectionTimeWait * 1000), TimeUnit.MILLISECONDS);
                }
            }
        };
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("idleMonitor");
        scheduler.setPoolSize(5);
        return scheduler;
    }

    @Bean
    public CloseableHttpClient httpClient() {

        return HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        /**
                         * https://www.baeldung.com/httpclient-timeout
                         * the Connection Timeout (http.connection.timeout) – the time to establish the connection with the remote host
                         * the Socket Timeout (http.socket.timeout) – the time waiting for data – after establishing the connection; maximum time of inactivity between two data packets
                         * the Connection Manager Timeout (http.connection-manager.timeout) – the time to wait for a connection from the connection manager/poo
                         */
                        .setConnectTimeout((restClientConnectTimeout * 1000))
                        .setConnectionRequestTimeout((restClientConnectTimeout * 1000))
                        .setSocketTimeout((restClientReadTimeout * 1000))
                        .build())
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

    @Bean("httpClientESBNonTransactional")
    public CloseableHttpClient httpClientEsbNonTransactional() {
        return HttpClients.custom()
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout((restClientConnectTimeout * 1000))
                        .setConnectionRequestTimeout((restClientConnectTimeout * 1000))
                        .setSocketTimeout((esbNonTransactionalTimeout * 1000))
                        .build())
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

    @Bean("httpClientESBTransactional")
    public CloseableHttpClient httpClientEsbTransactional() {
        return HttpClients.custom()
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout((restClientConnectTimeout * 1000))
                        .setConnectionRequestTimeout((restClientConnectTimeout * 1000))
                        .setSocketTimeout((esbTransactionalTimeout * 1000))
                        .build())
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

}