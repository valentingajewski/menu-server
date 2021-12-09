package com.cicdlectures.menuserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cicdlectures.menuserver.controller.MenuNotFoundException;

@ControllerAdvice
class MenuNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(MenuNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String MenuNotFoundHandler(MenuNotFoundException ex) {
    return ex.getMessage();
  }
}