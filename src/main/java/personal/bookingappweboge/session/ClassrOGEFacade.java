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
import personal.bookingappweboge.entities.ClassrOGE;
import personal.bookingappweboge.entities.ClassrOGE_;
import personal.bookingappweboge.entities.StudentOGE;

/**
 *
 * @author emeka
 */
@Stateless
public class ClassrOGEFacade extends AbstractFacade<ClassrOGE> {

    public List<ClassrOGE> getClassrOGEs() {        
        List<ClassrOGE> classroges = findAll();
        System.out.println("ClassrOGEFacade.findAll()="+classroges.size());
        return classroges;
    }
    
    //new code
    
   public ClassrOGE createClassrOGE(String school, Integer level, String arm){
        Integer classrOGECount = count()+1;
        ClassrOGE classrOGE = new ClassrOGE();
        classrOGE.setId(classrOGECount);
        classrOGE.setSchool(school);
        classrOGE.setLevel(level);
        classrOGE.setArm(arm);
        create(classrOGE);
        return classrOGE;
    }
   
    public ClassrOGE findClassrWithProperties(String school, Integer level, String arm) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ClassrOGE> cq = cb.createQuery(ClassrOGE.class);
        Root<ClassrOGE> classroge = cq.from(ClassrOGE.class);
        cq.where(cb.and(
                cb.equal(classroge.get(ClassrOGE_.school), school.trim()), 
                cb.and(cb.equal(classroge.get(ClassrOGE_.level), level),
                cb.equal(classroge.get(ClassrOGE_.arm), arm.trim()))));
        List<ClassrOGE> classroges = getEntityManager().createQuery(cq).getResultList();
        if(classroges != null){
            if (!classroges.isEmpty()){
                return classroges.get(0);
            }else{
                //create a ClassrOGE without id i.e. -1
                ClassrOGE classrogeNew = new ClassrOGE();
                classrogeNew.setSchool(school);
                classrogeNew.setLevel(level);
                classrogeNew.setArm(arm);
                classrogeNew.setId(-1);
                return classrogeNew;
            }
        }else{
            //create a ClassrOGE without id i.e. -1
           ClassrOGE classrogeNew = new ClassrOGE();
           classrogeNew.setSchool(school);
           classrogeNew.setLevel(level);
           classrogeNew.setArm(arm);
           classrogeNew.setId(-1);
           return classrogeNew;
        }
    }
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClassrOGEFacade() {
        super(ClassrOGE.class);
    }
    
}
