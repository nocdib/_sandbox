package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController // HTTP requests are handled by a controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(name="/greeting") // GET requests to /greeting are mapped to the greeting() method.
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        // @RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method.
        // If the name parameter is absent in the request, the defaultValue of "World" is used.
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}