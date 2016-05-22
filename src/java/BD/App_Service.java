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
import POJOS.Empresas;
import POJOS.Usuarios;
import es.cartera.Md5;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public String Update_Empresa(@PathParam("paquete") String paquete)
    {
        String exito="fracaso";
         String[] datos=paquete.split(",");
         String CIF=paquete.split(",")[0];
         Empresas em=Op_Empresas.find(CIF);
         if (em!=null)
         {
            int telefono=-1;
            try
            {
                telefono=Integer.parseInt(datos[6]);
            }
            catch(Exception e)
            {
                  System.err.print("Exception con el telefono en servicio Updatempresa " + e);
            }
            int comercial=-1;
            try
            {
                Usuarios u=Op_Usuarios.find_by_id(Integer.parseInt(datos[7]));
                if (u!=null)
                {
                    comercial=u.getId();
                }
            }
            catch(Exception e)
            {
                 System.err.print("Exception con el comercialen servicio UpdateEmpresa  "+e);
            }
            
            Empresas e=new Empresas(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],telefono,comercial,datos[9]);
            e.setId(em.getId());
            e.setFechaAlta(em.getFechaAlta());
           
          
            Op_Empresas.update(e.getId(),e);
          
            
            exito="ok";
         }
        
        return(exito);
            
    }
    
    @GET
    @Path("/borrarempresabycif/{cif}")
    public void borrarEmpresabyCIF(@PathParam("cif") String CIF)
    {
        Empresas e=Op_Empresas.find(CIF);
        int id=e.getId();
        if (id!=-1)
           Op_Empresas.delete(id);
    }
    
    
    @GET
    @Path("/empresabycif/{cif}")
    @Produces(MediaType.TEXT_HTML)
    public String EmpresaByCIF(@PathParam("cif") String CIF)
    {
        Empresas e=BD.Op_Empresas.find(CIF);
        return (e.toString());
    }
    
    @GET
    @Path("/addempresa/{paquete}")
    @Produces(MediaType.TEXT_HTML)
    public String AddEmpresa(@PathParam("paquete") String paquete)
    {
            String[] datos=paquete.split(",");
                
            int telefono=-1;
            try
            {
                telefono=Integer.parseInt(datos[7]);
            }
            catch(Exception e)
            {
                  System.err.print("Exception con el telefono en servicio AddEmpresa " + e);
            }
            int id=-1;
            try{
            id=Integer.parseInt(datos[7]);}
            catch (Exception e)
            {
                System.err.print("Exception con id del comercial " + e);
            }
                     
            Empresas e=new Empresas(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],telefono,id,datos[9]);
            
            String mensaje="";
            try
            {
                Op_Empresas.add(e);
                mensaje="ok";
            }
            catch(Exception ex)
            {
                mensaje="problema al insertar empresa en servicio "+ex;
                System.err.print("problema al insertar empresa en servicio "+ex);
            }
            finally
            {
                return mensaje;
            }
    }
    
    @GET
    @Path("/valida/{login},{pass}")
    @Produces(MediaType.TEXT_HTML)
    public String UserIsValid(@PathParam("login") String login, @PathParam("pass") String pass) {
        String respuesta = "";
        if ("".equals(login)) {
            respuesta = "1";
        } else if ("".equals(pass)) {
            respuesta = "2";
        } else if (!Op_Usuarios.validar(login, Md5.getStringMessageDigest(pass, Md5.MD5))) {
            respuesta = "3";
        } else {
            POJOS.Usuarios usr = BD.Op_Usuarios.find_by_login(login);
            if (!Op_Usuarios.esAdm(login)) {
                respuesta = "menu.html?id=" + usr.getId();

            } else {
                respuesta = "menu_adm.html?id=" + usr.getId();
            }

        }

        return respuesta;
    }

    @GET
    @Path("/nombre/{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
        try 
        {
            List lista = BD.Op_Usuarios.list();
            rlist = new ResposeList();
            rlist.setList(lista);
        }

        catch (Exception e) 
        {
           System.err.print("Excepcion en servicio con lista comerciles"+e.getMessage().toString());
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
    @Path("/userlogin/{login}")
    @Produces(MediaType.TEXT_HTML)
    public Response getUSerbyId(@PathParam("login") String login) {
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
    @Path("/wusers/{userid}")
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
             Usuarios old_user=BD.Op_Usuarios.find_by_login(login);
            int result = BD.Op_Usuarios.update(old_user.getId(), user);
            if (result == 1) 
            {
                return SUCCESS_RESULT;
            }
        } catch (Exception e) 
        {
            System.err.print("Excepcion con Servicio metodo UPdateuser"+e);
            return FAILURE_RESULT;
        }
        return FAILURE_RESULT;
    }
//
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
            if (result == 1) 
            {
                return SUCCESS_RESULT;
            }
        } catch (Exception e) 
        {
            System.err.print("Excepcion con Servicio metodo Adduser"+e);
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
