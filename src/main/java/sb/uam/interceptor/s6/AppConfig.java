package sb.uam.interceptor.s6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// Implementamos la interfaz WebMvcConfigurer para poder configurar el comportamiento de Spring MVC
public class AppConfig implements WebMvcConfigurer {

    // Inyectamos la dependencia del interceptor
    @Autowired
    @Qualifier("timeInterceptor") // Inyectamos el interceptor por su nombre, recuerda que se definio el nombre en la clase
    private HandlerInterceptor timeInterceptor;

    // Implementamos uno de los metodos de WebMvcConfigurer
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // registry.addInterceptor(timeInterceptor); // Se aplica de esta manera a todos los controladores, pero se puede modificar/
        // registry.addInterceptor(timeInterceptor).addPathPatterns("/api/app"); // Se aplica solo al controlador app
        // registry.addInterceptor(timeInterceptor).addPathPatterns("/api/app", "api/var");
        registry.addInterceptor(timeInterceptor).excludePathPatterns("/api/app"); // Se aplica a todos menos al controlador app, o el indicado
        // /app/** -> Esto indica que se aplica a todo lo qye esta dentrode app
    }
}
