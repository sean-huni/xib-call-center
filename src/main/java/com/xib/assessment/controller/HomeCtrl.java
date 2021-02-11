package com.xib.assessment.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home redirection to swagger api documentation
 */
@Controller
@Log4j2
public class HomeCtrl {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        log.info("Redirected >> swagger-ui.html");
        return "redirect:swagger-ui/";
    }
}
