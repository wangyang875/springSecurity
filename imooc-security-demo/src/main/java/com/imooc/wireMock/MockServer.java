package com.imooc.wireMock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class MockServer {
    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8081);//指定服务器端口
        WireMock.removeAllMappings();//把以前的配置清楚掉
        mock("/order/1","01");
    }
        //加入映射配置
        public static void mock(String url, String file) throws IOException {
            ClassPathResource resource = new ClassPathResource("mock/response/"+file+".txt");
            String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(), "\n");
            WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                    .willReturn(aResponse().withBody(content).withStatus(200)));
        }

}
