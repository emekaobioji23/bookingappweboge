/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import personal.bookingappweboge.entities.LoginOGE;

/**
 *
 * @author emeka
 */
@Stateless
public class LoginOGEFacade extends AbstractFacade<LoginOGE> {

    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginOGEFacade() {
        super(LoginOGE.class);
    }
    
}
