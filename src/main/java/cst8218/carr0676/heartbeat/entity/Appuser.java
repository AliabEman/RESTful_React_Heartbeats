/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.carr0676.heartbeat.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.enterprise.inject.Instance;
import javax.security.enterprise.identitystore.PasswordHash;;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
/**
 *
 * @author Dylan
 */
@Entity
@Table(name = "APPUSER")

public class Appuser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERID")
    private String userid = UUID.randomUUID().toString();
    
     
    @Column(name = "PASSWORD")
    @NotNull
    private String password;
    
    
    @NotNull
    @Column(name = "GROUPNAME")
    @Size(max = 50)
    private String groupname;
    

    public Appuser() {
    }



   public String getUserid() {
        return userid;
    }

    public void setUserId(String userid) {
         this.userid = userid;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String password) {
        if(!password.equals("")){
                     
            Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
            PasswordHash passwordHash = instance.get();
            passwordHash.initialize(new HashMap<>());

            
            String inputP = password;
            inputP = passwordHash.generate(inputP.toCharArray());
            //hash password then store in field
            
            this.password = inputP;
        }else{
            // do nothing - user does not want to change password
        }
        
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appuser)) {
            return false;
        }
        Appuser other = (Appuser) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.lab4.AppUser[ id=" + userid + " ]";
    }
    
}
