<%@page import="java.util.ListIterator"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="POJOS.Usuarios"%>
<%@page import="BD.Op_Usuarios"%>
<%@page import="java.util.List"%>
<%@page import="BD.Op_Usuarios"%>
<%@page import="POJOS.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Usuarios e = null;
    boolean inicializa = false;
    boolean error=false;
    String mensaje_Error="";
    String id_usu=request.getParameter("update");    

    if (id_usu!=null)
    {          
        try
        {
           inicializa=true;
           int id=Integer.parseInt(id_usu);
           e=Op_Usuarios.find(id);
        }
        catch(Exception ex)
        {
            inicializa=false;
            error=true;
            mensaje_Error=ex.getMessage().toString();
            
        }
    }
    //int edit_id = -1;

    
    //inicializa = true;
       
    //e = (Usuarios) session.getAttribute("usu");
 %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comerciales - Alta Nueva usu.</title>
        <link rel="shortcut icon" href="./favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/estilos.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/forms.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/iconos.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/posicionamiento.css" />
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="js/additional-methods.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#altausu').submit(function () {
                    if (confirm("¿Estas Seguro?"))
                        return true;
                    else
                        return false;
                });
                $('#altausu').validate({
                    rules: {
                        "nombre": {
                            "required": true,
                            "maxlength": 80
                        },
                        "direccion": {
                            "required": true,
                            "maxlength": 80
                        },
                        "provincia": {
                            "required": true,
                            "maxlength": 30
                        },
                        "poblacion": {
                            "required": true,
                            "maxlength": 30
                        },
                        "cp": {
                            "required": true,
                            "digits": true,
                            "minlength": 5,
                            "maxlength": 5
                        },
                        "tlf": {
                            "required": true,
                            "digits": true,
                            "minlength": 9,
                            "maxlength": 9
                        },
                        "contacto": {maxlength: 50}
                    },
                    messages: {
                        "nombre": {
                            "required": "Debe de escribir el nombre de la usu",
                            "maxlength": "El numero maximo de caracteres es 80"
                        },
                        "direccion": {
                            "required": "Debe escribir una direccion",
                            "maxlength": "Tamaño maximo de caracteres es 80."
                        },
                        "provincia": {
                            "required": "Debe de escribir la Provincia",
                            "maxlength": 30
                        },
                        "poblacion": {
                            "required": "Debe de escribir la Población",
                            "maxlength": 30
                        },
                        "cp": {
                            "required": "Debe de escribir un Codigo Postal",
                            "digits": "Solo se permiten numeros.",
                            "minlength": "Codigo postal no valido",
                            "maxlength": "Codigo postal no valido"
                        },
                        "tlf": {
                            "required": "Este campo es obligatorio",
                            "digits": "Telefono incorrecto",
                            "minlength": "Telefono incorrecto",
                            "maxlength": "Telefono incorrecto"
                        },
                        "contacto": {maxlength: "Numero maximo de caracteres es de 50", }
                    }
                });
            });
            function EnviarFormulario() {
                if ($("#altausu").valid())
                    document.forms[0].submit();
            }
        </script>

    </head>
    <body>
        <div id="pagina">
            <jsp:directive.include file="header.jsp" />
            <div id="toolbar">      
                <div class="m_tootlbar">                    
                    <div id="toolbar_botones">
                        <ul>
                            <li>
                                <a href="#" onclick="EnviarFormulario();">
                                    <span class="icono-32-aceptar"></span>
                                    Aceptar
                                </a>
                            </li>
                            <li>
                                <a href="usus.jsp">
                                    <span class="icono-32-cancelar"></span>
                                    Cancelar
                                </a>                                
                            </li>
                        </ul>
                        <div class="limpiar"></div>
                    </div>
                    <div class="toolbar_titulo icono-48-usus">
                        <h2>Nueva Empresa</h2>
                    </div>                    
                </div>
            </div>
            <div id="content">
                <%if (error ) {%>
                <div class="marcoError">
                    <p><%=mensaje_Error%></p>
                </div>
                <% }%>
                <form id="altausu" method="POST" action="<% out.print("acciones/altausu.action.jsp"); 
                      %>">
                    <% if (e != null && e.getId() != -1) {%>
                    <input type="hidden" name="id" value="<%=e.getId()%>" />
                    <% }%>
                    <table class="altaEmpresa">
                        <tr>
                            <td width="130"><label>C.I.F.:</label></td>
                            <td><input type="text" name="NIF" value="<%=inicializa ? e.getNif() : ""%>" /></td>
                        </tr>
                        <tr>
                            <td><label>Nombre:</label></td>
                            <td><input type="text" name="nombre" value="<%=inicializa ? e.getNombre() : ""%>" /></td>
                        </tr>
                        <tr>
                            <td><label>Direccion:</label></td>
                            <td><input type="text" name="Apellidos" value="<%=inicializa ? e.getApellidos() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Provincia:</label></td>
                            <td><input type="text" name="Fecha Nacimiento" value="<%=inicializa ? e.getFnac() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Población:</label></td>
                            <td><input type="text" name="Permisos de Adm" value="<%=inicializa ? e.isEsAdm() : ""%>"/></td>
                        </tr>
                        
                       
                        <tr>
                            <td><label>Fecha Ultimo Acceso:</label></td>
                            <td><input type="text" name="falta" readonly="readonly" 
                                       value="<%
                                           SimpleDateFormat fecha = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                                   out.print(fecha.format(e.getFu()));
                                       %>"/>
                                
                            </td>
                        </tr>                                                            
                    </table>
                </form>
            </div>
        </div>
        <jsp:directive.include file="pie.jsp" />

    </body>
</html>
