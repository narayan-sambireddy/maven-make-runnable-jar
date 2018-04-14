package maven.narayan.makejar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author narayan-sambireddy
 */
@RestController
public class AppController {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}
