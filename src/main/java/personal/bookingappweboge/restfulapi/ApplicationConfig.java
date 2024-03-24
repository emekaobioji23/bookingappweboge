/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.restfulapi;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author emeka
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(personal.bookingappweboge.restfulapi.BookOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.ClassingOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.ClassrOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.CrossOriginResourceSharingFilter.class);
        resources.add(personal.bookingappweboge.restfulapi.LoginOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.OffenceOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.StudentOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.TeacherLoginOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.TeacherOGEFacadeREST.class);
        resources.add(personal.bookingappweboge.restfulapi.TeamOGEFacadeREST.class);
    }
    
}
