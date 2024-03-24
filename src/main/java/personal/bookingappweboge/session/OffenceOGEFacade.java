/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.session;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import personal.bookingappweboge.entities.OffenceOGE;
import personal.bookingappweboge.entities.OffenceOGE_;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author emeka
 */
@Stateless
public class OffenceOGEFacade extends AbstractFacade<OffenceOGE> {
    
    /* The method/function 
     * public OffenceOGE createOffence(Integer id, String description){...}
     * Creates an OffenceOGE if the id is -1 i.e. does not already exists, else
     * finds it and returns it to the method caller.
     * It receives two parameters - id (a reference to an Integer object) and 
     * description (a reference to a String object)
     * It checks if id is equal to -1, a -1 id value means that the offence does
     * not yet exist in the offence table of the database.
     * If that is the case, 
        * It creates the new offence as an OffenceOGE object: 
        * OffenceOGE offenceOGE = new OffenceOGE(); 
        * Note offenceOGE (with small letter) can be any name of choice.
        * After creating the offence, using its set methods, it assigns an id to it
        * offenceOGE.setId(offenceCount);
        * Where offenceCount is a reference to an Integer object obtained by the 
        * value returned from the method call count() plus 1:
        * Integer offenceCount = count()+1;
        * The count() method is already defined by the NetBeans creation wizard
        * in the AbstractFacade<OffenceOGE> class inherited by OffenceOGEFacade 
        * using via the extends keyword.
        * Using its set method, it assigns to it a description:
        * offenceOGE.setDescription(description);
        * Then using the create method inherited from the 
        * AbstractFacade<OffenceOGE> class it insert a row for the new offence in 
        * the offence table:
        * create(offenceOGE);
        * It returns the newly created offence object to the caller:
        * return offenceOGE;
    *Else (if the offence already exists in the offence table)
        * using the find method inherited from the AbstractFacade<OffenceOGE> 
        * class it finds the row with the id in the offence table and returns
        * it to the caller as an offence object:
        * OffenceOGE offenceOGE = find(id);
     */
    public OffenceOGE createOffence(Integer id, String description){
        if(id == -1){
            Integer offenceCount = count()+1;
            OffenceOGE offenceOGE = new OffenceOGE();
            offenceOGE.setId(offenceCount);
            offenceOGE.setDescription(description);
            create(offenceOGE);
            return offenceOGE;
        }else{
            OffenceOGE offenceOGE = find(id);
            return offenceOGE;
        }
    }
    
