package com.example.calculator;

import com.example.calculator.protocol.TCalculatorService;
import com.facebook.nifty.processor.NiftyProcessorAdapters;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServiceProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import java.util.Arrays;

@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @Bean
    TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }
    
    @Bean
    ThriftCodecManager thriftCodecManager() {
        return new ThriftCodecManager();
    }



    @Bean
    public ServletRegistrationBean thriftBookServlet(ThriftCodecManager thriftCodecManager, TProtocolFactory protocolFactory, TCalculatorService exampleService) {
        ThriftServiceProcessor processor = new ThriftServiceProcessor(thriftCodecManager, Arrays.<ThriftEventHandler>asList(), exampleService);

        TServlet tServlet = new TServlet(
                NiftyProcessorAdapters.processorToTProcessor(processor),
                protocolFactory,
                protocolFactory
        );

        return new ServletRegistrationBean(tServlet, "/thrift/");
    }
}