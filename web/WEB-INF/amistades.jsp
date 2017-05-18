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

    <title>Amistades</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="../css/starter-template.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>


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
                <li ><a href="#">Mensajes</a></li>
                <li class="active"><a href="editarPublicaciones.jsp">Editar publicaciones</a> </li>
            </ul>
            <form action="logout.do" method="post">
                <input type="submit" class="btn btn-danger navbar-btn pull-right" value="Logout" />
            </form>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <h1>Usuarios y amistades</h1>
    </div>

    <div class="row">
        <div class="col-md-offset-1 col-xs-3">
            <div class="list-group">
                <a href="#" class="list-group-item active">Amistades</a>
                <c:forEach var="amigo" items="${listaAmigos}" varStatus="loop">
                    <a href="usuario.jsp?nick=${amigo.nick}"
                       class="list-group-item list-group-item-action">${amigo.nick}</a>
                </c:forEach>

            </div>
        </div>
        <div class="col-xs-3">
            <div class="list-group">
                <a href="#" class="list-group-item active">Otros usuarios</a>
                <c:forEach var="noAmigo" items="${listaNoAmigos}" varStatus="loop">
                    <a href="usuario.jsp?nick=${noAmigo.nick}"
                       class="list-group-item list-group-item-action">${noAmigo.nick}</a>
                </c:forEach>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="list-group">
                <!-- TODO, mostrar las peticiones de amistad que tiene el usuario, tabla relaciones -->
                <a href="#" class="list-group-item active">Peticiones de amistad</a>
            </div>
        </div>
    </div>



</div><!-- /.container -->



<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-confirmation.js"></script>



</html>
