<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="miModelo.ClienteVO" %>
<%@ page import="miModelo.AgenteVO" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="./img/EZ HOME Logo.png">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    <link href="./styles/style.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <header class="sticky-top">
      <nav class="navbar">
        <div class="container-fluid">
          <a class="navbar-brand" href="Home">
            <img
              src="./img/EZ HOME Logo.png"
              alt="logo"
              width="70"
              height="70"
              class="d-inline-block"
            />
            Home
          </a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="bi bi-list"></i>
          </button>
        </div>
      </nav>
      <div class="collapse" id="navbarToggleExternalContent">
        <div class="shadow-3 p-4 d-flex justify-content-center flex-column">
          <a href="Catalogo" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Catalogo</a>
          <hr>
          <a href="Contacto" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Contacto</a>
          <hr>
          <%
              ClienteVO cvo;
              AgenteVO avo;

              cvo = (ClienteVO) session.getAttribute("cliente");
              avo = (AgenteVO) session.getAttribute("agente");
              if ((cvo != null && cvo.getCodigoVerificar().equals("0")) || avo != null) {
                %><a href="Perfil" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Perfil</a>
                  <hr>
                  <a href="Logout" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Logout</a><%
                } else {
                %><a href="Login" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Login</a><%
                }
            %>
        </div>
      </div>
    </header>

    <div id="presentacion">
      <h1 class="text-center">EZ HOME</h1>
    </div>
    <main id="home">
      <div class="container-fluid d-flex justify-content-center flex-column py-5">
            <div>
                <h2 class="text-center pt-3">
                    Ayudándote a hacer realidad<br>
                    tu casa de ensueño<br>
                    por mas de 20 años.
                </h2>
                <img src="./img/placeholder3-edit.png" class="img-fluid mx-auto d-block" alt="placeholder">
            </div>

            <div class="pt-5" style="margin-left: auto; margin-right: auto;">
                <div class="card" style="width: 20em;">
                  <div class="card-body text-center">
                    <img src="./img/plc1.png" class="img-fluid mx-auto d-block" alt="plc1">
                    <h2>Nuestros expertos a tu disposición</h2>
                    <h5>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eget venenatis arcu. Mauris laoreet tortor lacus, tempor ultrices lorem efficitur vel. Suspendisse nisl ipsum, blandit sit amet gravida et, ullamcorper a velit.</h5>
                  </div>
                </div>

                <div class="card mt-5" style="width: 20em;">
                  <div class="card-body text-center">
                    <img src="./img/plc2.png" class="img-fluid mx-auto d-block" alt="plc1">
                    <h2>Encuentra la propiedad ideal</h2>
                    <h5>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eget venenatis arcu. Mauris laoreet tortor lacus, tempor ultrices lorem efficitur vel. Suspendisse nisl ipsum, blandit sit amet gravida et, ullamcorper a velit.</h5>
                  </div>
                </div>

                <div class="card mt-5" style="width: 20em;">
                  <div class="card-body text-center">
                    <img src="./img/plc3.png" class="img-fluid mx-auto d-block" alt="plc1">
                    <h2>Atención al cliente 24/7</h2>
                    <h5>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eget venenatis arcu. Mauris laoreet tortor lacus, tempor ultrices lorem efficitur vel. Suspendisse nisl ipsum, blandit sit amet gravida et, ullamcorper a velit.</h5>
                  </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 px-5">
      <div class="col-md-4 d-flex align-items-center">
        <a href="/" class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1">
          <img
            src="./img/EZ HOME Logo.png"
            alt="logo"
            width="40"
            height="40"
            class="d-inline-block"
          />
        </a>
        <span>&copy; 2022 EzHome, Inc</span>
      </div>

      <ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
        <li class="ms-3">
          <a class="text-muted" href="#">
            <i class="bi bi-twitter" ></i>
          </a>
        </li>
        <li class="ms-3">
          <a class="text-muted" href="#">
            <i class="bi bi-instagram"></i>
          </a>
        </li>
        <li class="ms-3">
          <a class="text-muted" href="#">
            <i class="bi bi-facebook"></i>
          </a>
        </li>
      </ul>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
  </body>
</html>
