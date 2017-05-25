<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Editar usuario</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="../css/starter-template.css" rel="stylesheet">

    <link href="../css/editarPublicaciones.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<%
    boolean hayError = false;
    String mensajeError = "";

    if (request.getAttribute("error") != null) {
        mensajeError = (String) request.getAttribute("nick");
        hayError = true;
    }
%>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Navegación</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="portada.jsp">Pictagram</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li ><a href="portada.jsp">Portada</a></li>
                <li ><a href="editarUsuario.jsp">Mi perfil</a></li>
                <li class="active"><a href="editarPublicaciones.jsp">Editar publicaciones</a> </li>
                <li ><a href="amistades.jsp">Usuarios y amistades</a> </li>
            </ul>
            <form action="logout.do" method="post">
                <input type="submit" class="btn btn-danger navbar-btn pull-right" value="Logout" />
            </form>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <h1>Editar publicaciones</h1>
    </div>

    <% if(hayError) {%>
    <div class="alert alert-danger">
        <strong>Error - </strong>No se ha podido eliminar la publicación.
    </div>
    <%}%>

    <table class="table table-hover">
        <thead>
            <tr>
                <th>Título</th>
                <th>Descripción</th>
                <th>Imagen</th>
                <th>Edición</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="foto" items="${listaFotos}" varStatus="loop">
                <tr>
                    <td>${foto.titulo}</td>
                    <td>${foto.descripcion}</td>
                    <td class="smallImage"><img src="./imagenes/${foto.foto}" alt="lights" class="portada img-thumbnail "
                             style="width:100%"></td>
                    <td>

                        <form action="editarPublicacion.do" method="get">
                            <label for="editar${foto.idFoto}" class="btn"><i class="glyphicon glyphicon-pencil"
                                                               style="color:#2e6da4"></i></label>
                            <input id="editar${foto.idFoto}" type="submit" name="idPub" value="${foto.idFoto}"
                                   class="hidden" />


                        </form>

                        <form action="eliminarPublicacion.do" method="post">
                            <label for="eliminar${foto.idFoto}" class="btn"><i class="glyphicon glyphicon-trash"
                                style="color:darkred"></i></label>
                            <input id="eliminar${foto.idFoto}" type="submit" name="botonEliminar"
                                   value="${foto.idFoto}"
                                    class="hidden" />
                        </form>


                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>


</div><!-- /.container -->



<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-confirmation.js"></script>



</html>
