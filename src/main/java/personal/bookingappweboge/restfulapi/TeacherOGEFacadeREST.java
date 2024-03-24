/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.restfulapi;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import personal.bookingappweboge.entities.TeacherOGE;
import personal.bookingappweboge.session.TeacherOGEFacade;

/**
 *
 * @author emeka
 */
@Stateless
@Path("personal.bookingappweboge.entities.teacheroge")
public class TeacherOGEFacadeREST extends AbstractFacade<TeacherOGE> {
    
    @PersistenceContext(unitName = "bookingappwebogepu")
    private EntityManager em;

    public TeacherOGEFacadeREST() {
        super(TeacherOGE.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TeacherOGE entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, TeacherOGE entity) {
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
    public TeacherOGE find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TeacherOGE> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TeacherOGE> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @EJB 
    TeacherOGEFacade teacherOGEFacade; 
    @GET
    @Path("{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TeacherOGE> findByName(@PathParam("name") String name) {
        System.out.println("worked...........");
        try{
            teacherOGEFacade = 
                    (TeacherOGEFacade)(new InitialContext().
                            lookup("java:module/TeacherOGEFacade"));
        }catch(NamingException e){
            System.err.println("TeacherOGEFacadeREST:findByName "+e.toString());
        }
        return teacherOGEFacade.findTeachersByName(name);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
