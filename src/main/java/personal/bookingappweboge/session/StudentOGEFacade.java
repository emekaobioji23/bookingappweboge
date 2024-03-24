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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import personal.bookingappweboge.entities.StudentOGE;
import personal.bookingappweboge.entities.StudentOGE_;

/**
 *
 * @author emeka
 */
@Stateless
public class StudentOGEFacade extends AbstractFacade<StudentOGE> {

    /*
     * The createStudentOGE(...){...} method Has 3 parameters of Class type
     * String. The 3 parameters are referenced as surname, firstname, othernames
     * The method is used to add a new student in the student table of the MySQL
     * bookings database. It works by creating a Student*** entity EJB object,
     * choosing an appropriate id for it from the student table in the MySQL
     * bookings database i.e., an id that is unique, since id is the primary key
     * column. It also assings values to or initializes the properties of the
     * Student*** entity EJB object. It then uses the create method of the
     * AbstractFacade to create a row for the Student*** entity EJB object in
     * the student table of the MySQL bookings database.
     */
    public StudentOGE createStudentOGE(String surname, String firstname, String othernames) {

        /*
         * calling the count() method inherited from the AbstractFacade class
         * (that is why one does not see the definition of method count() in
         * Studnet***Facade class) which returns the number of rows in the
         * student table of MySQL bookings database. It then adds 1 to it and
         * assigns it to an Integer object reference or pointer student***Count.
         */
        Integer studentOGECount = count() + 1;

        /*
         * calling the constructor of the Student***entity EJB, it creates an
         * object of the Student***entity EJB
         */
        StudentOGE studentOGE = new StudentOGE();

        /*
         * it then assigns a value to the id, firstname, surname, and othernames
         * properties of the Student*** entity EJB usings the Student*** entity
         * EJB's setter methods and the parameter values
         */
        studentOGE.setId(studentOGECount);
        studentOGE.setFirstname(firstname);
        studentOGE.setSurname(surname);
        studentOGE.setOthernames(othernames);

        /*
         * calling the create(...) method inherited from the AbstractFacade
         * class (that is why one does not see the definition of method
         * create(...) in Student***Facade) which adds a row for the Student***
         * entity EJB in the student table.
         */
        create(studentOGE);

        /*
         * returns the Student*** entity EJB object to the calling method
         */
        return studentOGE;
    }

    /*
     * The findStudentWithNames(...){...} method Has 3 parameters of Class type
     * String. The 3 parameters are referenced as surname, firstname, othernames
     * The method is used to find a row in the the student table of the MySQL
     * bookings database that has the same surname, firstname and othernames as
     * in the parameter list. It works by defining a variant of SQL called JPQL
     * (Java Persistence Query Langugage) that will search the student table for
     * the row that matches the names. If such a row exists, it wraps it as a
     * Student*** entity EJB object and returns to the calling method. If it
     * does not find any matching rows, it creates a new Student*** entity EJB
     * and assigns values to the properties using the method parameters and
     * returns to calling method.
     */
    public StudentOGE findStudentWithNames(String surname, String firstname, String othernames) {

        /*
         * The method getEntityManager() returns an EntityManager object. The
         * EntityManager object has a method getCriteriaBuilder() that returns a
         * CriteriaBuilder object in our case referenced as cb.
         */
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        /*
         * The CriteriaBuilder object has a method createQuery(...) which is
         * used to create a JPQL query object for the corresponding database
         * table of the entity class. In this case, the student table. The JPQL
         * object is referenced as a CriteriaQuery object cq. The CriteriaQuery
         * also needs to know the table to operate upon and that explans the
         * <Student***> suffix.
         */
        CriteriaQuery<StudentOGE> cq = cb.createQuery(StudentOGE.class);

        /*
         * The CriteriaQuery has a method from(...)that uses the entity EJB
         * class provided to determine the table on which to execute the JPQL.
         * The where(...) part of the JPQL contains the comparisons and, equal,
         * or. Below what is being done is: select surname, firstname,
         * othernames from student where (surname = surname_from_parameter and
         * firstname = firstname_from_paramter and othernames =
         * othernames_from_parameter)
         */
        Root<StudentOGE> studentoge = cq.from(StudentOGE.class);
        cq.where(cb.and(
                cb.equal(studentoge.get(StudentOGE_.surname), surname.trim()),
                cb.and(cb.equal(studentoge.get(StudentOGE_.firstname), firstname.trim()),
                        cb.equal(studentoge.get(StudentOGE_.othernames), othernames.trim()))));

        /*
         * The CriteriaQuery object cq is passed as parameter to the
         * createQuery(...) method of the EntityManager to create a query
         * object. The query object (createQuery(cq)) has a method
         * getResultList() that finds all the Student*** entity EJBs with the
         * exact names and keeps them in a List<Student***> object referenced as
         * student***s.
         */
        List<StudentOGE> studentoges = getEntityManager().createQuery(cq).getResultList();

        /*
         * If the List<Student***> object is null or empty for whatever reason,
         * a new Student*** object is created and its properties initialized
         * with the names provided in the parameter list. It is then returned to
         * the calling method. Else the found Student*** object is retrieved
         * from the List using the List method get(...) and returned to calling
         * method.
         */
        if (studentoges != null) {
            if (!studentoges.isEmpty()) {
                return studentoges.get(0);
            } else {
                //create a StudentOGE without id i.e. -1
                StudentOGE studentogeNew = new StudentOGE();
                studentogeNew.setSurname(surname);
                studentogeNew.setFirstname(firstname);
                studentogeNew.setOthernames(othernames);
                studentogeNew.setId(-1);
                return studentogeNew;
            }
        } else {
            //create a StudentOGE without id i.e. -1
            StudentOGE studentogeNew = new StudentOGE();
            studentogeNew.setSurname(surname);
            studentogeNew.setFirstname(firstname);
            studentogeNew.setOthernames(othernames);
            studentogeNew.setId(-1);
            return studentogeNew;
        }
    }

    /*
     * The method public List<StudentOGE> getStudentOGEs(){...} has equivalents
     * in Teacher***Facade and Classr***Facade session EJB. Ir uses the findAll
     * of the AbstractFacade to retrieve all rows in the student table of the
     * MySQL booing database as a List<Student***> object referenced as
     * student***s.
     */
    public List<StudentOGE> getStudentOGEs() {
        List<StudentOGE> studentoges = findAll();
        System.out.println("StudentOGEFacade.findAll()=" + studentoges.size());
        return studentoges;
    }

    
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentOGEFacade() {
        super(StudentOGE.class);
    }

}
