package com.menuservice.service.impl;

import com.menuservice.dto.*;
import com.menuservice.exception.MenuServiceException;
import com.menuservice.mapper.MenuItemMapper;
import com.menuservice.service.MenuService;
import com.menuservice.storage.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.menuservice.storage.model.MenuItemProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemMapper mapper;
    private final MenuItemRepository repository;

    @Override
    public MenuItemDto createMenuItem(CreateMenuRequest dto) {
        var menu = mapper.toDomain(dto);
        try {
            return mapper.toDto(repository.save(menu));
        } catch (DataIntegrityViolationException e) {
            var msg = String.format("Failed to create", dto, dto.getName());
            throw new MenuServiceException(msg, HttpStatus.CONFLICT);
        }
    }

    @Override
    public void deleteMenuItem(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public MenuItemDto updateMenuItem(Long id, UpdateMenuRequest update) {
        try {
            int updateCount = repository.updateMenu(id, update);
            if (updateCount == 0) {
                var msg = String.format("MenuItem with id=%d not found.", id);
                throw new MenuServiceException(msg, HttpStatus.NOT_FOUND);
            }
            return getMenu(id);
        } catch (DataIntegrityViolationException ex) {
            var msg = String.format("Failed to update",
                    id, update.getName());
            throw new MenuServiceException(msg, HttpStatus.CONFLICT);
        }
    }

    @Override
    public MenuItemDto getMenu(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> {
                    var msg = String.format("MenuItem id=%d not found.", id);
                    return new MenuServiceException(msg, HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public OrderMenuResponse getMenusForOrder(OrderMenuRequest request) {
        Map<String, MenuItemProjection> nameToProjection = repository.getMenuInfoForNames(request.getMenuNames()).stream()
                .collect(Collectors.toMap(MenuItemProjection::getName, Function.identity()));
        List<MenuInfo> menuInfos = new ArrayList<>();
        for (String name : request.getMenuNames()) {
            if (nameToProjection.containsKey(name)) {
                var projection = nameToProjection.get(name);
                menuInfos.add(new MenuInfo(projection.getName(), projection.getPrice(), true));
            } else {
                menuInfos.add(new MenuInfo(name, null, false));
            }
        }
        return OrderMenuResponse.builder().menuInfos(menuInfos).build();
    }
}