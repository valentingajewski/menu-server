package com.cicdlectures.menuserver.service;

import com.cicdlectures.menuserver.repository.MenuRepository;
// import com.cicdlectures.menuserver.repository.DishRepository;
import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.dto.DishDto;
import com.cicdlectures.menuserver.model.Menu;
import com.cicdlectures.menuserver.model.Dish;
import com.cicdlectures.menuserver.service.ListMenuService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListMenuServiceTests {

  private ListMenuService subject;
  private MenuRepository repository;

  @BeforeEach
  public void init() {
    this.repository = mock(MenuRepository.class);
    this.subject = new ListMenuService(this.repository);
  }

  @Test
  @DisplayName("lists all known menus")
  public void listsKnownMenus() {
    // Défini une liste de menus avec un menus.
    Iterable<Menu> existingMenus = Arrays.asList(
      new Menu(
        Long.valueOf(1),
        "Christmas menu",
        new HashSet<>(
          Arrays.asList(
            new Dish(Long.valueOf(1), "Turkey", null),
            new Dish(Long.valueOf(2), "Pecan Pie", null)
          )
        )
      )
    );

    // On configure le menuRepository pour qu'il retourne notre liste de menus.
    when(repository.findAll()).thenReturn(existingMenus);

    // On appelle notre sujet
    List<MenuDto> gotMenus = subject.listMenus();

    // On défini wantMenus, les résultats attendus
    Iterable<MenuDto> wantMenus = Arrays.asList(
        new MenuDto(
          Long.valueOf(1),
          "Christmas menu",
          new HashSet<>(
            Arrays.asList(
              new DishDto(Long.valueOf(1), "Turkey"),
              new DishDto(Long.valueOf(2), "Pecan Pie")
            )
          )
        )
      );

      // On compare la valeur obtenue avec la valeur attendue.
      assertEquals(wantMenus, gotMenus);
  }
}