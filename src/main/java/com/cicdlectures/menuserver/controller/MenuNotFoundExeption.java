package com.cicdlectures.menuserver.controller;

class MenuNotFoundException extends RuntimeException {

  MenuNotFoundException(Long id) {
    super("Could not find menu " + id);
  }
}
