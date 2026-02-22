package com.matienzoShop.celulares.events.model;

public enum EventType {
    VIEW_PRODUCT(true),
    ADD_TO_FAVORITES(true),
    REMOVE_FROM_FAVORITES(true),
    ADD_TO_CART (true),
    REMOVE_FROM_CART(true),

    SEARCH_QUERY(false),
    LOGIN(false),
    REGISTER(false),
    LOGIN_FAILED(false),
    LOGOUT(false),
    PURCHASE(false);

    private final boolean requiresProduct;

    EventType (boolean requiresProduct){
        this.requiresProduct = requiresProduct;
    }

    public boolean requiresProduct(){
        return requiresProduct;
    }
}
