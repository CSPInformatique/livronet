package com.cspinformatique.livronet.config;

import javax.annotation.Resource;

import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.cspinformatique.livronet.elasticsearch.TransportClientUtil;

@Configuration
@EnableElasticsearchRepositories(basePackages="com.cspinformatique.livronet.**.repository.elasticsearch")
@PropertySource("classpath:config/elasticsearch.properties")
public class ElasticsearchConfig {
	@Resource
	private Environment env;
	
	@Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
		if(env.getRequiredProperty("dilicom.sync.elasticsearch.localNode", Boolean.class)){
			return new ElasticsearchTemplate(NodeBuilder.nodeBuilder().local(true).node().client());
		}else{
			return new ElasticsearchTemplate(TransportClientUtil.buildTransportClient(env));			
		}
    }
}
