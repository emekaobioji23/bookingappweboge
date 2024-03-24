package personal.bookingappweboge.jsf;

import personal.bookingappweboge.entities.OffenceOGE;
import personal.bookingappweboge.jsf.util.JsfUtil;
import personal.bookingappweboge.jsf.util.PaginationHelper;
import personal.bookingappweboge.session.OffenceOGEFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("offenceOGEController")
@SessionScoped
public class OffenceOGEController implements Serializable {

    private OffenceOGE current;
    private DataModel items = null;
    @EJB
    private personal.bookingappweboge.session.OffenceOGEFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private String offenceDescription;
    private Integer offenceid;

    public OffenceOGEController() {
    }
    /* This method takes no parameters
     * It is called when the book button is pressed on the web page.
     * It then calls the equivalent in the Session EJB of same name. i.e.,
     * OffenceOGEFacade.
     * It then returns the id of the offence.
     * Note that Apache Tomcat Server will create unique controller objects for
     * each web application user in the RAM.
     * For example, if there are 8 users using the booking app web, there will
     * be 8 Student***Controller,8 Teacher***Controller, 8 Classr***Controller,
     * 8 Team***Controller, 8 Offence***Controller objects but 
     * 1 Student***Facade,1 Teacher***Facade, 1 Classr***Facade,
     * 1 Team***Facade, 1 Offence***Facade objects, and as many Student***, 
     * Teacher***, Classr***, Team***, Offence*** objects as rows of their
     * corresponding tables in the database.
     */
    public Integer createOffence(){
        /*getFacade() returns the Offence***Facade object created by Apache
         * Tomcat Server on the execution of the web application (i.e., on the
         * deploying of the bookingappweb***.10.war).
         * That Offence***Facade object is referenced as ejbFacade.
         * Using ejbFacade createOffence(Integer,String) method, a new 
         * Offence*** entity EJB may be created i.e., a row for this instance 
         * of an offence object is inserted in the offence table.
         * If no new Offence*** object was created, then its id is returned 
         * else -1 is returned.
         */
        return getFacade().createOffence(offenceid, offenceDescription).getId();
    }
    
    /*This method takes only one parameter text of type String*/
    public void setOffenceDescription(String text){
        
        /*the text parameter is assigned to the property of Offence 
         * description*/
        offenceDescription = text;
        
        /*Owing to the fact that user can just type out a new offence 
         * description, each time the offence description is changed, 
         * one has to verify if a new offence has been created. 
         * In order that when it is time to persist the book,
         * the offence with id -1 is created before the book is created.
        */
        OffenceOGE offenceoge = getFacade().findOffenceWithDescription(text);
        
        /*Using the offence id setter method, it updates the offenceid property
         * with the id of the offence if it already exists in offence table or 
         * with -1 if the offence is new.
         */
        setOffenceid(offenceoge.getId());
    }
    public String getOffenceDescription(){
        return offenceDescription;
    }
    public void setOffenceid(int id){
        this.offenceid = id;
    }
    public Integer getOffenceid(){
        return offenceid;
    }
    /*This method takes as parameter a String referenced as userTyped.
     * userTyped refers to what the user types in the offence description 
     * text input component on the web page.
     * as the user types an offence description, the <p:autoComplete .../> tag 
     * takes charge displaying all Offence*** entity EJB objects in the RAM 
     * whose descriptions are similar to user the input.
     */
    public List<String> loadOffenceOGEDescriptions(String userTyped) {
        
        /* Display on Apache Tomcat Webserver output what the user typed.
         * useful to the developer for debugging purposes.*/
        System.out.println("OffenceOGEConroller:loadOffenceOGEDescriptions(String userTyped):userTyped="+userTyped);
        
        /*through the Offence***Facade session EJB object, retrieve all 
         * OffenceOGE session EJB objects that have similar offence description
         * as in the parameter.
         Reference the list as offenceOGEs*/
        List<OffenceOGE> offenceOGEs =  getFacade().findAllWhereDescriptionContains(userTyped);
        
        /* Display on Apache Tomcat Webserver output how many OffenceOGE session
         * EJBs were retrieved.*/
        System.out.println("OffenceOGEConroller:loadOffenceOGEDescriptions(String userTyped):offenceOGEs="+offenceOGEs.size());
        
        /*Obtain an Iterator object from the List<OffenceOGE> object referenced
         as ioffenceOGEs*/
        Iterator<OffenceOGE> ioffenceOGEs = offenceOGEs.iterator();
        
        /*Create a new String list ArrayList<String>*/
        ArrayList<String> offenceDescriptions = new ArrayList();
        
        /*with the aid of a while loop, as long as ioffenceOGEs still contains an 
         * OffenceOGE object, iterate through it, for each OffenceOGE object 
         * encountered in ioffenceOGEs, get its offence description, and adds it 
         * to the ArrayList<String> object referenced as offenceDescriptions
         The method hasNext() advances the Iterator objects pointer one step
         until it is empty.
         The next() method returns the object currently pointed to*/
        while(ioffenceOGEs.hasNext()){
            offenceDescriptions.add(ioffenceOGEs.next().getDescription());
        }
        
        /*it returns the list of descriptions to calling method*/
        return offenceDescriptions;
    }
    public OffenceOGE getSelected() {
        if (current == null) {
            current = new OffenceOGE();
            selectedItemIndex = -1;
        }
        return current;
    }

    private OffenceOGEFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (OffenceOGE) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new OffenceOGE();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OffenceOGECreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (OffenceOGE) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OffenceOGEUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (OffenceOGE) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OffenceOGEDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public OffenceOGE getOffenceOGE(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = OffenceOGE.class)
    public static class OffenceOGEControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OffenceOGEController controller = (OffenceOGEController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "offenceOGEController");
            return controller.getOffenceOGE(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof OffenceOGE) {
                OffenceOGE o = (OffenceOGE) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OffenceOGE.class.getName());
            }
        }

    }

}
