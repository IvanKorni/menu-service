package com.menuservice.controller;

import com.menuservice.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.menuservice.service.MenuService;

@Slf4j
@RestController
@RequestMapping("/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuService menuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemDto createMenuItem(@RequestBody @Valid CreateMenuRequest dto) {
        return menuService.createMenuItem(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable("id") Long id) {
        menuService.deleteMenuItem(id);
    }


    @PatchMapping("/{id}")
    public MenuItemDto updateMenuItem(@PathVariable("id") Long id,
                                      @RequestBody @Valid UpdateMenuRequest update) {
        return menuService.updateMenuItem(id, update);
    }

    @GetMapping("/{id}")
    public MenuItemDto getMenu(@PathVariable("id") Long id) {
        return menuService.getMenu(id);
    }


    @PostMapping("/menu-info")
    public OrderMenuResponse getMenusForOrder(@RequestBody @Valid OrderMenuRequest request) {
        return menuService.getMenusForOrder(request);
    }
}
