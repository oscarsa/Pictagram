<%@ page import="java.util.HashMap" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>


    <%


  //Para añadir los mensajes de error
  String tituloErrorMessage="";
  String descripcionErrorMessage="";
  String imagenErrorMessage="";

  //Para añadir los valores correctos
  String tituloValido="";
  String descripcionValida="";
  String imagenValida="";

  //Para reescribir los parametros correctos
  String tituloValor="";
  String descripcionValor="";
  String imagenValor="";

  //Para indicar si hay error
  boolean tituloHayError= false;
  boolean descripcionHayError= false;
  boolean imagenHayError= false;

  //Diccionarios para los errores
  HashMap<String, String> errores = (HashMap) request.getAttribute("errores");
  HashMap<String, String> correcto = (HashMap) request.getAttribute("correcto");

  String errorHeader = "<span class='help-block'>";
  String errorFooter = "</span>";

  String resaltarOk = "class=\"form-group has-success has-feedback\"";
  String resaltarFail = "class=\"form-group has-error has-feedback\"";
  String sinResaltar = "class=\"form-group\"";

  String resaltarTitulo = "";
  String resaltarDescripcion = "";
  String resaltarImagen = "";

  if(errores != null){

    if(errores.containsKey("titulo")){
      tituloErrorMessage = errorHeader + errores.get("titulo") + errorFooter;
      tituloHayError = true;
    }else {
      tituloValido = correcto.get("titulo");
    }
    if(errores.containsKey("descripcion")){
      descripcionErrorMessage = errorHeader + errores.get("descripcion") + errorFooter;
      descripcionHayError = true;
    }else {
      descripcionValida = correcto.get("descripcion");
    }

      imagenErrorMessage = errorHeader + errores.get("imagen") + errorFooter;
      imagenHayError = true;



    if(!tituloHayError) {
        tituloValor = "value='"+tituloValido+"'";
        resaltarTitulo = resaltarOk;

    } else {
        tituloValor = "placeholder='Introducir título'";
        resaltarTitulo = resaltarFail;
    }

    if(!descripcionHayError) {
        descripcionValor = descripcionValida;
        resaltarDescripcion = resaltarOk;

    } else {
        resaltarDescripcion = resaltarFail;
    }

    resaltarImagen = resaltarFail;

  } else {
        resaltarTitulo = sinResaltar;
        resaltarDescripcion = sinResaltar;
        resaltarImagen = sinResaltar;
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
        <h1>Crear nueva publicación</h1>
    </div>




    <form class="form-horizontal" method="post" enctype="multipart/form-data" action="NuevaPublicacion.do">
        <div <%=resaltarTitulo%>>
            <label class="control-label col-sm-4" for="titulo">Título:</label>

            <div class="col-sm-6">
                <input type="text"  name="inputTitulo" id="titulo" class="form-control" <%= tituloValor%> autofocus>
                <%=tituloErrorMessage%>
            </div>
        </div>
        <div <%=resaltarDescripcion%>>
            <label class="control-label col-sm-4" for="descripcion">Descripción:</label>
            <div class="col-sm-6">

                <textarea class="form-control" rows="3"  class="form-control" id="descripcion"
                          name="inputDescripcion"><%= descripcionValor%></textarea>
                <%=descripcionErrorMessage%>
            </div>
        </div>
        <div <%= resaltarImagen%>>
            <label class="control-label col-sm-4" for="foto">Fotografía:</label>
            <div class="col-sm-6">

                <input type="file" name="inputFoto" class="form-control"  id="foto" >
                <%=imagenErrorMessage%>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-6">
                <button type="submit" name="modificar" class="btn btn-info">Crear publicación</button>
            </div>
        </div>
    </form>
</div><!-- /.container -->



<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-confirmation.js"></script>



</html>
