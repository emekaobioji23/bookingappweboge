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
import personal.bookingappweboge.entities.ClassingOGE;
import personal.bookingappweboge.entities.ClassingOGE_;
import personal.bookingappweboge.entities.StudentOGE;

/*05/06/2023
 * 
 */
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJB;
import static personal.bookingappweboge.entities.ClassingOGE_.dateofclassing;

/**
 *
 * @author emeka
 */
@Stateless
public class ClassingOGEFacade extends AbstractFacade<ClassingOGE> {
    @EJB
    private personal.bookingappweboge.session.StudentOGEFacade studentOGEFacade;
    //new code
    public ClassingOGE createClassingOGE
        (Integer studentid, Integer classrid, String dateofclassing){
        Integer classingOGECount = count()+1;
        ClassingOGE classingOGE = new ClassingOGE();
        classingOGE.setId(classingOGECount);
        classingOGE.setStudentid(studentid);
        classingOGE.setClassrid(classrid);
        classingOGE.setDateofclassing(dateofclassing);
        create(classingOGE);
        return classingOGE;
    }
    
    public ClassingOGE findClassingWithProperties
        (Integer studentId, Integer classrId, String dateofclassing){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ClassingOGE> cq = cb.createQuery(ClassingOGE.class);
        Root<ClassingOGE> classingoge = cq.from(ClassingOGE.class);
        cq.where(cb.and(
                cb.equal(classingoge.get(ClassingOGE_.studentid), studentId), 
                cb.equal(classingoge.get(ClassingOGE_.classrid), classrId)));
        List<ClassingOGE> classingoges = getEntityManager().createQuery(cq).getResultList();
        if(classingoges != null){
            if (!classingoges.isEmpty()){
                return classingoges.get(0);
            }else{
                //create a ClassingOGE without id i.e. -1
                ClassingOGE classingogeNew = new ClassingOGE();
                classingogeNew.setClassrid(classrId);
                classingogeNew.setStudentid(studentId);
                classingogeNew.setDateofclassing(dateofclassing);
                classingogeNew.setId(-1);
                return classingogeNew;
            }
        }else{
            //create a ClassingOGE without id i.e. -1
            ClassingOGE classingogeNew = new ClassingOGE();
            classingogeNew.setClassrid(classrId);
            classingogeNew.setStudentid(studentId);
            classingogeNew.setDateofclassing(dateofclassing);
            classingogeNew.setId(-1);
            return classingogeNew;
        }
    }
    
    /*find students in classr*/
    public List<StudentOGE> findStudentsInClasr
        (Integer classrId){
            //create a studentoge list for holding student entities
        ArrayList<StudentOGE> studentoges = new ArrayList<StudentOGE>();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ClassingOGE> cq = cb.createQuery(ClassingOGE.class);
        Root<ClassingOGE> classingoge = cq.from(ClassingOGE.class);
        cq.where(cb.equal(classingoge.get(ClassingOGE_.classrid), classrId));
        List<ClassingOGE> classingoges = getEntityManager().createQuery(cq).getResultList();
        if(classingoges != null){
            //if class not found amongst all classing entties (almost impossible)
            //return the student list empty
            if (classingoges.isEmpty()){
                return studentoges;
            }else{
                Iterator<ClassingOGE> classingogeit = classingoges.iterator();
                while(classingogeit.hasNext()){
                    studentoges.add(studentOGEFacade.find(classingogeit.next().getStudentid()));
                }
                System.out.println("ClassingOGEController:findStudentsInClasr("+classrId+") = "+studentoges.size());
                return studentoges;
            }
        }else{
            return studentoges;
        }
    }
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClassingOGEFacade() {
        super(ClassingOGE.class);
    }
    
}
