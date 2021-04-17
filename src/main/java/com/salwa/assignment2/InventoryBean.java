package com.salwa.assignment2;


import com.salwa.assignment2.entity.Inventory;
import com.salwa.assignment2.entity.Store;
import com.salwa.assignment2.services.InventoryService;
import com.salwa.assignment2.services.StoreService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//@Named is commonly used if there are more than one implementation for an interface.
// So it provides to give and inject by their names
@Named
//Use of this annotation requires that any beans stored in view scope must be serializable and
// proxyable as defined in the CDI specification, the runtime must ensure that any methods on the bean
// annotated with PostConstruct or PreDestroy are called when the scope begins and ends, respectively.
@ViewScoped
public class InventoryBean implements Serializable {

    private int storeId;
    private Store store = new Store();
    private Inventory inventory = new Inventory();
    private List<Inventory> ivList = new ArrayList<>();

    private boolean isAscSort = true;

    //Inject ejb
    @EJB
    private StoreService storeService;

    //Inject ejb
    @EJB
    private InventoryService inventoryService;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) throws NamingException, IOException {
        if (storeId == 0) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("stores.xhtml");
        }
        this.storeId = storeId;
    }

    public Store getStore() throws NamingException {
        this.store = storeService.findStoreById(storeId);
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public List<Inventory> getIvList() {
        this.ivList = inventoryService.getInventoryByStoreId(storeId);
        //sort by update date
        this.ivList = ivList.stream().sorted(Comparator.comparing(Inventory::getUpdated).reversed()).collect(Collectors.toList());
        return ivList;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }


    //Create product
    public void productDetails(AjaxBehaviorEvent event) throws NamingException {
        //set time and date of creation
        inventory.setCreated(LocalDateTime.now());
        inventory.setUpdated(LocalDateTime.now());
        //set store to inventory
        inventory.setStore(this.store);
        //add new product to inventory
        inventoryService.addToInventory(inventory);
        //get product details
        getDetailsProduct();
        //object of Inventory
        inventory = new Inventory();
    }

    //get details data for product from store Service
    private void getDetailsProduct() throws NamingException {
        this.store = storeService.findStoreById(storeId);
    }

    //Method for deleting product
    public void deleteItem(AjaxBehaviorEvent event) throws NamingException {
        Long itemToDelete = (Long) event.getComponent().getAttributes().get("itemToDelete");
        inventoryService.removeInventory(itemToDelete);
        getDetailsProduct();
    }

}
