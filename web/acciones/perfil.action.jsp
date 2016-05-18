<%-- 
    Document   : menu.action
    Created on : 16-abr-2016, 12:01:13
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object o=session.getAttribute("uid");
    if ( o instanceof Integer)
    {
        Integer i=(Integer)o;
        String uid=o.toString();
        response.sendRedirect("../perfil.jsp?id="+uid);  
    }
    
    
%>
