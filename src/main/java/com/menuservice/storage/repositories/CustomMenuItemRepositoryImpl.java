package com.menuservice.storage.repositories;

import com.menuservice.dto.UpdateMenuRequest;
import com.menuservice.storage.model.MenuItem;
import com.menuservice.storage.model.MenuItem_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.menuservice.storage.repositories.updaters.MenuAttrUpdater;

import java.util.List;

@Repository
public class CustomMenuItemRepositoryImpl implements CustomMenuItemRepository {

    private final EntityManager em;
    private final List<MenuAttrUpdater<?>> updaters;

    public CustomMenuItemRepositoryImpl(EntityManager em, List<MenuAttrUpdater<?>> updaters) {
        this.em = em;
        this.updaters = updaters;
    }

    @Transactional
    @Override
    public int updateMenu(Long id, UpdateMenuRequest dto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<MenuItem> criteriaUpdate = cb.createCriteriaUpdate(MenuItem.class);
        Root<MenuItem> root = criteriaUpdate.from(MenuItem.class);
        updaters.forEach(updater -> updater.updateAttr(criteriaUpdate, dto));
        criteriaUpdate.where(cb.equal(root.get(MenuItem_.id), id));
        return em.createQuery(criteriaUpdate).executeUpdate();
    }
}
