/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.restfulapi;

import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import personal.bookingappweboge.entities.LoginOGE;
import personal.bookingappweboge.entities.TeacherLoginOGE;
import personal.bookingappweboge.entities.TeacherOGE;

/**
 *
 * @author emeka
 */
@Stateless
@Path("personal.bookingappweboge.entities.loginoge")
public class LoginOGEFacadeREST extends AbstractFacade<LoginOGE> {
    @EJB
    private personal.bookingappweboge.session.TeacherOGEFacade teacherFacade;
    @EJB
    private personal.bookingappweboge.restfulapi.TeacherLoginOGEFacadeREST teacherLoginOGEFacadeREST;
    @EJB
    private personal.bookingappweboge.entities.TeacherOGE teacherOGE;
    @EJB
    private personal.bookingappweboge.entities.TeacherLoginOGE teacherLoginOGE;
    
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    public LoginOGEFacadeREST() {
        super(LoginOGE.class);
    }
    public TeacherLoginOGE getTeacherLoginOGEByLoginOGE(LoginOGE loginoge) {
        TypedQuery<TeacherLoginOGE> query = em.createNamedQuery("TeacherLoginOGE.findByLoginid", TeacherLoginOGE.class);
        query.setParameter("loginid", loginoge.getId());
        return query.getSingleResult();
    }
    public TeacherOGE getTeacherOGEByTeacherLoginOGE(TeacherLoginOGE teacherloginoge) {
        TypedQuery<TeacherOGE> query = em.createNamedQuery("TeacherOGE.findById", TeacherOGE.class);
        query.setParameter("id", teacherloginoge.getTeacherid());
        return query.getSingleResult();
    }
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(LoginOGE entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, LoginOGE entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LoginOGE find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LoginOGE> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LoginOGE> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @POST
    @Path("userExists{username}/{password}")
    @Produces(MediaType.TEXT_PLAIN)
    public String userExists(@PathParam("username") String username, @PathParam("password") String password) {
        System.out.println("LoginOGEFacadeREST:userExists called username="+username+" password="+password);
        String toreturn = "false";
        List<LoginOGE> logins = super.findAll();
        for (LoginOGE login : logins) {
            System.out.println("LoginOGEFacadeREST:userExists called username="+username+" password="+password);
            System.out.println("LoginOGEFacadeREST:userExists called username="+login.getName()+" password="+login.getPassword());
            if(login.getName().trim().equalsIgnoreCase(username.trim())&& login.getPassword().trim().equalsIgnoreCase(password.trim())){
                toreturn = "true";
                break;
            }
        }
        System.out.println("LoginOGEFacadeREST:userExists exits");
        return toreturn;
    }
    
    @POST
    @Path("userFullNames{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public String userFullNames(@PathParam("username") String username) {
        System.out.println("LoginOGEFacadeREST:userFullNames called username="+username);
        String toreturn = "I could not find the name";
        LoginOGE login;
        List<LoginOGE> logins = super.findAll();
        for(LoginOGE l : logins){
            System.out.println("LoginOGEFacadeREST:userFullNames called username="+username);
            System.out.println("LoginOGEFacadeREST:userFullNames called username="+l.getName());
            if(l.getName().trim().equalsIgnoreCase(username.trim())){
                login=l;
                teacherLoginOGE = getTeacherLoginOGEByLoginOGE(login);
                System.out.println("LoginOGEFacadeREST:userFullNames called teacherLoginOGE="+teacherLoginOGE.getId());
                teacherOGE = getTeacherOGEByTeacherLoginOGE(teacherLoginOGE);
                System.out.println("LoginOGEFacadeREST:userFullNames called teacherOGE="+teacherOGE.getId());
                toreturn = teacherOGE.getName();
                break;
            }
        }
        
        System.out.println("LoginOGEFacadeREST:userFullNames exits");
        return toreturn;
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
