package com.practice.webeditor.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HtmlController {
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String welcomeAsHTML() {
        return """
                <html>
                <header><title>Welcome</title></header>
                <body>
                Welcome to the WebEditor
                </body>
                </html>""";
    }
}
