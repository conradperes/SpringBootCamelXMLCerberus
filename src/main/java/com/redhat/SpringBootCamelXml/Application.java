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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
// load regular Spring XML file from the classpath that contains the Camel XML
// DSL
@ImportResource({ "classpath:spring/camel-context.xml" })
public class Application extends SpringBootServletInitializer {
	@Bean
	ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				"/cerberus/*");
		servlet.setName("CamelServlet");
		return servlet;
	}

	@Component
	class RestApi extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			restConfiguration().contextPath("/cerberus").apiContextPath("/api-doc")
					.apiProperty("api.title", "Camel REST API").apiProperty("api.version", "1.0")
					.apiProperty("cors", "true").apiContextRouteId("doc-api").component("servlet")
					.bindingMode(RestBindingMode.json);

			rest("/bye").get().to("direct:bye");
			from("direct:bye").bean(CerberusBean.class);

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
		// request.addHeader("chaveAcesso",
		// "d4fec2a2-7f41-43bd-9c0c-c00afe8f4858");
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
	}

	/**
	 * A main method to start this application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}