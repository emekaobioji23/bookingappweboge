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
import personal.bookingappweboge.entities.BookOGE;

//added later to enable creation of a booking
import personal.bookingappweboge.entities.TeacherOGE;
import personal.bookingappweboge.entities.ClassrOGE;
import personal.bookingappweboge.entities.StudentOGE;
import personal.bookingappweboge.entities.TeamOGE;
import personal.bookingappweboge.entities.OffenceOGE;

//added later to enable using the highest book table id column to create a new book
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import personal.bookingappweboge.entities.BookOGE_;

/**
 *
 * @author emeka
 */
@Stateless
public class BookOGEFacade extends AbstractFacade<BookOGE> {
    
    /* This method takes as parameters
     * a Teacher*** entity EJB object referenced as teacher,
     * a Student*** entity EJB object referenced as student,
     * a Team*** entity EJB object referenced as team,
     * a Classr*** entity EJB object referenced as classr,
     * a Offence*** entity EJB object referenced as offence,and a String object
     * referenced as dateofbooking
     */
    public String book(TeacherOGE teacher, StudentOGE student, TeamOGE team, 
            ClassrOGE classr, OffenceOGE offence, String dateofbooking){
        
        /*A StringBuilder object sb is created.
         * A StringBuilder is used to easily concatenate String object without
         * using the + symbol. e.g., String s1="a"+"man";
         * It makes more efficient use of memory than use of +.
         */
        StringBuilder sb = new StringBuilder();
        
        /*The try{...}catch(...){...} is used to enclose code that is prone
         * to error such that if the error happens in the try{...}, the
         * catch(...){...} will contain codes that will help the software
         * recover smoothly from the error without leaving the user in a ditch.
         */
        try{
            /*create a new Book*** entity EJB object referenced as book****/
            BookOGE bookoge = new BookOGE();
            
            /*assign values to its properties from the parameters.
             entity EJBs by convention define get and set methods for all their 
             properties. For e.g., if there is a property or variable xxxx then
             * there will be methods getXxxx() and setXxxx(...).
             * Since a book unites teacher, student, team, classroom, offence,
             * and date, it has the properties teacherid, ...,and methods 
             * setTeacherid(...), getTeacherid(...),...
             */
            bookoge.setTeacherid(teacher.getId());
            bookoge.setStudentid(student.getId());
            bookoge.setTeamid(team.getId());
            bookoge.setClassrid(classr.getId());
            bookoge.setOffenceid(offence.getId());
            bookoge.setDateofbooking(dateofbooking);
            
            /*for every new Book*** entity EJB object, it get its id by 
             * calling the nextBookID()*/
            bookoge.setId(nextBookID());
            
             /*the create(...) method inserts an entity EJB object as a new row
              * in the corresponding table.*/
            create(bookoge);
            
            /*Build the message for the user*/
            sb.append(teacher.getName());
            sb.append(" booked ");
            sb.append(student.getName());
            sb.append(" of Team ");
            sb.append(team.getName());
            sb.append(" in ");
            sb.append(classr.getClassr());
            sb.append(" for ");
            sb.append(offence.getDescription());
            sb.append(" on ");
            sb.append(dateofbooking);
            sb.append(".");
            
        /*if an error occurs catch the error object as e*/    
        }catch(Exception e){
            
            /*add the error message to sb and return*/    
            sb.append(e.getMessage());
            return sb.toString();
        }
        
        //return returnMessage;
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public List<BookOGE> getBookOGEs() {        
        List<BookOGE> bookoges = findAll();
        System.out.println("BookOGEFacade.findAll()="+bookoges.size());
        return bookoges;
    }

    /*The method returns next book ID. 
     This approach was adopted when in 2022/2023 session it was discovered 
     that the number of books in the database is not always equal to the 
     highest book ID.
     This is simply because book deletion is not sequential*/
    public Integer nextBookID() {
        /*A CriteriaBuilder object is obtained via the EntityManger.
         *This object is referenced or named as cb.
         *The EntityManager is obtained using the inbuilt 
         *AbstractFacade<Book***> method getEntityManager().
         *The getCriteriaBuilder() method of the EntityManager is used to create
         *a CriteriaBuilder object.
         *A CriteriaBuilder object is used to manipulate entity EJBs in RAM.
         *Here the CriteriaBuilder is named as cb.*/
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                
        /*The cb via its createQuery(Book***.class) creates a 
         *CriteriaQuery<Book***> object referenced as cq.
         *It will a create a query for the collection of Book*** entity EJBs 
         *built from the rows of the book table of the bookings MySQL 
         *database and installed in the RAM by the Apache Tomcat web server.*/
        CriteriaQuery<BookOGE> cq = cb.createQuery(BookOGE.class);
                
         /*The cq with its from(Book***.class) method selects all the 
         *Book*** entity EJB objects in the RAM and stores them in the 
         *Root<Book***> object named book***.
         */
        Root<BookOGE> bookoge = cq.from(BookOGE.class);
        
        /*I don't know why this line*/
        cq.select(bookoge);
        
        /*order the books by their id in descending order such that the highest
         * id is atop
         */
        cq.orderBy(cb.desc(bookoge.get(BookOGE_.id)));
                
        /*Through the getEntityManager() method of the 
         *AbstractFacade<Book***>, the EntityManager object is obtained.
         *The EntityManager's createQuery(...) method is used to create a 
         *Query object.
         *The getResultList() of the Query object retains in the 
         *List<Book***> object referenced as book***s the 
         *Book*** entity EJB objects that meet the selection criteria 
         *encapsulated in the CriteriaQuery<Book***> cq object */
        List<BookOGE> bookoges = getEntityManager().createQuery(cq).getResultList();
        
        
        /*checks if the book***s list is existent*/
        if(bookoges != null){
            /*if the book***s list is existent, check if empty*/
            if (!bookoges.isEmpty()){
                /*if not empty the first item in the list obtained by the
                 *get(...) method of the List<Book***> object book***s 
                 *is the Book*** entity EJB that is being sought for,
                 hence its ID increased one step will be returned to the 
                 * calling method*/
                return bookoges.get(0).getId()+1;
            /*if the book***s list is empty*/
            }else{
                /*it returns 1 as next ID*/
                return 1;
            }
         /*if the book***s list is non-existent*/
        }else{
            /*it returns 1 as next ID*/
            return 1;
        }
    }
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookOGEFacade() {
        super(BookOGE.class);
    }
    
}
