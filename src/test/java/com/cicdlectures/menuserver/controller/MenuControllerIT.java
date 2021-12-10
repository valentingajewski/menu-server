package com.cicdlectures.menuserver.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.dto.DishDto;
import com.cicdlectures.menuserver.model.Menu;
import com.cicdlectures.menuserver.model.Dish;
import com.cicdlectures.menuserver.repository.MenuRepository;
import com.cicdlectures.menuserver.repository.DishRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Lance l'application sur un port aléatoire.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Indique de relancer l'application à chaque test.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MenuControllerIT {

  @LocalServerPort
  private int port;

  @Autowired
  private MenuRepository menuRepository;

  // Injecte automatiquement l'instance du TestRestTemplate
  @Autowired
  private TestRestTemplate template;

  private URL getMenusURL() throws Exception {
    return new URL("http://localhost:" + port + "/menus");
  }

  @Test
  @DisplayName("lists all known menus")
  public void listsAllMenus() throws Exception {
    Menu monMenu = new Menu(
        null,
        "Christmas menu",
        new HashSet<>(
          Arrays.asList(
            new Dish(null, "Turkey", null),
            new Dish(null, "Pecan Pie", null)
          )
        )
      );
    MenuDto ceMenuLa = new MenuDto(
          Long.valueOf(1),
          "Christmas menu",
          new HashSet<>(
            Arrays.asList(
              new DishDto(Long.valueOf(1), "Turkey"),
              new DishDto(Long.valueOf(2), "Pecan Pie")
            )
          )
        );
    menuRepository.save(monMenu);

    ResponseEntity<MenuDto[]> response = this.template.getForEntity(getMenusURL().toString(), MenuDto[].class);

    //Parse le payload de la réponse sous forme d'array de MenuDto
    MenuDto[] gotMenus = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, gotMenus.length);
    assertEquals(ceMenuLa,gotMenus[0]);


  }


}
