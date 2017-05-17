
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
        //Valores del usuario a editar
        String nick = "";
        String email = "";

        //Indican si la modificación de email ha ido bien o mal
        boolean emailModificadoOk = false;
        boolean emailModificadoFail = false;

        //Indican si la modificación de la contraseña ha ido bien o mal
        boolean passModificadaOk = false;
        boolean passModificadaFail = false;

        //Mensajes para dar feedback al usuario
        String mensajeEmailOk = "<span class='help-block'>El email se ha modificado correctamente</span>";
        String mensajeEmailFail =
                "<span class='help-block'>El email contiene alg&uacute;n error, no se ha podido modificar</span>";
        String resaltarEmailOk = "has-success has-feedback";
        String resaltarEmailFail = "has-error has-feedback";
        String mensajeEmail="";
        String resaltarEmail="";

        String mensajePassOk = "<span class='help-block'>La contrase&ntilde;a se ha modificado correctamente</span>";
        String mensajePassFail =
                "<span class='help-block'>La contrase&ntilde;a contiene alg&uacute;n error, no se ha podido modificar</span>";
        String resaltarPassOk = "has-success has-feedback";
        String resaltarPassFail = "has-error has-feedback";
        String mensajePass="";
        String resaltarPass="";


        if (request.getAttribute("nick") != null) {
            nick = (String) request.getAttribute("nick");
        }

        if (request.getAttribute("email") != null) {
            email = (String) request.getAttribute("email");
        }


        if (request.getAttribute("emailModificadoOk")!=null) {
            emailModificadoOk = true;
        } else if (request.getAttribute("emailModificadoFail")!=null) {
            emailModificadoFail = true;
        }

        if (request.getAttribute("passModificadaOk")!=null) {
            passModificadaOk = true;
        } else if (request.getAttribute("passModificadaFail")!=null) {
            passModificadaFail = true;
        }

        String emailFinal = "";


        if(emailModificadoFail) {
            mensajeEmail=mensajeEmailFail;
            resaltarEmail=resaltarEmailFail;
            emailFinal = " value='" + email + "'";
        } else {
            emailFinal = "value='" + email + "'";
        }

        if(emailModificadoOk) {
            mensajeEmail=mensajeEmailOk;
            resaltarEmail=resaltarEmailOk;
        }

        if(passModificadaFail) {
            mensajePass=mensajePassFail;
            resaltarPass=resaltarPassFail;
        }

        if(passModificadaOk) {
            mensajePass=mensajePassOk;
            resaltarPass=resaltarPassOk;
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
                        <li class="active"><a href="#">Mi perfil</a></li>
                        <li ><a href="#">Mensajes</a></li>
                        <li ><a href="editarPublicaciones.jsp">Editar publicaciones</a> </li>
                    </ul>
                    <form action="logout.do" method="post">
                        <input type="submit" class="btn btn-danger navbar-btn pull-right" value="Logout" />
                    </form>
                </div><!--/.nav-collapse -->
            </div>
        </nav>

        <div class="container">

            <div class="starter-template">
                <h1>Editar perfil</h1>
            </div>

            <form class="form-horizontal" method="post" action="EditarUsuario.do">
                <div class="form-group">
                    <label class="control-label col-sm-4" for="nick">Nick:</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="nick" value="<%=nick%>" disabled>
                    </div>
                </div>
                <div class="form-group <%=resaltarEmail%>">
                    <label class="control-label col-sm-4" for="email">Email:</label>
                    <div class="col-sm-6">

                        <input type="email" class="form-control" id="email"  name="email"
                        <%=emailFinal%> >
                        <%=mensajeEmail%>
                    </div>
                </div>
                <div class="form-group <%=resaltarPass%>">
                    <label class="control-label col-sm-4" for="pwd">Contrase&ntilde;a:</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="pwd"
                               placeholder="Introduce la nueva contrase&ntilde;a" name="password">
                        <%=mensajePass%>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-6">
                        <button type="submit" name="modificar" class="btn btn-info">Modificar perfil</button>
                        <input name="eliminar" type="submit"  class="btn btn-danger"
                        value="Eliminar perfil"></input>
                    </div>
                </div>
            </form>
        </div><!-- /.container -->



        <!-- Scripts -->
        <script src="../js/jquery-1.11.1.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/bootstrap-confirmation.js"></script>



</html>
