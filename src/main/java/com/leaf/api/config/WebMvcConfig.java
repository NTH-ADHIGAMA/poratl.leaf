package com.leaf.api.config;


import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Value("${media.path}")
    private String mediaPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path mediaDirectory = Paths.get(mediaPath);
	    String path = mediaDirectory.toFile().getAbsolutePath();
	    registry.addResourceHandler("/media/**").addResourceLocations("file:"+mediaPath);
	    //registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/static/plugins/");
	}
}
