package POJOS;
// Generated 02-may-2016 18:45:51 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Usuarios generated by hbm2java
 */
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Usuarios")
public class Usuarios  implements Serializable {

private static final long serialVersionUID = 1L;
    // @QueryParam("entityId")
     private Integer id;
     private String login;
     private String nombre;
     private String apellidos;
     private Date fnac;
     private Date fu;
     private String pass;
     private String nif;
     private boolean esAdm;
     private Set visitases = new HashSet(0);

    public Usuarios() 
    {
    }

    @Override
    public String toString() 
    {
        String cadena=login+","+nombre+","+apellidos+","+fnac+","+pass+","+nif+","+esAdm;
        
        return cadena;
    }
    
    public Usuarios(String login, String nombre, String apellidos, Date fnac, String pass, String nif, boolean esAdm) {
        this.login = login;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fnac = fnac;
        this.pass = pass;
        this.nif = nif;
        this.esAdm = esAdm;
    }
  
    public Usuarios(String login, String nombre, String apellidos, Date fnac, Date fu, String pass, String nif, boolean esAdm, Set visitases) {
       this.login = login;
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.fnac = fnac;
       this.fu = fu;
       this.pass = pass;
       this.nif = nif;
       this.esAdm = esAdm;
       this.visitases = visitases;
    }
    
   @XmlElement
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    @XmlElement
    public String getLogin() {
        return this.login;
    }
    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }
    @XmlElement
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @XmlElement
    public String getApellidos() {
        return this.apellidos;
    }
  
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    @XmlElement
    public Date getFnac() {
        return this.fnac;
    }
   
    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }
    @XmlElement
    public Date getFu() {
        return this.fu;
    }
  
    public void setFu(Date fu) {
        this.fu = fu;
    }
    
    @XmlElement
    public String getPass() {
        return this.pass;
    }
    
    
 
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    @XmlElement
    public String getNif() {
        return this.nif;
    }
    
  
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    @XmlElement
    public boolean isEsAdm() {
        return this.esAdm;
    }
    
   
    public void setEsAdm(boolean esAdm) {
        this.esAdm = esAdm;
    }
    
   
    public Set getVisitases() {
        return this.visitases;
    }
    
   
    public void setVisitases(Set visitases) {
        this.visitases = visitases;
    }

   @Override
   public boolean equals(Object object){
      if(object == null){
         return false;
      }else if(!(object instanceof Usuarios)){
         return false;
      }else {
         Usuarios user = (Usuarios)object;
         if(id == user.getId()
            && nombre.equals(user.getNombre())
            && nif.equals(user.getNif())
         ){
            return true;
         }			
      }
      return false;
   }


}

