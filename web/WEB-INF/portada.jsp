<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Portada</title>

    <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <link href="../css/portada.css" rel="stylesheet">

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
                <li class="active"><a href="portada.jsp">Portada</a></li>
                <li ><a href="editarUsuario.jsp">Mi perfil</a></li>
                <li ><a href="#">Mensajes</a></li>
                <li ><a href="editarPublicaciones.jsp">Editar publicaciones</a> </li>
            </ul>
            <form action="logout.do" method="post">
                <input type="submit" class="btn btn-danger navbar-btn pull-right" value="Logout" />
            </form>
            <a role="button" href="nuevaPublicacion.jsp" style="margin-right: 10px" type="button"
               class="btn btn-info navbar-btn pull-right">
                Nueva publicación
            </a>
        </div><!--/.nav-collapse -->
    </div>
</nav>



<div class="container">


    <div class="starter-template">
        <h1>Galer&iacute;a de im&aacute;genes</h1>
    </div>

    <div class="row ">
        <%-- ${fn:length(listaFotos)} --%>
        <c:forEach var="foto" items="${listaFotos}" varStatus="loop">
            <div class="col-md-4">
                <div class="thumbnail">
                    <div class="row">
                        <div class="col-xs-2">
                            <div class="circle-avatar" style="background-image: url('/img/fotos/profile.png')"></div>
                        </div>
                        <div class="col-xs-10 texto-portada">

                            <!--TODO La redirección a usuario hacerla por POST, no por GET-->
                            <a href="usuario.jsp?nick=${foto.nickname}">
                                <c:out value="${foto.nickname}"/>
                            </a>
                        </div>
                        <div class="col-xs-12 ">

                            <!--TODO La redirección a imagen hacerla por POST, no por GET-->
                            <a href="foto.jsp?idFoto=${foto.idFoto}">
                                <img src="./imagenes/${foto.foto}" alt="lights" class="portada" style="width:100%">
                                <div class="caption">
                                    <p class="titulo"><c:out value="${foto.titulo}"/></p>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</div><!-- /.container -->



<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-confirmation.js"></script>
</body>
</html>
