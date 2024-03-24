/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personal.bookingappweboge.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author emeka
 */
@Entity
@Table(name = "teacherlogin", catalog = "bookings", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeacherLoginOGE.findAll", query = "SELECT t FROM TeacherLoginOGE t"),
    @NamedQuery(name = "TeacherLoginOGE.findById", query = "SELECT t FROM TeacherLoginOGE t WHERE t.id = :id"),
    @NamedQuery(name = "TeacherLoginOGE.findByTeacherid", query = "SELECT t FROM TeacherLoginOGE t WHERE t.teacherid = :teacherid"),
    @NamedQuery(name = "TeacherLoginOGE.findByLoginid", query = "SELECT t FROM TeacherLoginOGE t WHERE t.loginid = :loginid"),
    @NamedQuery(name = "TeacherLoginOGE.findByLastlogin", query = "SELECT t FROM TeacherLoginOGE t WHERE t.lastlogin = :lastlogin")})
public class TeacherLoginOGE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int teacherid;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int loginid;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogin;

    public TeacherLoginOGE() {
    }

    public TeacherLoginOGE(Integer id) {
        this.id = id;
    }

    public TeacherLoginOGE(Integer id, int teacherid, int loginid, Date lastlogin) {
        this.id = id;
        this.teacherid = teacherid;
        this.loginid = loginid;
        this.lastlogin = lastlogin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
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
        if (!(object instanceof TeacherLoginOGE)) {
            return false;
        }
        TeacherLoginOGE other = (TeacherLoginOGE) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "personal.bookingappweboge.entities.TeacherLoginOGE[ id=" + id + " ]";
    }
    
}
