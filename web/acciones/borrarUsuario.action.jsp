<%@page import="BD.Op_Usuarios"%>
<%@page import="POJOS.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int id;
    
    try 
    {
        id = Integer.parseInt(request.getParameter("id"));
        Op_Usuarios.delete(id);
    } 
    catch (Exception e) 
    {
        out.print(e);
        id = -1;
       
    }
    response.sendRedirect("../usuarios.jsp");
    
%>
