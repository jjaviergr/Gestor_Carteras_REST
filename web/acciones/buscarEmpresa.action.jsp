<%@page import="POJOS.Empresas"%>
<%@page import="BD.Op_Empresas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String cif = request.getParameter("cif");
   
    try{
        
        Empresas emp=Op_Empresas.find(cif);
        response.sendRedirect("../nueva-empresa.jsp?update="+ emp.getId());
    }
    catch(Exception e){
        response.sendRedirect("../empresas.jsp?error=4");
    }
%>