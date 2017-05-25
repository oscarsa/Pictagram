
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
    String nickAmistad = (String) request.getAttribute("nickAmistad");
    String emailAmistad = (String) request.getAttribute("emailAmistad");
    String botonAmistad = "";

    if (request.getAttribute("hayRelacion")==null) {
        if(request.getAttribute("haMandadoPeticion")!=null) {
            botonAmistad =
            "<button type=\"button\"  class=\"col-md-offset-2 btn btn-default disabled\">Peticion enviada</button>";
        } else if(request.getAttribute("haRecibidoPeticion")!=null) {
            botonAmistad =
            "<button type=\"submit\" name=\"botonConfirmar\" class=\" col-md-offset-2 btn btn-success\">Confirmar amistad</button>";
        } else {
             botonAmistad =
        "<button type=\"submit\" name=\"botonPeticion\" class=\"col-md-offset-2 btn btn-success\">Enviar peticion de amistad</button>";
        }
    } else {
         botonAmistad =
        "<button type=\"submit\" name=\"botonEliminar\" class=\"col-md-offset-2 btn btn-danger\">Eliminar amigo</button>";
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
                <li ><a href="#">Mi perfil</a></li>
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
        <h1>Perfil</h1>
    </div>

    <form class="form-horizontal" action="amistad.do" method="post">
        <div class="form-group">
            <label class="col-sm-2 control-label">Nick</label>
            <div class="col-sm-10">
                <p class="form-control-static"><%=nickAmistad%></p>
            </div>

        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <p class="form-control-static"><%=emailAmistad%></p>
            </div>

        </div>
        <input type="hidden" name="nickAmigo" value="<%=nickAmistad%>">

        <%=botonAmistad%>

    </form>
</div><!-- /.container -->



<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-confirmation.js"></script>



</html>
