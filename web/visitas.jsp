<%@page import="java.text.SimpleDateFormat"%>
<%@page import="POJOS.Visitas"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="BD.Op_Visitas"%>
<%
    
    List listado = Op_Visitas.find(1);
    
    
    int n_empresas = listado.size();
    int nPaginas = (n_empresas / 10);
    if (n_empresas % 10 != 0) 
    {
        nPaginas++;
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comerciales - Gestión de Visitas.</title>
        <link rel="shortcut icon" href="./favicon.ico" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/estilos.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/forms.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/iconos.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/posicionamiento.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/nyroModal.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui/jquery-ui-1.8.21.custom.css" />
        <script  src="js/jquery-1.7.2.min.js"></script>        
        <script  src="js/jquery-ui-1.8.21.custom.min.js"></script>
        <script  src="js/jquery.nyroModal.custom.min.js"></script>
        <script  src="js/jquery.nyroModal-ie6.min.js"></script>
        <script >
            $(document).ready(function () {
                $(".nyroModal").nyroModal();
                $("input:submit").button();
                $("#frmBuscar").dialog({autoOpen: false, modal: true, resizable: false});
            <%
                   if (request.getParameter("error") != null
                           && request.getParameter("error").equals("4")) { %>
                $("#error4").dialog({show: "Scale", hide: "Flod", autoOpen: true, modal: true, width: 350, height: 'auto', resizable: false,
                    buttons: {"Aceptar":
                                function () {
                                    $(this).dialog('close');
                                }
                    }
                });
            <%}%>
            });
            function Confirmar() {
                return confirm('¿Estas seguro que deseas borrar la visita?');
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
                                <a href="nueva-visita.jsp">
                                    <span class="icono-32-add"></span>
                                    Añadir
                                </a>
                            </li>
                            <li>
                                <a href="#" onclick="$('#frmBuscar').dialog('open');">
                                    <span class="icono-32-buscar"></span>
                                    Buscar
                                </a>
                            </li>                            
                            <li>
                                <a href="menu.jsp">
                                    <span class="icono-32-volver"></span>
                                    Volver
                                </a>                                
                            </li>
                        </ul>
                        <div class="limpiar"></div>
                    </div>
                    <div class="toolbar_titulo icono-48-empresas">
                        <h2>Visitas</h2>
                    </div>                    
                </div>
            </div>
            <div id="content">
                <table class="listado">
                    <thead>
                        <tr>
                            <th style="display:none">Id</th>
                            <th width="80p">Empresa</th>
                            <th>Nombre de Comercial</th>
                            <th width="80p">Motivo</th>
                            <th width="200p">Resultado</th>
                            <th width="160p">&nbsp;</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <% Iterator<Visitas> it=listado.iterator();
                        while (it.hasNext())
                        {
                                Visitas v=it.next();
                        %>

                        <tr>
                            <td style="display:none"><%=v.getId()%></td>
                            <td><%=v.getEmpresas().getCif()%></td>
                            <td><%=v.getUsuarios().getNombre()%></td>
                            <td align="center"><%=v.getMotivo()%></td>
                            <td><%=v.getResultado()%></td>
                            <td align="center">
                                <%
                                   SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
                                   out.print(fecha.format(v.getFecha().getTime()));
                                %>
                            </td>
                            <td align="center">
                                <a href="nueva-visita.jsp?update=<%=v.getId()%>">
                                    <img src="img/icono16/editar.png" alt="editar" title="Editar visita" />
                                </a>
                                <a href="acciones/borrarVisita.action.jsp?idEmpresa=<%=v.getId()%>" onclick="return Confirmar();">
                                    <img src="img/icono16/borrar.png" alt="editar" title="Borrar la visita" />
                                </a>
                                <a class="nyroModal" href="verEmpresa.jsp?idEmpresa=<%=v.getId()%>">
                                    <img src="img/icono16/ver.png" alt="editar" title="Ver ficha de empresa" />
                                </a>
                            </td>
                        </tr>
                        <% }
                        %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="2">
                                <h5>NºVisitas: <%=n_empresas%></h5>
                            </td>
                            <td colspan="5">
                                <ul class="paginar">

                                    <% for (int i = nPaginas - 1; i >= 0; i--) {%>
                                    <li><a href="visitas.jsp?pag=<%=i%>"><%=(i + 1)%></a></li>
                                        <% } %>
                                </ul>                                
                                <span class="limpiar"></span>
                            </td>
                        </tr>
                    </tfoot>
                </table>
                <div class="limpiar"></div>
            </div>
        </div>
        <jsp:directive.include file="pie.jsp" />
        <div id="frmBuscar" title="Buscar Empresa">
            <form method="POST" action="acciones/buscarEmpresa.action.jsp">
                <table style="padding: 20px">
                    <tr>
                        <td width="100">C.I.F.:</td>
                        <td width="400"><input type="text" name="cif" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="Buscar Empresa"/>
                        </td>
                    </tr>

                </table>
            </form>
        </div>
        <%
    if (request.getParameter("error") != null
            && request.getParameter("error").equals("4")) { %>
        <div id="error4" title="Error al buscar">
            <p class="ui-state-error ui-corner-all" 
               style="padding: 0.7em; font-size: 1.1em; text-align: center">
                No existe ninguna empresa con el C.I.F. facilitado
            </p>    
        </div>
        <% }%>
    </body>
</html>
