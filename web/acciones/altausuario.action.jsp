<%@page import="BD.Op_Usuarios"%>
<%@page import="java.util.Set"%>
<%@page import="BD.Op_Empresas"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="POJOS.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE HTML>
<html>
    <head>
        <title>
            
        </title>
    </head>
</html>
<%   
    String isupdate=request.getParameter("updateusu");
    out.print(isupdate+"-------");
    String login = request.getParameter("login");
    String nombre = request.getParameter("nombre");
    String apellidos=request.getParameter("apellidos");
    
    Date fnac=new Date();
    try
    {
        fnac=new java.sql.Timestamp(Long.parseLong(request.getParameter("fnac")));
    }
    catch(Exception e)
    {
        
    }
    
    Date fu=new Date();    
    try
    {
        fu=new java.sql.Timestamp(Long.parseLong(request.getParameter("fu")));
    }
    catch(Exception e)
    {
        
    }
    String pass=request.getParameter("pass");
    String nif=request.getParameter("nif");
    boolean esAdm=Boolean.parseBoolean(request.getParameter("esAdm"));
    Set vis=null;
    try
    {
       vis=Op_Usuarios.extrae_visitas("login");
    }
    catch(Exception e)
    {
        
    }
    
    
    
    //java.util.Date utilDate = new java.util.Date(); //fecha actual
    //long lnMilisegundos = utilDate.getTime();
    //java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
    //String login, String nombre, String apellidos, Date fnac, Date fu, String pass, String nif, boolean esAdm, Set visitases
    Usuarios e = new Usuarios( login,  nombre,  apellidos,  fnac,  fu,  pass,  nif,  esAdm,  vis);

    try 
    {
        if (isupdate.compareTo("true")!=1)
        {
            out.print("update");
           Op_Usuarios.add(e);
        }
        else
        {
            out.print("noupdate");
           Op_Usuarios.update(Op_Usuarios.find(login).getId().toString(), e);
        }
        
        response.sendRedirect("../usuarios.jsp");
    } 
    catch (Exception err) 
    {
        out.print(err.toString());
        session.setAttribute("error", err);
        session.setAttribute("usuario", e);
        //response.sendRedirect("../nuevo-usuario.jsp?error=1");
    } 
    

%>
