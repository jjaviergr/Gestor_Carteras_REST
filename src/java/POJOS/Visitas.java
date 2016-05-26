package POJOS;
// Generated 02-may-2016 18:45:51 by Hibernate Tools 4.3.1

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Visitas generated by hbm2java
 */
@XmlRootElement(name = "Visitas")
public class Visitas implements java.io.Serializable {

    private Integer id;
    private Empresas empresas;
    private Usuarios usuarios;
    private Date fecha;
    private String resultado;
    private String motivo;

    public Visitas() {
    }

    public Visitas(Empresas empresas, Usuarios usuarios, Date fecha) {
        this.empresas = empresas;
        this.usuarios = usuarios;
        this.fecha = fecha;
    }

    public Visitas(Empresas e, Usuarios u, String motivo, Date f, String resultado) {

        this.empresas = e;
        this.usuarios = u;
        this.fecha = f;
        this.resultado = resultado;
        this.motivo = motivo;
    }

    public Visitas(String cadena) 
    {
        this.id = Integer.parseInt(cadena.split(",")[0]);
        //11
        List<String> subcadena = new ArrayList();
        for (int i = 1; i < 12; i++) {
            subcadena.add(cadena.split(",")[i]);
        }
        this.empresas = new Empresas(subcadena);
        subcadena = new ArrayList();
        for (int i = 12; i < 21; i++) {
            subcadena.add(cadena.split(",")[i]);
        }
        
        this.usuarios = new Usuarios(subcadena);
        
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        Date f = null;
        try 
        {
            f = fecha.parse(cadena.split(",")[20]);
        } 
        catch (ParseException ex) 
        {
            System.err.print("exception con la fecha en contructor empresa " + ex);
        }
        
        this.fecha = f;
        this.resultado = cadena.split(",")[21];
        this.motivo = cadena.split(",")[22];
    }

    /**
     *
     * @param e
     * @param u
     * @param motivo
     * @param f
     * @param resultado
     * @param motivo
     */
    public Visitas(Integer id_, Empresas e, Usuarios u, String motivo, Date f, String resultado) {
        this.id = id_;
        this.empresas = e;
        this.usuarios = u;
        this.fecha = f;
        this.resultado = resultado;
        this.motivo = motivo;
    }

    @XmlElement
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Empresas getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    @XmlElement
    public Usuarios getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @XmlElement
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlElement
    public String getResultado() {
        return this.resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @XmlElement
    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        
         
  
            String cadena = id + "," + empresas + "," + usuarios + "," +fecha+ "," + resultado + "," + motivo;

        return cadena;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (!(object instanceof Usuarios)) {
            return false;
        } else {
            Visitas v = (Visitas) object;
            if (id.equals(v.getId()) && (empresas.equals(v.getEmpresas())) && (usuarios.equals(v.getUsuarios()))) {
                return true;
            }
        }
        return false;
    }

}
