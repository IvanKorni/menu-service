package com.menuservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.menuservice.storage.model.MenuItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.http.HttpStatus;
import com.menuservice.exception.MenuServiceException;
import com.menuservice.storage.model.MenuItem_;

public enum SortBy {
    AZ {
        @Override
        public Order getOrder(CriteriaBuilder cb, Root<MenuItem> root) {
            return cb.asc(root.get(MenuItem_.name));
        }
    },
    ZA {
        @Override
        public Order getOrder(CriteriaBuilder cb, Root<MenuItem> root) {
            return cb.desc(root.get(MenuItem_.name));
        }
    },
    PRICE_ASC {
        @Override
        public Order getOrder(CriteriaBuilder cb, Root<MenuItem> root) {
            return cb.asc(root.get(MenuItem_.price));
        }
    },
    PRICE_DESC {
        @Override
        public Order getOrder(CriteriaBuilder cb, Root<MenuItem> root) {
            return cb.desc(root.get(MenuItem_.price));
        }
    },
    DATE_ASC {
        @Override
        public Order getOrder(CriteriaBuilder cb, Root<MenuItem> root) {
            return cb.asc(root.get(MenuItem_.createdAt));
        }
    },
    DATE_DESC {
        @Override
        public Order getOrder(CriteriaBuilder cb, Root<MenuItem> root) {
            return cb.desc(root.get(MenuItem_.createdAt));
        }
    };

    public abstract Order getOrder(CriteriaBuilder cb, Root<MenuItem> root);

    @JsonCreator
    public static SortBy fromString(String str) {
        try {
            return SortBy.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            var msg = "Failed to create SortBy %s".formatted(str);
            throw new MenuServiceException(msg, HttpStatus.BAD_REQUEST);
        }
    }
}
