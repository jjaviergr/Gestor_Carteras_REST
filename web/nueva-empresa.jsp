<%@page import="java.util.ListIterator"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="POJOS.Usuarios"%>
<%@page import="BD.Op_Usuarios"%>
<%@page import="java.util.List"%>
<%@page import="BD.Op_Empresas"%>
<%@page import="POJOS.Empresas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Empresas e = null;
    boolean inicializa = false;
    boolean error=false;
    String mensaje_Error="";
    String id_empresa=request.getParameter("update");    

    if (id_empresa!=null)
    {          
        try
        {
           inicializa=true;
           int id=Integer.parseInt(id_empresa);
           e=Op_Empresas.find(id);
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
       
    //e = (Empresas) session.getAttribute("empresa");
        
   

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comerciales - Alta Nueva empresa.</title>
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
                $('#altaempresa').submit(function () {
                    if (confirm("¿Estas Seguro?"))
                        return true;
                    else
                        return false;
                });
                $('#altaempresa').validate({
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
                            "required": "Debe de escribir el nombre de la empresa",
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
                if ($("#altaempresa").valid())
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
                                <a href="empresas.jsp">
                                    <span class="icono-32-cancelar"></span>
                                    Cancelar
                                </a>                                
                            </li>
                        </ul>
                        <div class="limpiar"></div>
                    </div>
                    <div class="toolbar_titulo icono-48-empresas">
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
                <form id="altaempresa" method="POST" action="<% if (!inicializa)
                                                                   out.print("acciones/altaempresa.action.jsp"); 
                else out.print("acciones/editarempresa.action.jsp");
                      %>">
                    <% if (e != null && e.getId() != -1) {%>
                    <input type="hidden" name="id" value="<%=e.getId()%>" />
                    <% }%>
                    <table class="altaEmpresa">
                        <tr>
                            <td width="130"><label>C.I.F.:</label></td>
                            <td><input type="text" name="cif" value="<%=inicializa ? e.getCif() : ""%>" /></td>
                        </tr>
                        <tr>
                            <td><label>Nombre:</label></td>
                            <td><input type="text" name="nombre" value="<%=inicializa ? e.getNombre() : ""%>" /></td>
                        </tr>
                        <tr>
                            <td><label>Direccion:</label></td>
                            <td><input type="text" name="direccion" value="<%=inicializa ? e.getDireccion() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Provincia:</label></td>
                            <td><input type="text" name="provincia" value="<%=inicializa ? e.getProvincia() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Población:</label></td>
                            <td><input type="text" name="poblacion" value="<%=inicializa ? e.getPoblacion() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Cod.Postal:</label></td>
                            <td><input type="text" name="cp" value="<%=inicializa ? e.getCp() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Telefono:</label></td>
                            <td><input type="text" name="tlf" value="<%=inicializa ? e.getTlf() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Per.Contacto:</label></td>
                            <td><input type="text" name="contacto" value="<%=inicializa ? e.getContacto() : ""%>"/></td>
                        </tr>
                        <tr>
                            <td><label>Comercial :</label></td>
                            <td>
                                <select name="comercial">
                                    <option value="-1">----Seleccione Comercial------</option>
                                   
                                    <%
                                        int i=0;
                                        List lista=Op_Usuarios.list();
                                        ListIterator iter = lista.listIterator(lista.size());
                                        while (iter.hasPrevious())                                        
                                        {
                                            Usuarios u=(Usuarios)lista.get(i);
                                           
                                            iter.previous();
                                           i++;
                                    %>
                                    <option value="<%=u.getId()%>">
                                        <%=u.getApellidos()+" "%>
                                        <%=u.getNombre()%>
                                        
                                    </option>
                                    <%
                                        }
                                    %>
                                    
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Fecha Alta:</label></td>
                            <td><input type="text" name="falta" readonly="readonly" 
                                       value="<%
                                           SimpleDateFormat fecha = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                                   out.print(fecha.format(new Date()));
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
