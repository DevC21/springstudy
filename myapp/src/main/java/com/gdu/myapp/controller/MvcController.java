package com.gdu.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
public class MvcController {

  @GetMapping(value={"/", "/main.page"})
  public String welcome() {
    return "index";
  }

}