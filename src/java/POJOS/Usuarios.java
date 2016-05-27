package POJOS;
// Generated 27-may-2016 11:01:44 by Hibernate Tools 4.3.1

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Usuarios generated by hbm2java
 */
public class Usuarios implements java.io.Serializable {

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

    
    @Override
    public String toString() 
    {
        String cadena=id+","+login+","+nombre+","+apellidos+","+fnac+","+fu+","+pass+","+nif+","+esAdm;
        
        return cadena;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (!(object instanceof Usuarios)) {
            return false;
        } else {
            Usuarios user = (Usuarios) object;
            if (id == user.getId()
                    && nombre.equals(user.getNombre())
                    && nif.equals(user.getNif())) {
                return true;
            }
        }
        return false;
    }

    public Usuarios() {
    }

    public Usuarios(List<String> sublista) {
        this.id = Integer.parseInt(sublista.get(0));
        this.login = sublista.get(1);
        this.nombre = sublista.get(2);
        this.apellidos = sublista.get(3);
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        Date f = null;
        try {
            f = fecha.parse(sublista.get(4));
        } catch (ParseException ex) {
            System.err.print("exception con la fecha en contructor empresa " + ex);
        }

        this.fu = f;
        f = null;
        try {
            f = fecha.parse(sublista.get(8));
        } catch (ParseException ex) {
            System.err.print("exception con la fecha en contructor empresa " + ex);
        }

        this.fnac = f;

        this.pass = sublista.get(5);
        this.nif = sublista.get(6);
        this.esAdm = Boolean.parseBoolean(sublista.get(7));
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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFnac() {
        return this.fnac;
    }

    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }

    public Date getFu() {
        return this.fu;
    }

    public void setFu(Date fu) {
        this.fu = fu;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

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

}
