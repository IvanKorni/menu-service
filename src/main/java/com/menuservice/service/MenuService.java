package com.menuservice.service;

import com.menuservice.dto.*;

public interface MenuService {

    MenuItemDto createMenuItem(CreateMenuRequest dto);

    void deleteMenuItem(Long id);

    MenuItemDto updateMenuItem(Long id, UpdateMenuRequest update);

    MenuItemDto getMenu(Long id);

    OrderMenuResponse getMenusForOrder(OrderMenuRequest request);
}
