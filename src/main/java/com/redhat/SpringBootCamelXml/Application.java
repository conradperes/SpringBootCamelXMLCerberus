/*
 * Copyright 2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package com.redhat.SpringBootCamelXml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.google.common.io.CharStreams;

@SpringBootApplication
// load regular Spring XML file from the classpath that contains the Camel XML DSL
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends FatJarRouter {
 
    @Override
    public void configure() throws Exception {
//    	from("netty-http:187.111.97.10:80/cerberus/seam/resource/v1/permissoes")
//    	.setBody().simple("ref:helloWorld")
//		.setHeader("ambiente", simple("DESENVOLVIMENTO", String.class))
//		.setHeader("provedor", simple("EXEMPLO", String.class))
//		.setHeader("consumidor", simple("EXEMPLO", String.class))
//		.setHeader("chaveAcesso", simple("d4fec2a2-7f41-43bd-9c0c-c00afe8f4858", String.class))
//		.setHeader("usuario", simple("05520685789", String.class))
//		.setHeader("senhaUsuario", simple("", String.class));
    	rest("/cerberus").get().to("direct:hello");
		from("direct:hello").transform().simple("Hello Mundo!");
    	rest("/bye").get().to("direct:bye");
		from("direct:bye").transform().simple("Adios Mundo!");
    	//rest("/cerberus").get().to("ref:cerberusBean");
		//from("direct:bye").transform().simple("Adios Mundo!");
    }
 
	// @Bean
	// String helloWorld() {
	// String responseAsString = null;
	// HttpClient client = HttpClientBuilder.create().build();
	// HttpGet request = new
	// HttpGet("http://jeap.rio.rj.gov.br/cerberus/seam/resource/v1/permissoes");
	// request.addHeader("ambiente", "DESENVOLVIMENTO");
	// request.addHeader("provedor", "EXEMPLO");
	// request.addHeader("consumidor", "EXEMPLO");
	// request.addHeader("chaveAcesso", "d4fec2a2-7f41-43bd-9c0c-c00afe8f4858");
	// request.addHeader("usuario", "05520685789");
	// request.addHeader("senhaUsuario", "xxxx");
	// HttpResponse response = null;
	// try {
	// response = client.execute(request);
	//
	// InputStream inputStream = response.getEntity().getContent();
	// responseAsString = CharStreams.toString(new
	// InputStreamReader(inputStream, "UTF-8"));
	// System.out.println(response.getStatusLine().getStatusCode());
	// System.out.println(responseAsString);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return responseAsString;
	// }

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}