package com.menuservice.storage.repositories;

import com.menuservice.dto.UpdateMenuRequest;

public interface CustomMenuItemRepository {

    int updateMenu(Long id, UpdateMenuRequest dto);

}
