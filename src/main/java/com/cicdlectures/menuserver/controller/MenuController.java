package com.cicdlectures.menuserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import com.cicdlectures.menuserver.controller.MenuNotFoundException;
import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.service.CreateMenuService;
import com.cicdlectures.menuserver.service.ListMenuService;
import com.cicdlectures.menuserver.repository.MenuRepository;

@RestController
public class MenuController {

  private final CreateMenuService createMenuService;

  private final ListMenuService listMenuService;

  private final MenuRepository menuRepository;

  @Autowired
  MenuController(CreateMenuService createMenuService, ListMenuService listMenuService, MenuRepository menuRepository) {
    this.createMenuService = createMenuService;
    this.listMenuService = listMenuService;
    this.menuRepository = menuRepository;
  }

  @GetMapping(path = "/menus", produces = "application/json")
  public List<MenuDto> listMenus() {
    return listMenuService.listMenus();
  }

  @PostMapping(path = "/menus", produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public MenuDto createMenu(@RequestBody MenuDto menu) {
    return createMenuService.createMenu(menu);
  }

  @DeleteMapping("/menus/{id}")
  public void deletemenus(@PathVariable Long id) {
    if (menuRepository.existsById(id)){
      menuRepository.deleteById(id);
    } else {
      throw new MenuNotFoundException(id);
    }
  }
}
