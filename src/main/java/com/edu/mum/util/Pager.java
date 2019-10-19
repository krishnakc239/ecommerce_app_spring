package com.edu.mum.util;

import org.springframework.data.domain.Page;


public class Pager<T> {

    private final Page<T> pagerObjects;

    public Pager(Page<T> pagerObjects) {
        this.pagerObjects = pagerObjects;
    }

    public int getPageIndex() {
        return pagerObjects.getNumber() + 1;
    }

    public int getPageSize() {
        return pagerObjects.getSize();
    }

    public boolean hasNext() {
        return pagerObjects.hasNext();
    }

    public boolean hasPrevious() {
        return pagerObjects.hasPrevious();
    }

    public int getTotalPages() {
        return pagerObjects.getTotalPages();
    }

    public long getTotalElements() {
        return pagerObjects.getTotalElements();
    }

    public Page<T> getPagerObjects() {
        return pagerObjects;
    }

    public boolean indexOutOfBounds() {
        return getPageIndex() < 0 || getPageIndex() > getTotalElements();
    }

}
