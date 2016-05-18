<%@page import="POJOS.Usuarios"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
    Boolean login = (Boolean) session.getAttribute("isLogin");
    String nombre = "";
    Usuarios usr = null;
    if ( login != null && login.booleanValue() ){
        Class.forName("com.mysql.jdbc.Driver");
        Connection cnt = DriverManager.getConnection(
                "jdbc:mysql://localhost/comerciales",
                "root", 
                "");
        Integer id = (Integer) session.getAttribute("userId");
       // usr = Usuarios.load(id.intValue(), cnt);
       // nombre = usr.nombre + " " + usr.apellidos;
    }
    else{
        response.sendRedirect("login.jsp");               
        return ;
    }
%>