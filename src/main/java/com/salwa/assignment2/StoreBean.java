package com.salwa.assignment2;

import com.salwa.assignment2.entity.Store;
import com.salwa.assignment2.services.StoreService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Named
@ViewScoped
public class StoreBean implements Serializable {
    //list of store
    private Store store = new Store();
    private List<Store> stores = new ArrayList<>();

    //inject service class
    @EJB
    private StoreService storeService;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Store> getStores() throws NamingException {

        stores = storeService.getStoreList();

        return stores;
    }


    //add store
    public void addStore(ActionEvent event) throws NamingException {
        storeService.addToStore(store);
        store = new Store();
    }

    //delete store
    public void deleteStore(AjaxBehaviorEvent event) throws NamingException {
        Long itemToDelete = (Long) event.getComponent().getAttributes().get("itemToDeleteStore");
        System.out.println("to-delete");
        storeService.removeStore(itemToDelete);
    }
}
