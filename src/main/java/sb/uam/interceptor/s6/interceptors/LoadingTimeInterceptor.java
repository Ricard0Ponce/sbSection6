package sb.uam.interceptor.s6.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger; // Importamos la clase Logger de SLF4J
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Inicialmente debemos importar la clase HandlerInterceptor de Spring
// Al componente se le da un nombre debido a que varias clases implementan HandlerException
@Component("timeInterceptor") // Esta clase se anota como un componente de Spring pora que pueda ser inyectada
public class LoadingTimeInterceptor implements HandlerInterceptor {
    // Hay 3 métodos de la clase HandlerInterceptor que podemos implementar
    // Implementando 2 de los 3 a continuación:

    // Creamos el objeto logger de la clase Logger de SLF4J para poder registrar mensajes
    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    // Ejemplo cuando es true:
    /*
        @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //  return HandlerInterceptor.super.preHandle(request, response, handler); // Por defecto
        HandlerMethod controller = ((HandlerMethod)handler); // Hacemos el cast del objeto handler a HandlerMethod
        // Con getMethod y getName obtenemos el método que se está ejecutando y su nombre
        logger.info("LoadingTimeInterceptor: preHandle() entrando... " + controller.getMethod().getName()); // Agregamos un mensaje de log de entrada
        long start = System.currentTimeMillis(); // Declaramos la variable para el tiempo de inicio en milisegundos
        // Luego almacenamos la informacion en el request, pues es un objeto al que ambos métodos tienen acceso
        // Se le asigna a request un valor con setAttribute, el cual es un par clave-valor
        request.setAttribute("start", start);
        Random random = new Random(); // Creamos un objeto de la clase Random
        int delay = random.nextInt(500); // Numero entero de 0 a 500
        Thread.sleep(delay); // Hacemos que el hilo actual duerma por el tiempo especificado para ver el delay

        return true;
    }
    * */

    // Ejemplo cuando es false:
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //  return HandlerInterceptor.super.preHandle(request, response, handler); // Por defecto
        HandlerMethod controller = ((HandlerMethod)handler); // Hacemos el cast del objeto handler a HandlerMethod
        // Con getMethod y getName obtenemos el método que se está ejecutando y su nombre
        logger.info("LoadingTimeInterceptor: preHandle() entrando... " + controller.getMethod().getName()); // Agregamos un mensaje de log de entrada
        long start = System.currentTimeMillis(); // Declaramos la variable para el tiempo de inicio en milisegundos
        // Luego almacenamos la informacion en el request, pues es un objeto al que ambos métodos tienen acceso
        // Se le asigna a request un valor con setAttribute, el cual es un par clave-valor
        request.setAttribute("start", start);
        Random random = new Random(); // Creamos un objeto de la clase Random
        int delay = random.nextInt(500); // Numero entero de 0 a 500
        Thread.sleep(delay); // Hacemos que el hilo actual duerma por el tiempo especificado para ver el delay

        Map<String, String>  json =  new HashMap<>(); // Creamos un objeto de tipo Map
        json.put("message", "Acceso denegado"); // Agregamos un mensaje al objeto json
        json.put("date", new Date().toString()); // Agregamos la fecha actual al objeto json (new

        ObjectMapper mapper = new ObjectMapper(); // Creamos un objeto de la clase ObjectMapper
        String jsonString = mapper.writeValueAsString(json); // Convertimos el objeto json a un string
        response.setContentType("application/json"); // Establecemos el tipo de contenido de la respuesta
        response.setStatus(401); // Establecemos el código de estado de la respuesta
        response.getWriter().write(jsonString); // Escribimos el string en la respuesta
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        // Ahora en el post vamos a poner el tiempo de finalización
        long end = System.currentTimeMillis(); // Declaramos la variable para el tiempo de finalización en milisegundos
        long start = (long) request.getAttribute("start"); // Obtenemos el tiempo de inicio del request
        long loadingTime = end - start; // Calculamos el tiempo de carga
        logger.info("Tiempo transcurrido: "+ loadingTime + " milisegundos");

        logger.info("LoadingTimeInterceptor: postHandle() saliendo... " + ((HandlerMethod)handler).getMethod().getName()); // Agregamos un mensaje de log de salida
    }

}
