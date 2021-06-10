package com.epsagon.springbootk8s.web;
import io.jaegertracing.Configuration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Span;

@SpringBootApplication
@RestController
public class HelloKubernetes extends SpringBootServletInitializer {
    // @Autowired
    // private io.opentracing.Tracer tracer;
    // Span span = tracer.buildSpan("hello").start();
    @Bean
      public io.opentracing.Tracer initTracer() {
        SamplerConfiguration samplerConfig = new SamplerConfiguration().withType("const").withParam(1);
        SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv().withAgentHost("localhost");
        ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(false).withSender(senderConfig);
        return Configuration.fromEnv("propzy-services").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
      }
    // @Bean
    //   public ServletWebServerFactory servletWebServerFactory() {
    //       return new TomcatServletWebServerFactory();
    //   }
    @RequestMapping("/")
    String home() {
        return "Hello World from Kubernetes!";
    }
    public static void main(String[] args) {
      SpringApplication.run(HelloKubernetes.class, args);
    }
}