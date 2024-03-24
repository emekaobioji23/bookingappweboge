/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import personal.bookingappweboge.entities.TeacherOGE_;
import personal.bookingappweboge.entities.TeacherOGE;

/**
 *
 * @author emeka
 */
@Stateless
public class TeacherOGEFacade extends AbstractFacade<TeacherOGE> {

    public List<TeacherOGE> getTeacherOGEs() {        
        List<TeacherOGE> teacheroges = findAll();
        System.out.println("TeacherOGEFacade.findAll()="+teacheroges.size());
        return teacheroges;
    }

    public List<TeacherOGE> findTeachersByName(String name) {        
       /*A CriteriaBuilder object is obtained via the EntityManger.
         *This object is referenced as cb.
         *The EntityManager is obtained using the inbuilt 
         *AbstractFacade<Teacher***> method getEntityManager().
         *The getCriteriaBuilder() method of the EntityManager is used to create
         *a CriteriaBuilder object.
         *A CriteriaBuilder object is used to manipulate entity EJBs in RAM.
         *Here the CriteriaBuilder is named as cb.*/
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        
        /*The cb via its createQuery(Teacher***.class) creates a 
         *CriteriaQuery<Teacher***> object referenced as cq.
         *It will a create a query for the collection of Teacher*** entity EJBs 
         *buiilt from the rows of the teacher table of the bookings MySQL 
         *database and installed in the RAM by the Apache Tomcat web server.*/
        CriteriaQuery<TeacherOGE> cq = cb.createQuery(TeacherOGE.class);
        
        
         /*The cq with its from(Teacher***.class) method selects all the 
         *Teacher*** entity EJB objects in the RAM and stores them in the 
         *Root<Teacher***> object named teaccher***.
         */
        Root<TeacherOGE> teacheroge = cq.from(TeacherOGE.class);
        
        
         /*The cq with its where(***) method selects the Teacher*** entity EJB
          *objects, and the cb with its or selects the Teacher*** entity EJB 
          * objects whose firstname, surname or othernames match the name 
          * of the parameter.*/
        cq.where(cb.or(cb.equal(teacheroge.get(TeacherOGE_.firstname), 
                name.trim()),
                cb.or(cb.equal(teacheroge.get(TeacherOGE_.surname), 
                        name.trim()),
                        cb.or(cb.equal(teacheroge.get(TeacherOGE_.othernames), 
                                name.trim())))));
        
        
        /*Through the getEntityManager() method of the 
         *AbstractFacade<Teacher***>, the EntityManager object is obtained.
         *The EntityManager's createQuery(...) method is used to create a 
         *Query object.
         *The getResultList() of the Query object retains in the 
         *List<Teacher***> object referenced as teacher***s, the 
         *Teacher*** entity EJB objects that meet the selection criteria 
         *encapsulated in the CriteriaQuery<Teacher***> cq object */
        List<TeacherOGE> teacheroges = 
                getEntityManager().createQuery(cq).getResultList();
        
        
        /*checks if the teacher***s list is existent*/
        if(teacheroges != null){
            /*if the teacher***s list is existent, check if empty*/
            if (!teacheroges.isEmpty()){
                /*if not empty return to the calling method*/
                return teacheroges;
            /*if the offence***s list is empty*/
            }else{
                /*it creates a mock Teacher*** entity EJB object without 
                 * id i.e.*/
                TeacherOGE teacherogeNew = new TeacherOGE();
                /*it set its firstname, surname, othernames*/
                teacherogeNew.setFirstname(name+"Does not exist");
                /*it assigns an id of -1 to indicate that the teacher***New 
                 *does not yet exists in the database.*/
                teacherogeNew.setId(-1);
                /*it adds to empty List<TeacherOGE> and returns it to 
                 * the calling method*/
                teacheroges.add(teacherogeNew);
                return teacheroges;
            }
         /*if the teacher***s list is non-existent*/
        }else{
            /*it creates a mock Teacher*** entity EJB object without 
            * id i.e.*/
            TeacherOGE teacherogeNew = new TeacherOGE();
            /*it set its firstname, surname, othernames*/
            teacherogeNew.setFirstname(name+"Does not exist");
            /*it assigns an id of -1 to indicate that the teacher***New 
            *does not yet exists in the database.*/
            teacherogeNew.setId(-1);
            /*it creates a an empty List<TeacherOGE>, add the mock Teacher,
             * and returns it to the calling method*/
            teacheroges = new ArrayList<>();
            teacheroges.add(teacherogeNew);
            return teacheroges;
        }
    }
    
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeacherOGEFacade() {
        super(TeacherOGE.class);
    }
    
}
