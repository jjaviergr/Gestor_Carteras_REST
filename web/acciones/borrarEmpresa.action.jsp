<%@page import="BD.Op_Empresas"%>
<%@page import="POJOS.Empresas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int idEmpresa;
    Empresas emp;
    try {
        idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
        Op_Empresas.delete(idEmpresa);
    } catch (Exception e) {
        idEmpresa = -1;
        emp = null;
    }
    response.sendRedirect("../empresas.jsp");
    
%>
