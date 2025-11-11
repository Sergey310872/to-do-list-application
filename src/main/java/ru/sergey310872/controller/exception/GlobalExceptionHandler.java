package ru.sergey310872.controller.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler implements ErrorController {
//    private static final Logger log = LoggerFactory.getLogger(ControllerErrorHandler.class);

    @RequestMapping("/error")
    public String errorPageSpring() {
        return "public/error/error-page";
    }


    @RequestMapping("/error-page")
    public String errorPage() {
        return "public/error/error-page";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex) {
//        System.out.println(ex.getMessage());
//        log.error(ex.getMessage(), ex);
        return "redirect:/error-page";
    }
}
