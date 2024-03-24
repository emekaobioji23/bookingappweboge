/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author emeka
 */
@Entity
@Table(name = "student", catalog = "bookings", schema = "")
@NamedQueries({
    @NamedQuery(name = "StudentOGE.findAll", query = "SELECT s FROM StudentOGE s"),
    @NamedQuery(name = "StudentOGE.findById", query = "SELECT s FROM StudentOGE s WHERE s.id = :id"),
    @NamedQuery(name = "StudentOGE.findBySurname", query = "SELECT s FROM StudentOGE s WHERE s.surname = :surname"),
    @NamedQuery(name = "StudentOGE.findByFirstname", query = "SELECT s FROM StudentOGE s WHERE s.firstname = :firstname"),
    @NamedQuery(name = "StudentOGE.findByOthernames", query = "SELECT s FROM StudentOGE s WHERE s.othernames = :othernames")})
public class StudentOGE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 45)
    @Column(length = 45)
    private String surname;
    @Size(max = 45)
    @Column(length = 45)
    private String firstname;
    @Size(max = 45)
    @Column(length = 45)
    private String othernames;

    public StudentOGE() {
    }

    public StudentOGE(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public String getName(){
        return firstname+" "+othernames+" "+surname;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentOGE)) {
            return false;
        }
        StudentOGE other = (StudentOGE) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "personal.bookingappweboge.entities.StudentOGE[ id=" + id + " ]";
    }
    
}
