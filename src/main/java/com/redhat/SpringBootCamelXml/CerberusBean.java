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

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.io.CharStreams;

/**
 * A sample transform
 */
@Component(value = "cerberusBean")
public class CerberusBean {
	
	
	private String ambiente;
	private String provedor;
	private String consumidor;
	private String usuario;
	private String senhaUsuario;
	
	
	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(String consumidor) {
		this.consumidor = consumidor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

//	@Bean
    public String getAuthorization(String nome, String ambiente, String provedor, String consumidor) {
		System.out.println("Usuário digitado no header ="  +nome);
    	String responseAsString = null;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://jeap.rio.rj.gov.br/cerberus/seam/resource/v1/permissoes");
		request.addHeader("ambiente", ambiente);
		request.addHeader("provedor", provedor);
		request.addHeader("consumidor", consumidor);
		request.addHeader("chaveAcesso", "d4fec2a2-7f41-43bd-9c0c-c00afe8f4858");
		request.addHeader("usuario", nome);
		request.addHeader("senhaUsuario", "xxxx");
		HttpResponse response = null;
		try {
			response = client.execute(request);

			InputStream inputStream = response.getEntity().getContent();
			responseAsString = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println("Executou o Cerberus bean"+responseAsString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseAsString;
    }

	@Override
	public String toString() {
		return "CerberusBean [ambiente=" + ambiente + ", provedor=" + provedor + ", consumidor=" + consumidor
				+ ", usuario=" + usuario + ", senhaUsuario=" + senhaUsuario + "]";
	}



	
}
