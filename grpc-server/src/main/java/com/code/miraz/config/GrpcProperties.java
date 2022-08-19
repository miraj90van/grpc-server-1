package com.code.miraz.config;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class GrpcProperties {

  private Ssl ssl;

  @Data
  static class Ssl {
    private Resource serverCertificate;
    private Resource serverKey;
  }

}
