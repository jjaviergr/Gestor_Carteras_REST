
<%@page import="BD.Op_Empresas"%>
<%@page import="POJOS.Empresas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>

<%@include  file="../check.jsp" %> 

<%
    String cif = request.getParameter("cif");
    String nombreEmpresa = request.getParameter("nombre");
    String cp = request.getParameter("cp");
    String direccion = request.getParameter("direccion");
    String provincia = request.getParameter("provincia");
    String poblacion = request.getParameter("poblacion");
    String contacto = request.getParameter("contacto");
    int tlf,comercial,id;
    try{
        tlf = Integer.parseInt(request.getParameter("tlf"));
        comercial = Integer.parseInt(request.getParameter("comercial"));        
        id = Integer.parseInt(request.getParameter("id"));
    }
    catch(Exception e){
        tlf = 0;
        comercial = -1;
        id = -1;
    }    
    Empresas e=Op_Empresas.find(cif);
    
    
    e.setCif(cif);
    e.setComercial(comercial);
    e.setContacto(contacto);
    e.setCp(cp);
    e.setDireccion(direccion);
    e.setNombre(nombre);
    e.setPoblacion(poblacion);
    e.setProvincia(provincia);
    e.setTlf(tlf);
    try
    {
        Op_Empresas.update(e);
        response.sendRedirect("../empresas.jsp");
    }  
    catch( Exception err)
    {
        out.print(err);
        session.setAttribute("error", err);
        session.setAttribute("empresa", e);
       // response.sendRedirect("../nueva-empresa.jsp?error=1,edit="+id);
    }
    finally
    {
        
    }
    
    
%>
