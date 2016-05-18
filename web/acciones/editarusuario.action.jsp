<%@page import="java.util.Date"%>
<%@page import="BD.Op_Usuarios"%>
<%@page import="POJOS.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>

<%@include  file="../check.jsp" %> 

<%
    String nif = request.getParameter("nif");
    String nomb = request.getParameter("nombre");
    String apellidos = request.getParameter("apellidos");
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
    
    int id=-1;
    try{      
        
        id = Integer.parseInt(request.getParameter("id"));
    }
    catch(Exception e){        
        id = -1;
    }    
    Usuarios e=Op_Usuarios.find(id);
    
    e.setNif(nif);
    e.setNombre(nomb);
    e.setApellidos(apellidos);
    e.setFnac(fnac);
    e.setFu(fu);
    
    
    
    try
    {
        Op_Usuarios.update(id,e);
        response.sendRedirect("../usuarios.jsp");
    }  
    catch( Exception err)
    {
        out.print(err);
        session.setAttribute("error", err);
        session.setAttribute("usuario", e);
       // response.sendRedirect("../nueva-usuario.jsp?error=1,edit="+id);
    }
    finally
    {
        
    }
    
    
%>
