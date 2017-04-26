<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.Map,java.util.HashMap" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Pictagram</title>
    <meta name="description"/>
    <meta name="author" content="Óscar Saboya, Ian Ávila"/>
    <!-- Favicons (created with http://realfavicongenerator.net/)-->
    <link rel="apple-touch-icon" sizes="57x57" href="../img/favicons/apple-touch-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="../img/favicons/apple-touch-icon-60x60.png">
    <link rel="icon" type="image/png" href="../img/favicons/favicon-32x32.png" sizes="32x32">
    <link rel="icon" type="image/png" href="../img/favicons/favicon-16x16.png" sizes="16x16">
    <link rel="manifest" href="../img/favicons/manifest.json">
    <link rel="shortcut icon" href="../img/favicons/favicon.ico">
    <meta name="msapplication-TileColor" content="#00a8ff">
    <meta name="msapplication-config" content="img/favicons/browserconfig.xml">
    <meta name="theme-color" content="#ffffff">
    <!-- Normalize -->
    <link rel="stylesheet" type="text/css" href="../css/normalize.css">
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <!-- Owl -->
    <link rel="stylesheet" type="text/css" href="../css/owl.css">
    <!-- Animate.css -->
    <link rel="stylesheet" type="text/css" href="../css/animate.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" type="text/css" href="../fonts/font-awesome-4.1.0/css/font-awesome.min.css">
    <!-- Elegant Icons -->
    <link rel="stylesheet" type="text/css" href="../fonts/eleganticons/et-icons.css">
    <!-- Main style -->
    <link rel="stylesheet" type="text/css" href="../css/cardio.css">
</head>

<body onload="abrirDialogo()">

<%

    //Variables para abrir dialogos login/registro tras un error
    boolean abrirLogin = false;
    boolean abrirRegistro = false;


    ////////////////////
    //Parámetros Login//
    ////////////////////

    String emailLogin = "";
    String contrasenyaLogin = "";
    String mensajeErrorLogin = "";
    boolean loginError = false;

    if (request.getAttribute("loginError") != null) {
        loginError = true;
    }

    if (request.getAttribute("mensajeErrorLogin") != null) {
        mensajeErrorLogin = (String) request.getAttribute("mensajeErrorLogin");
    }

    if (request.getAttribute("abrirLogin")!=null) {

        abrirLogin = true;
    }

    ///////////////////////
    //Parámetros Registro//
    ///////////////////////

    //Valores
    String nickRegistro = "";
    String emailRegistro = "";
    String contrasenyaRegistro = "";

    //Valores que se han validado
    String nickRegistroOK = "";
    String emailRegistroOK = "";
    String contrasenyaRegistroOK = "";

    //Mensajes de error
    String nickErrorRegistro = "";
    String emailErrorRegistro = "";
    String contrasenyaErrorRegistro = "";
    String mensajeErrorRegistro = "";

    //Errores
    boolean nickHayErrorRegistro = false;
    boolean emailHayErrorRegistro = false;
    boolean contrasenyaHayErrorRegistro = false;

    HashMap<String, String> errores = (HashMap) request.getAttribute("errores");
    HashMap<String, String> correcto = (HashMap) request.getAttribute("correcto");

    String errorHeader = "<font color=\"red\"><b>";
    String errorFooter = "</b></font>";

    if (errores != null) {
        if (errores.containsKey("email")) {
            emailErrorRegistro = errorHeader + errores.get("email") + errorFooter;
            emailHayErrorRegistro = true;
        } else {
            emailRegistroOK = correcto.get("email");
        }

        if (errores.containsKey("nick")) {
            nickErrorRegistro = errorHeader + errores.get("nick") + errorFooter;
            nickHayErrorRegistro = true;
        } else {
            nickRegistroOK = correcto.get("nick");
        }

        if (errores.containsKey("contrasenya")) {
            contrasenyaErrorRegistro = errorHeader + errores.get("contrasenya") + errorFooter;
            contrasenyaHayErrorRegistro = true;
        } else {
            contrasenyaRegistroOK = correcto.get("contrasenya");
        }

        if (errores.containsKey("abrirRegistro")) {
            abrirRegistro = true;
            mensajeErrorRegistro = errores.get("abrirRegistro");
        }
    }

%>
<!------------------------->


<div class="preloader">
    <img src="../img/loader.gif" alt="Preloader image">
</div>
<nav class="navbar">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp"><i class="white fa fa-instagram fa-5x"></i></a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right main-nav">
                <li><a href="#" data-toggle="modal" data-target="#modalLogin">Login</a></li>
                <li><a href="#" data-toggle="modal" data-target="#modalRegistro" class="btn btn-blue">Registro</a></li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<header id="intro">
    <div class="container">
        <div class="table">
            <div class="header-text">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <h1 class="white typed">Pictagram</h1>
                        <h2 class="light white">Una forma divertida de compartir tus fotos.<span
                                class="typed-cursor">|</span></h2>

                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<section>
    <div class="cut cut-top"></div>
    <div class="container">
        <div class="row intro-tables">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <div class="intro-table intro-table-hover">
                    <div class="bottom">
                        <h4 class="white heading small-heading no-margin regular">Regístrate hoy</h4>
                        <h4 class="white heading small-pt">Gratis!</h4>
                        <a href="#" data-toggle="modal" data-target="#modalRegistro" class="btn btn-white-fill expand">Registro</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<div class="modal fade" id="modalRegistro" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content modal-popup">
            <a href="#" class="close-link"><i class="icon_close_alt2"></i></a>
            <h3 class="white">Registro</h3>
            <form class="popup-form" method="post" action="Registro.do" >

                <%
                    if (abrirRegistro) {
                        mensajeErrorRegistro = "<p class='alert alert-danger'>"+mensajeErrorRegistro+"</p>";
                    }
                %>
                <%=mensajeErrorRegistro%>
                <%
                    if (nickHayErrorRegistro) {
                        nickRegistro = "style='background-color:red' placeholder='Nick'";
                    } else {
                        nickRegistro = "value='" + nickRegistroOK + "'";
                    }
                %>
                <%=nickErrorRegistro%>
                <input type="text" class="form-control form-white" id="registroNick" name="registroNick"
                       placeholder="Nick"
                        <%=nickRegistro%> />
                <%
                    if (emailHayErrorRegistro) {
                        emailRegistro = "style='background-color:red' placeholder='Email'";
                    } else {
                        emailRegistro = "value='" + emailRegistroOK + "'";
                    }
                %>
                <%=emailErrorRegistro%>
                <input type="text" class="form-control form-white" id="registroEmail" name="registroEmail"
                       placeholder="Email" <%=emailRegistro%> />
                <%
                    if (contrasenyaHayErrorRegistro) {
                        contrasenyaRegistro = "style='background-color:red' placeholder='Contraseña'";
                    } else {
                        contrasenyaRegistro = "value='" + contrasenyaRegistroOK + "'";
                    }
                %>
                <%=contrasenyaErrorRegistro%>
                <input type="password" class="form-control form-white" id="registroContrasenya"
                       name="registroContrasenya"
                       placeholder="Contraseña" <%=contrasenyaRegistro%>/>
                <button type="submit" class="btn btn-submit" value="registro">Registrarme</button>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content modal-popup">
            <a href="#" class="close-link"><i class="icon_close_alt2"></i></a>
            <h3 class="white">Login</h3>
            <form class="popup-form" method="post" action="Login.do">
                <%
                    if (abrirLogin) {
                        mensajeErrorLogin = "<p class='alert alert-danger'>"+mensajeErrorLogin+"</p>";
                    }
                %>
                <%=mensajeErrorLogin%>
                <input type="text" class="form-control form-white" id="loginEmail" name="loginEmail"
                       placeholder="Nick">
                <input type="password" class="form-control form-white" id="loginContrasenya"
                       name="loginContrasenya" placeholder="Contraseña">

                <button type="submit" class="btn btn-submit" value="login">Entrar</button>
            </form>
        </div>
    </div>
</div>
<footer>
    <div class="container">

        <div class="row bottom-footer text-center-mobile">
            <div class="col-sm-8">
                <p>&copy; Óscar Saboya e Ian Ávila</p>
            </div>
            <div class="col-sm-4 text-right text-center-mobile">
                <ul class="social-footer">
                    <li><a href="http://www.facebook.com"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="http://www.twitter.com"><i class="fa fa-twitter"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>
<!-- Holder for mobile navigation -->
<div class="mobile-nav">
    <ul>
    </ul>
    <a href="#" class="close-link"><i class="arrow_up"></i></a>
</div>
<!--Javascript-->
<script>
    function abrirDialogo() {
        var abrirLogin = "<%=abrirLogin%>";
        var abrirRegistro = "<%=abrirRegistro%>";
        var mensajeErrorLogin = "<%=mensajeErrorLogin%>";
        var mensajeErrorRegistro = "<%=mensajeErrorRegistro%>";

        console.log("Login: "+abrirLogin);
        console.log("Registro: "+abrirRegistro);
        console.log("Mensaje Login: "+mensajeErrorLogin);
        console.log("Mensaje Registro: "+mensajeErrorRegistro);

        if(abrirLogin == 'true') {
            $("#modalLogin").modal();

        } else if(abrirRegistro == 'true') {
            $("#modalRegistro").modal();

        }
    }
</script>
<!-- Scripts -->
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/owl.carousel.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/wow.min.js"></script>
<script src="../js/typewriter.js"></script>
<script src="../js/jquery.onepagenav.js"></script>
<script src="../js/main.js"></script>
</body>

</html>

