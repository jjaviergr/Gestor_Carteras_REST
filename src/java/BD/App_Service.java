/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

/**
 *
 * @author pc
 */
import POJOS.Coordenadas;
import POJOS.Empresas;
import POJOS.Usuarios;
import POJOS.Visitas;
import es.cartera.Md5;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/App_Service")
public class App_Service {

    private static final String SUCCESS_RESULT = "Exito";
    private static final String FAILURE_RESULT = "Fracaso";

    @GET
    @Path("/updateEmpresa/{paquete}")
    @Produces(MediaType.TEXT_HTML)
    public String Update_Empresa(@PathParam("paquete") String paquete) {
        String exito = "fracaso";
        String[] datos = paquete.split(",");
        String CIF = paquete.split(",")[0];
        Empresas em = Op_Empresas.find(CIF);
        if (em != null) {
            int telefono = -1;
            try {
                telefono = Integer.parseInt(datos[6]);
            } catch (Exception e) {
                System.err.print("Exception con el telefono en servicio Updatempresa " + e);
            }
            int comercial = -1;
            try {
                Usuarios u = Op_Usuarios.find_by_id(Integer.parseInt(datos[7]));
                if (u != null) {
                    comercial = u.getId();
                }
            } catch (Exception e) {
                System.err.print("Exception con el comercialen servicio UpdateEmpresa  " + e);
            }

            Empresas e = new Empresas(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], telefono, comercial, datos[9]);
            e.setId(em.getId());
            e.setFechaAlta(em.getFechaAlta());

            Op_Empresas.update(e.getId(), e);

            exito = "ok";
        }

