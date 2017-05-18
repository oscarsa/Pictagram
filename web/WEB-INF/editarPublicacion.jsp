<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

    <title>Editar usuario</title>

    <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/starter-template.css" rel="stylesheet">

    <link href="../css/portada.css" rel="stylesheet">
    <link href="../css/editarPublicaciones.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

    <%
        //Valores del usuario a editar
        String titulo = "";
        String descripcion = "";

        //Indican si la modificación de titulo ha ido bien o mal
        boolean tituloModificadoOk = false;
        boolean tituloModificadoFail = false;

        //Indican si la modificación de la descripcion ha ido bien o mal
        boolean descripcionModificadaOk = false;
        boolean descripcionModificadaFail = false;

        //Mensajes para dar feedback al usuario
        String mensajeTituloOk = "<span class='help-block'>El título se ha modificado correctamente</span>";
        String mensajeTituloFail =
                "<span class='help-block'>El título no se ha podido modificar</span>";
        String resaltarTituloOk = "has-success has-feedback";
        String resaltarTituloFail = "has-error has-feedback";
        String mensajeTitulo="";
        String resaltarTitulo="";

        String mensajeDescripcionOk =
        "<span class='help-block'>La descripción se ha modificado correctamente</span>";
        String mensajeDescripcionFail =
                "<span class='help-block'>La descripción no se ha podido modificar</span>";
        String resaltarDescripcionOk = "has-success has-feedback";
        String resaltarDescripcionFail = "has-error has-feedback";
        String mensajeDescripcion="";
        String resaltarDescripcion="";

        String rutaFoto = (String) request.getAttribute("rutaFoto");
        String idFoto = (String) request.getAttribute("idFoto");


        if (request.getAttribute("titulo") != null) {
            titulo = (String) request.getAttribute("titulo");
        }

        if (request.getAttribute("descripcion") != null) {
            descripcion = (String) request.getAttribute("descripcion");
        }


        if (request.getAttribute("tituloModificadoOk")!=null) {
            tituloModificadoOk = true;
        } else if (request.getAttribute("tituloModificadoFail")!=null) {
            tituloModificadoFail = true;
        }

        if (request.getAttribute("descripcionModificadaOk")!=null) {
            descripcionModificadaOk = true;
        } else if (request.getAttribute("descripcionModificadaFail")!=null) {
            descripcionModificadaFail = true;
        }

        String tituloFinal = "";
        String descripcionFinal = "";


        if(tituloModificadoFail) {
            mensajeTitulo=mensajeTituloFail;
            resaltarTitulo=resaltarTituloFail;
            tituloFinal = " value='" + titulo + "'";
        } else {
            tituloFinal = "value='" + titulo + "'";
        }

        if(tituloModificadoOk) {
            mensajeTitulo=mensajeTituloOk;
            resaltarTitulo=resaltarTituloOk;
        }

        if(descripcionModificadaFail) {
            mensajeDescripcion=mensajeDescripcionFail;
            resaltarDescripcion=resaltarDescripcionFail;
            descripcionFinal = descripcion;
        } else {
            descripcionFinal = descripcion;
        }

        if(descripcionModificadaOk) {
            mensajeDescripcion=mensajeDescripcionOk;
            resaltarDescripcion=resaltarDescripcionOk;
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
                <li ><a href="#">Mensajes</a></li>
                <li ><a href="editarPublicaciones.jsp">Editar publicaciones</a> </li>
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
        <h1>Editar publicación</h1>
    </div>

    <div class="row">

        <div class="col-sm-4"></div>
        <div class="mediumImage col-sm-4">
            <img src="./imagenes/<%=rutaFoto%>" alt="lights" class="portada" style="width:100%">
        </div>

    </div>

    <form class="form-horizontal" method="post" action="editarPublicacion.do">

        <div class="form-group <%=resaltarTitulo%>">

            <label class="control-label col-sm-4" for="titulo">Título:</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="titulo"  name="titulo"
                <%=tituloFinal%> >
                <%=mensajeTitulo%>
            </div>
        </div>
        <div class="form-group <%=resaltarDescripcion%>">
            <label class="control-label col-sm-4" for="descripcion">Contrase&ntilde;a:</label>
            <div class="col-sm-6">
                <textarea class="form-control" rows="3"  class="form-control" id="descripcion"
                          name="descripcion"><%= descripcionFinal%></textarea>
                <%=mensajeDescripcion%>
            </div>
        </div>
        <input class="hidden" id="idPub"  name="idPub" value="<%=idFoto%>">
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-6">
                <button type="submit" name="modificar" class="btn btn-info">Modificar publicación</button>
            </div>
        </div>
    </form>
</div><!-- /.container -->



<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-confirmation.js"></script>



</html>
