package com.code.miraz.config;


import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslProvider;
import java.io.IOException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.lognet.springboot.grpc.GRpcServerBuilderConfigurer;

@Configuration
@EnableConfigurationProperties(GrpcProperties.class)
public class GrpcConfiguration {

  @Bean
  GRpcServerBuilderConfigurer serverBuilderConfigurer(GrpcProperties properties) {
    return new GRpcServerBuilderConfigurer() {
      @Override
      public void configure(ServerBuilder<?> serverBuilder) {
        super.configure(serverBuilder);
        try {
          ((NettyServerBuilder)serverBuilder)
              .sslContext(sslContextConfiguration(properties));
        } catch (IOException e) {
          throw new RuntimeException("SSL cert or key files are missing", e);
        }
      }
    };
  }

  private SslContext sslContextConfiguration(GrpcProperties properties) throws IOException {
    GrpcProperties.Ssl ssl = properties.getSsl();
    Resource serverCertificate = ssl.getServerCertificate();
    Resource serverKey = ssl.getServerKey();
    return GrpcSslContexts
        .configure(
            SslContextBuilder.forServer(serverCertificate.getFile(), serverKey.getFile()),
            SslProvider.OPENSSL
        ).startTls(true)
        .build();
  }

}
