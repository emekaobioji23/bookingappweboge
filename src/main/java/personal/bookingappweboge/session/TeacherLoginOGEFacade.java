/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import personal.bookingappweboge.entities.TeacherLoginOGE;

/**
 *
 * @author emeka
 */
@Stateless
public class TeacherLoginOGEFacade extends AbstractFacade<TeacherLoginOGE> {

    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeacherLoginOGEFacade() {
        super(TeacherLoginOGE.class);
    }
    
}
