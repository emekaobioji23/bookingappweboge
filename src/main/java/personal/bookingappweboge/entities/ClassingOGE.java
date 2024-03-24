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
@Table(name = "classing", catalog = "bookings", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClassingOGE.findAll", query = "SELECT c FROM ClassingOGE c"),
    @NamedQuery(name = "ClassingOGE.findById", query = "SELECT c FROM ClassingOGE c WHERE c.id = :id"),
    @NamedQuery(name = "ClassingOGE.findByStudentid", query = "SELECT c FROM ClassingOGE c WHERE c.studentid = :studentid"),
    @NamedQuery(name = "ClassingOGE.findByClassrid", query = "SELECT c FROM ClassingOGE c WHERE c.classrid = :classrid"),
    @NamedQuery(name = "ClassingOGE.findByDateofclassing", query = "SELECT c FROM ClassingOGE c WHERE c.dateofclassing = :dateofclassing")})

public class ClassingOGE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    private Integer studentid;
    private Integer classrid;
    @Size(max = 10)
    @Column(length = 10)
    private String dateofclassing;

    public ClassingOGE() {
    }

    public ClassingOGE(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getClassrid() {
        return classrid;
    }

    public void setClassrid(Integer classrid) {
        this.classrid = classrid;
    }

    public String getDateofclassing() {
        return dateofclassing;
    }

    public void setDateofclassing(String dateofclassing) {
        this.dateofclassing = dateofclassing;
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
        if (!(object instanceof ClassingOGE)) {
            return false;
        }
        ClassingOGE other = (ClassingOGE) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "personal.bookingappweboge.entities.ClassingOGE[ id=" + id + " ]";
    }
    
}
