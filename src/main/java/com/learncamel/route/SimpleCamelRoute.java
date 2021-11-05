package com.learncamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
    	
    	from("timer:hello?period=10s")
    	   	.pollEnrich("file:data/input?delete=true&readLock=none")
    		.to("direct-vm:start");
    	
    	  from("direct-vm:start")
    	  		.circuitBreaker()	
    	  	.to("http://google.com")
    	  	
    	  	.transform().constant("Fallback message")
    	  	.end()
    	  	.log("body ${body}")
    	  	.to("mock:result").end(); 

    	  
    }
}
