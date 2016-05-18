
<%@page import="BD.Op_Empresas"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="POJOS.Empresas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE HTML>
<html>
    <head>
        <title>
            
        </title>
    </head>
</html>
<%    String cif = request.getParameter("cif");
    String nombreEmpresa = request.getParameter("nombre");
    String cp = request.getParameter("cp");
    String direccion = request.getParameter("direccion");
    String provincia = request.getParameter("provincia");
    String poblacion = request.getParameter("poblacion");
    String contacto = request.getParameter("contacto");
    int tlf, comercial;
    try {
        tlf = Integer.parseInt(request.getParameter("tlf"));
        comercial = Integer.parseInt(request.getParameter("comercial"));
    } catch (Exception e) {
        tlf = 0;
        comercial = -1;
    }
    
    java.util.Date utilDate = new java.util.Date(); //fecha actual
    long lnMilisegundos = utilDate.getTime();
    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
    
    Empresas e = new Empresas(cif,nombreEmpresa, direccion, provincia, poblacion, cp, tlf, comercial, sqlTimestamp,contacto,null);

    try 
    {
        Op_Empresas.add(e);
        response.sendRedirect("../empresas.jsp");
    } 
    catch (Exception err) 
    {
        session.setAttribute("error", err);
        session.setAttribute("empresa", e);
        response.sendRedirect("../nueva-empresa.jsp?error=1");
    } 
    

%>