    /*The method takes a parameter of type String referenced as description*/
    public OffenceOGE findOffenceWithDescription(String description) {
        /*A CriteriaBuilder object is obtained via the EntityManger.
         *This object is referenced as cb.
         *The EntityManager is obtained using the inbuilt 
         *AbstractFacade<Offence***> method getEntityManager().
         *The getCriteriaBuilder() method of the EntityManager is used to create
         *a CriteriaBuilder object.
         *A CriteriaBuilder object is used to manipulate entity EJBs in RAM.
         *Here the CriteriaBuilder is named as cb.*/
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        
        /*The cb via its createQuery(Offence***.class) creates a 
         *CriteriaQuery<Offence***> object referenced as cq.
         *It will a create a query for the collection of Offence*** entity EJBs 
         *buiilt from the rows of the offence table of the bookings MySQL 
         *database and installed in the RAM by the Apache Tomcat web server.*/
        CriteriaQuery<OffenceOGE> cq = cb.createQuery(OffenceOGE.class);
        
        
         /*The cq with its from(Offence***.class) method selects all the 
         *Offence*** entity EJB objects in the RAM and stores them in the 
         *Root<Offence***> object named offence***.
         */
        Root<OffenceOGE> offenceoge = cq.from(OffenceOGE.class);
        
        
         /*The cq with its where(***) method selects the Offence*** entity EJB
          *objects whose description match the description of the parameter.*/
        cq.where(cb.equal(offenceoge.get(OffenceOGE_.description), description.trim()));
        
        
        /*Through the getEntityManager() method of the 
         *AbstractFacade<Offence***>, the EntityManager object is obtained.
         *The EntityManager's createQuery(...) method is used to create a 
         *Query object.
         *The getResultList() of the Query object retains in the 
         *List<Offence***> object referenced as offence***s the 
         *Offence*** entity EJB objects that meet the selection criteria 
         *encapsulated in the CriteriaQuery<Offence***> cq object */
        List<OffenceOGE> offenceoges = getEntityManager().createQuery(cq).getResultList();
        
        
        /*checks if the offence***s list is existent*/
        if(offenceoges != null){
            /*if the offence***s list is existent, check if empty*/
            if (!offenceoges.isEmpty()){
                /*if not empty the first item in the list obtained by the
                 *get(...) method of the List<Offence***> object offence***s 
                 *is the Offence*** entity EJB that is being sought for,
                 hence it will be returned to the calling method*/
                return offenceoges.get(0);
            /*if the offence***s list is empty*/
            }else{
                /*it creates an Offence*** entity EJB object without id i.e.*/
                OffenceOGE offenceogeNew = new OffenceOGE();
                /*it set its description to that of the parameter*/
                offenceogeNew.setDescription(description);
                /*it assigns an id of -1 to indicate that the offence***New 
                 *does not yet exists in the database.*/
                offenceogeNew.setId(-1);
                /*it returns it to the calling method*/
                return offenceogeNew;
            }
         /*if the offence***s list is non-existent*/
        }else{
            /*it creates an OffenceOGE without id i.e. -1*/
            OffenceOGE offenceogeNew = new OffenceOGE();
            /*it sets its description to that of the parameter*/
            offenceogeNew.setDescription(description);
            /*it assigns an id of -1 to indicate that the offence***New does not
            *yet exists in the offence table of the database.*/
            offenceogeNew.setId(-1);
            /*return it to the calling method*/
            return offenceogeNew;
        }
    }
    
    /* The method takes as parameter a text. 
     * The text is an object of String referenced as regex.
     */
    public List<OffenceOGE> findAllWhereDescriptionContains(String regex) {
        
        /*A CriteriaBuilder object referenced as cb is obtained from an
         * EntityManager object.
         * The EntityManager object in its turn is obtained via the inbuilt
         * getEntityManager() method of the Offence***Facade class.
         * The getEntityManager() method simply returns the inbuilt EntityManager
         * object reference as em.
         * The inbuilt getCriteriaBuilder() method of the em is used to create
         * the CriteriaBuilder object referenced as cb.
         */
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        
        /* The createQuery(...) method of the cb object is used to create a
         * CriteriaQuery<OffenceOGE> object referenced as cq.
         * The createQuery takes as parameter the class file of an entity EJB
         * because it should be able to identify the entity objects of that 
         * class in the RAM.
         * In this case the OffenceOGE.class was the parameter.
         */
        CriteriaQuery<OffenceOGE> cq = cb.createQuery(OffenceOGE.class);
        
        
        /* The from method of the cq object is used to select all OffenceOGE
         * entity EjBs in the RAM.
         */
        Root<OffenceOGE> offenceoge = cq.from(OffenceOGE.class);
        /* The where method of the cq object is used to express the nature of
         * the properties of the entity EJB that one hopes to choose.
         * The like method of cb object aids comparison between two values. 
         */
        cq.where(cb.like(offenceoge.get(OffenceOGE_.description), "%"+regex.trim()+"%"));
        
        /* The cq getResultList() method gathers all OffenceOGE entity EJBs in 
         * the RAM that contains the string referenced as regex in their 
         * description into a List of OffenceEJB objects i.e., List<OffenceOGE>
         */
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<OffenceOGE> getOffenceOGEs() {        
        List<OffenceOGE> offenceoges = findAll();
        System.out.println("OffenceOGEFacade.findAll()="+offenceoges.size());
        return offenceoges;
    }
    
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OffenceOGEFacade() {
        super(OffenceOGE.class);
    }
    
}
