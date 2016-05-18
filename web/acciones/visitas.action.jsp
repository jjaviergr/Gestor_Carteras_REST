<%-- 
    Document   : visitas.action
    Created on : 16-abr-2016, 16:56:16
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object o=session.getAttribute("uid");
    if ( o instanceof Integer)
    {
        Integer i=(Integer)o;
        String uid=o.toString();
        response.sendRedirect("../visitas.jsp?id="+uid);  
    }
    
    
%>