        return (exito);

    }

    @GET
    @Path("/borrarempresabycif/{cif}")
    public void borrarEmpresabyCIF(@PathParam("cif") String CIF) {
        Empresas e = Op_Empresas.find(CIF);
        int id = e.getId();
        if (id != -1) {
            Op_Empresas.delete(id);
        }
    }

    @GET
    @Path("/empresabycif/{cif}")
    @Produces(MediaType.TEXT_PLAIN)
    public String EmpresaByCIF(@PathParam("cif") String CIF) {
        Empresas e = BD.Op_Empresas.find(CIF);
        return (e.toString());
    }

    @GET
    @Path("/addvisita/{paquete}")
    @Produces(MediaType.TEXT_HTML)
    public String AddVisita(@PathParam("paquete") String paquete) {
        String mensaje = "";
        //Empresas e, Usuarios u, String motivo, Date f, String resultado
        String[] datos = paquete.split(",");

        Empresas e = BD.Op_Empresas.find_by_name(datos[0]);
        Usuarios u = BD.Op_Usuarios.find_by_name(datos[1]);

        SimpleDateFormat sfecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = sfecha.parse(datos[4]);
        } catch (ParseException ex) {
            System.err.print("exception con la fecha en contructor empresa " + ex);
        }
        String resultado = datos[2];
        String motivo = datos[3];

        Visitas v = new Visitas(e, u, motivo, fecha, resultado);

        try {
            int id = Op_Visitas.add(v);
            mensaje = Integer.toString(id);
        } catch (Exception ex) {
            mensaje = "problema al insertar Visita en Servicio " + ex;
            System.err.print("problema al insertar Visita en Servicio " + ex);
        } finally {
            return mensaje;
        }
    }

    @GET
    @Path("/addempresa/{paquete}")
    @Produces(MediaType.TEXT_HTML)
    public String AddEmpresa(@PathParam("paquete") String paquete) {
        String[] datos = paquete.split(",");

        int telefono = -1;
        try {
            telefono = Integer.parseInt(datos[7]);
        } catch (Exception e) {
            System.err.print("Exception con el telefono en servicio AddEmpresa " + e);
        }
        int id = -1;
        try {
            id = Integer.parseInt(datos[7]);
        } catch (Exception e) {
            System.err.print("Exception con id del comercial " + e);
        }

        Empresas e = new Empresas(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], telefono, id, datos[9]);

        String mensaje = "";
        try {
            Op_Empresas.add(e);
            mensaje = "ok";
        } catch (Exception ex) {
            mensaje = "problema al insertar empresa en servicio " + ex;
            System.err.print("problema al insertar empresa en servicio " + ex);
        } finally {
            return mensaje;
        }
    }

    @GET
    @Path("/valida_usuario/{datos}")
    @Produces(MediaType.TEXT_PLAIN)
    public String valida_usuario(@PathParam("datos") String datos) {

        if (datos!=null)
        {
            
            String login=datos.split(",")[0];
            String pass=datos.split(",")[1];
            String md5_pass=Md5.getStringMessageDigest(pass, Md5.MD5);
            int id=BD.Op_Usuarios.validar(login, md5_pass);
            if(id==-1)
                return("usuario no valido");
            else
            {
                if (BD.Op_Usuarios.esAdm(login))
                {                
                    return ("menu_adm.html?"+Integer.toString(id));
                }
                else
                {
                    return ("menu.html?"+Integer.toString(id));
                }
                
            }
        }
        else
            return "no hay datos";

    }
    
    @GET
    @Path("/nombre/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getNombre(@PathParam("id") String id) {

        try {
            int int_id = Integer.parseInt(id);
            Usuarios user = Op_Usuarios.find_by_id(int_id);
            return user.getNombre();
        } catch (Exception e) {
            return "";
        }

    }

    @GET
    @Path("/saluda")
    @Produces(MediaType.TEXT_HTML)
    public String getHello() {
        return "hola";
    }

    @GET
    @Path("/lista_comerciales")
    @Produces(MediaType.APPLICATION_XML)
    public ResposeList ListUsers() {
        ResposeList rlist = null;
        try {
            List lista = BD.Op_Usuarios.list();
            rlist = new ResposeList();
            rlist.setList(lista);
        } catch (Exception e) {
            System.err.print("Excepcion en servicio con lista comerciles" + e.getMessage().toString());
        }
        return rlist;
    }

    /**
     * Devuelve todas las fechas en las que el usuario id ha grabado coordenadas
     * @param id
     * @return Un String separado por ',';
     */
    @GET
    @Path("/getfechas_de_coordenadas/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getfechas_de_coordenadas(@PathParam("id") String id)
    {
        return (BD.Op_Usuarios.getFechasCoordenadas(id));
    }
    
    
    @GET
    @Path("/getcoordenadas/{paquete}")
    @Produces(MediaType.APPLICATION_XML)
    public ResposeList get_coordenadas_by_date(@PathParam("paquete") String paquete) {
        ResposeList rlist = null;
        try {
            int user_id = Integer.parseInt(paquete.split(",")[0]);
            SimpleDateFormat sfecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = null;
            try {
                fecha = sfecha.parse(paquete.split(",")[1]);
            } catch (Exception e) {
                System.err.print("exception con la fecha en contructor coordenadas " + e);
            }

            List lista = BD.Op_Coordenadas.find(user_id, fecha);
            rlist = new ResposeList();

            rlist.setList(lista);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return rlist;
    }

    @GET
    @Path("/getprimeravisita")
    @Produces(MediaType.TEXT_PLAIN)
    public String GetPrimeraVisita() {
        return Op_Visitas.get_primera_Visita().toString();
    }

    @GET
    @Path("/listvisitas/{pos_act},{n_visitas}")
    @Produces(MediaType.APPLICATION_XML)
    public ResposeList ListVisitas(@PathParam("pos_act") int pos_act, @PathParam("n_visitas") int n_visitas) {
        ResposeList rlist = null;
        try {
            List lista = BD.Op_Visitas.list();
            rlist = new ResposeList();

            List sublist = new ArrayList();
            int limite = -1;
            if (n_visitas > lista.size()) {
                limite = lista.size();
            } else {
                limite = n_visitas;
            }
            for (int i = pos_act; i < limite; i++) {
                sublist.add(lista.get(i));
            }
            rlist.setList(sublist);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        return rlist;
    }

    @GET
    @Path("/listusers/{pos_act},{n_users}")
    @Produces(MediaType.APPLICATION_XML)
    public ResposeList ListUsers(@PathParam("pos_act") int pos_act, @PathParam("n_users") int n_users) {
        ResposeList rlist = null;
        try {
            List lista = BD.Op_Usuarios.list();
            rlist = new ResposeList();

            List sublist = new ArrayList();
            int limite = -1;
            if (n_users > lista.size()) {
                limite = lista.size();
            } else {
                limite = n_users;
            }
            for (int i = pos_act; i < limite; i++) {
                sublist.add(lista.get(i));
            }
            rlist.setList(sublist);
        } catch (Exception e) {
            System.err.print(e.getMessage().toString());
        }
        return rlist;
    }

    @GET
    @Path("/listempresas/{pos_act},{n_empresas}")
    @Produces(MediaType.APPLICATION_XML)
    public ResposeList ListEmpresas(@PathParam("pos_act") int pos_act, @PathParam("n_empresas") int n_empresas) {
        ResposeList rlist = null;
        try {
            List lista = BD.Op_Empresas.list();
            rlist = new ResposeList();

            List sublist = new ArrayList();
            int limite = -1;
            if (n_empresas > lista.size()) {
                limite = lista.size();
            } else {
                limite = n_empresas;
            }
            for (int i = pos_act; i < limite; i++) {
                sublist.add(lista.get(i));
            }
            rlist.setList(sublist);
        } catch (Exception e) {
            System.err.print(e.getMessage().toString());
        }
        return rlist;
    }

    @GET
    @Path("/numero_visitas")
    @Produces(MediaType.TEXT_PLAIN)
    public String numero_visitas() {

        int numero = -1;
        try {
            numero = BD.Op_Visitas.length();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            return (String.valueOf(numero));
        }
    }

    @GET
    @Path("/numero_usuarios")
    @Produces(MediaType.APPLICATION_XML)
    public String numero_usuarios() {
        List lista = null;
        int numero = -1;
        try {
            lista = BD.Op_Usuarios.list();
            numero = lista.size();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            return Integer.toString(numero);
        }
    }

    @GET
    @Path("/primera_id_visitas")
    @Produces(MediaType.TEXT_PLAIN)
    public String get_primera_id_visitas() {
        return String.valueOf(BD.Op_Visitas.get_minima_id());
    }

    @GET
    @Path("/numero_empresas")
    @Produces(MediaType.APPLICATION_XML)
    public String numero_empresas() {
        List lista = null;
        int numero = -1;
        try {
            lista = BD.Op_Empresas.list();
            numero = lista.size();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            return Integer.toString(numero);
        }
    }

    @GET
    @Path("/wusers")
    @Produces({"application/xml"})
    public ResposeList buscar() {
        List lista = BD.Op_Usuarios.list();
        ResposeList rlist = new ResposeList();
        rlist.setList(lista);
        return rlist;
    }

    @GET
    @Path("/find_visita_by_id/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response find_visita_by_id(@PathParam("id") String id) {
        List<Visitas> v = new ArrayList<Visitas>();
        if (id.compareTo("") != 0) {
            v.add(Op_Visitas.find_by_id(id));
            if ((id != null) && (v != null)) {
                return Response.status(200).entity(v.toString()).build();
            }
        }
        String error = "No existe";
        return Response.status(200).entity(error).build();
    }

    @GET
    @Path("/find_visita_by_nombre_empresa/{nombre}")
    @Produces(MediaType.TEXT_HTML)
    public Response find_visita_by_nombre_empresa(@PathParam("nombre") String nombre) {
        List<Visitas> v = new ArrayList<Visitas>();
        if (nombre != null) {
            if (nombre.compareTo("") != 0) {
                v = Op_Visitas.find_by_nombre_empresa(nombre);
                if ((v != null) && (v.size() > 0)) {
                    return Response.status(200).entity(v.toString()).build();
                }
            }
        }
        String error = "No existe";
        return Response.status(200).entity(error).build();

    }

    @GET
    @Path("/userlogin/{login}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUSerbyLogin(@PathParam("login") String login) {
        if (login.compareTo("") != 0) {
            Usuarios emp = Op_Usuarios.find_by_login(login);
            if (emp != null) {
                return Response.status(200).entity(emp.toString()).build();
            }
        }
        String error = "No existe";
        return Response.status(200).entity(error).build();
    }

    @GET
    @Path("/userid/{userid}")
    @Produces(MediaType.TEXT_HTML)
    public Response getUser(@PathParam("userid") int userid) {

//      return BD.Op_Usuarios.find(userid);
        if (userid < 0) {
            return Response.noContent().build();
        }
        Usuarios emp = Op_Usuarios.find_by_id(userid);

//        GenericEntity<Usuarios> entity = new GenericEntity<Usuarios>(emp, Usuarios.class);
//        return Response.ok().entity(entity).build();
        return Response.status(200).entity(emp.toString()).build();
    }

    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(@FormParam("login") String login,
            @FormParam("nombre") String nombre,
            @FormParam("apellidos") String apellidos,
            @FormParam("fnac") Date fnac,
            @FormParam("pass") String pass,
            @FormParam("nif") String nif,
            @FormParam("esAdm") boolean esAdm,
            @Context HttpServletResponse servletResponse) throws IOException {

        Usuarios user = new Usuarios(login, nombre, apellidos, fnac, pass, nif, esAdm);
        int result = BD.Op_Usuarios.add(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }
//  

    @GET
    @Path("/updateuser/{dato}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public String UpdateUser(@PathParam("dato") String datos) {
        System.err.print(datos);
        try {
            String login = datos.split(",")[0];
            String nombre = datos.split(",")[1];
            String apellidos = datos.split(",")[2];

            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fnac = null;
            try {
                fnac = fecha.parse(datos.split(",")[3]);
            } catch (ParseException ex) {
                System.err.print("exception con la fecha " + ex);
            }

            String nif = datos.split(",")[4];
            boolean esAdm = false;
            try {

                esAdm = Boolean.parseBoolean(datos.split(",")[5]);
            } catch (Exception e) {
                System.err.print("exception con el booleano " + e);
            }

            Usuarios user = new Usuarios(login, nombre, apellidos, fnac, "", nif, esAdm);
            Usuarios old_user = BD.Op_Usuarios.find_by_login(login);
            int result = BD.Op_Usuarios.update(old_user.getId(), user);
            if (result == 1) {
                return SUCCESS_RESULT;
            }
        } catch (Exception e) {
            System.err.print("Excepcion con Servicio metodo UPdateuser" + e);
            return FAILURE_RESULT;
        }
        return FAILURE_RESULT;
    }
//

    @GET
    @Path("/addcoord/{coord}")
    //@Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public void AddCoordenada(@PathParam("coord") String paquete) {
        System.err.print(paquete);
        //Coordenadas(String glatitud, String glongitud, Date date, Integer usuariosId)
        try {
            String latitud = paquete.split(",")[0];
            String longitud = paquete.split(",")[1];
            Date fecha = null;
            try {
                long f_cadena = Long.parseLong(paquete.split(",")[2]);
                fecha = new Date(f_cadena);
            } catch (Exception ex) {
                fecha = new Date();
            }

//            SimpleDateFormat sfecha = new SimpleDateFormat("yyyy-MM-dd");
//            Date fecha = null;
//            try {
//                fecha = sfecha.parse(paquete.split(",")[2]);
//            } catch (ParseException ex) {
//                System.err.print("exception con la fecha en contructor coordenada " + ex);
//                fecha = null;
//            }
            int usuario_id = Integer.parseInt(paquete.split(",")[3]);

            Coordenadas C = new Coordenadas(latitud, longitud, fecha, usuario_id);
            BD.Op_Coordenadas.add(C);
        } catch (Exception e) {
            System.err.print("Excepcion con Servicio metodo Adduser" + e);
        }

    }

    @GET
    @Path("/adduser/{dato}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public String AddUser(@PathParam("dato") String datos) {
        System.err.print(datos);
        try {
            String login = datos.split(",")[0];
            String nombre = datos.split(",")[1];
            String apellidos = datos.split(",")[2];

            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fnac = null;
            try {
                fnac = fecha.parse(datos.split(",")[3]);
            } catch (ParseException ex) {
                System.err.print("exception con la fecha " + ex);
            }

            String nif = datos.split(",")[4];
            boolean esAdm = false;
            try {

                esAdm = Boolean.parseBoolean(datos.split(",")[5]);
            } catch (Exception e) {
                System.err.print("exception con el booleano " + e);
            }

            Usuarios user = new Usuarios(login, nombre, apellidos, fnac, "", nif, esAdm);

            int result = BD.Op_Usuarios.add(user);
            if (result == 1) {
                return SUCCESS_RESULT;
            }
        } catch (Exception e) {
            System.err.print("Excepcion con Servicio metodo Adduser" + e);
            return FAILURE_RESULT;
        }
        return FAILURE_RESULT;
    }

//    @DELETE
//    @Path("/users/{userid}")
//    @Produces(MediaType.APPLICATION_XML)
//    public String deleteUser(@PathParam("userid") int userid) {
//        int result = BD.Op_Usuarios.delete(userid);
//        if (result == 1) {
//            return SUCCESS_RESULT;
//        }
//        return FAILURE_RESULT;
//    }
//   @OPTIONS
//   @Path("/users")
//   @Produces(MediaType.APPLICATION_XML)
//   public String getSupportedOperations(){
//      return "<operations>GET, PUT, POST, DELETE</operations>";
//   }
}
