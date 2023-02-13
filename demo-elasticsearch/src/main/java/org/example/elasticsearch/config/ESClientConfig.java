package org.example.elasticsearch.config;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientOptions;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Setter;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;

@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
public class ESClientConfig {


    @Setter
    private String hosts;

    @Setter
    private String username;

    @Setter
    private String password;

    /**
     * 解析配置的字符串，转为HttpHost对象数组
     *
     * @return
     */
    private HttpHost[] toHttpHost() {
        if (!StringUtils.hasLength(hosts)) {
            throw new RuntimeException("invalid elasticsearch configuration");
        }
        String[] hostArray = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArray.length];
        HttpHost httpHost;
        for (int i = 0; i < hostArray.length; i++) {
            String[] strings = hostArray[i].split(":");
            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), "http");
            httpHosts[i] = httpHost;
        }
        return httpHosts;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        return new ElasticsearchClient(getRestClientTransport());
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient() {
        return new ElasticsearchAsyncClient(getRestClientTransport());
    }

    private RestClientTransport getRestClientTransport() {
        HttpHost[] httpHosts = toHttpHost();
        RestClientBuilder builder = RestClient.builder(httpHosts).setHttpClientConfigCallback(
                new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        if (StrUtil.isNotBlank(username)) {
                            // 添加密码验证
                            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                            credentialsProvider.setCredentials(AuthScope.ANY,
                                    new UsernamePasswordCredentials(username, password));
                            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                        httpClientBuilder
//                                .setDefaultHeaders(
//                                        // 添加请求头
//                                        ListUtil.of(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()),
//                                                new BasicHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=8"))
//                                )
                                // 前置拦截器
                                .addInterceptorFirst(new HttpResponseInterceptor() {
                                    @Override
                                    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
//                                        System.out.println("httpResponse = " + httpResponse + ", httpContext = " + httpContext);
                                    }
                                })
                                // 后置拦截器添加相应请求头
                                .addInterceptorLast((HttpResponseInterceptor)
                                        (response, context) ->
                                                response.addHeader("X-Elastic-Product", "Elasticsearch"));
                        return httpClientBuilder;
                    }
                }
        );
        RestClient restClient = builder.build();
        return new RestClientTransport(restClient, new JacksonJsonpMapper())
                // 这种方式也可以添加请求头，推荐上面这种
                .withRequestOptions(new RestClientOptions.Builder(
                        RequestOptions.DEFAULT.toBuilder()
//                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=8"))
                        .build());
    }

}
