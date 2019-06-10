package hello.infrastructure.adapters.http.api;

import hello.domain.Thing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Queries {

    @RequestMapping("/things")
    public Thing giveAThing() {
        return new Thing(1, "hola");
    }
}
