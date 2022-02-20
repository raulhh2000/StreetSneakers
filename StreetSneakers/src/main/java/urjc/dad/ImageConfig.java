package urjc.dad;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/images/**").addResourceLocations("file:D:/Proyectos/Proyectos SpringToolSuite4/DESARROLLO DE APLICACIONES DISTRIBUIDAS/StreetSneakers/StreetSneakers/src/main/resources/images/");
		//registry.addResourceHandler("/images/**").addResourceLocations("file:C:/Users/Alberto/Documents/StreetSneakers/StreetSneakers/src/main/resources/images/");
        
    }
}
