package com.menuservice.storage.repositories.updaters;

import com.menuservice.dto.UpdateMenuRequest;
import com.menuservice.storage.model.MenuItem;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class MenuAttrUpdater<V> {
    SingularAttribute<MenuItem, V> attr;
    Function<UpdateMenuRequest, V> dtoValueExtractor;

    public void updateAttr(CriteriaUpdate<MenuItem> criteria, UpdateMenuRequest dto) {
        V dtoValue = dtoValueExtractor.apply(dto);
        if (dtoValue != null) {
            criteria.set(attr, dtoValue);
        }
    }
}
