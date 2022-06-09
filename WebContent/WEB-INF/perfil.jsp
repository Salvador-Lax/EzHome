<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="beans.PerfilBean" %>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    	<link rel="icon" href="./img/EZ HOME Logo.png">
		<title>Perfil</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    	<link href="./styles/style.css" rel="stylesheet" type="text/css">
		<jsp:useBean id="perfilbean" scope="session" class="beans.PerfilBean"></jsp:useBean>
	</head>
	<body>
		<header class="sticky-top">
			<nav class="navbar">
				<div class="container-fluid">
					<a class="navbar-brand" href="Login">
						<img
						src="./img/EZ HOME Logo.png"
						alt="logo"
						width="70"
						height="70"
						class="d-inline-block"
						/>
						Perfil
					</a>
					<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
						<i class="bi bi-list"></i>
					</button>
				</div>
			</nav>
			<div class="collapse" id="navbarToggleExternalContent">
				<div class="shadow-3 p-4 d-flex justify-content-center flex-column">
					<a href="Home" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Home</a>
					<hr>
					<a href="Catalogo" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Catalogo</a>
					<hr>
					<a href="Contacto" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Contacto</a>
					<hr>
					<a href="Logout" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Logout</a>
				</div>
			</div>
		</header>

		<main id="perfil">
			<div class="container-fluid d-flex justify-content-center py-5">
				<div class="card">
					<img alt="Imagen de perfil" src="CargarImagenPerfil" class="card-img-top">
					<div class="card-body">
						<h4 class="card-title" style="color: #67071d;"><jsp:getProperty property="apellido" name="perfilbean"/>,&nbsp<jsp:getProperty property="nombre" name="perfilbean"/></h4>
						<p class="card-text">
							Documentación: <jsp:getProperty property="dni" name="perfilbean"/><br>
							Dirección: <jsp:getProperty property="direccion" name="perfilbean"/><br>
							Teléfono: <jsp:getProperty property="telefono" name="perfilbean"/>
						</p>
					</div>

					<div class="card-footer">
						<a href="ModificarPerfil" class="btn">Modificar Perfil</a>
						<% if ((boolean) session.getAttribute("isAgente")) {
							%><a href="GestionInmueble" class="btn">Gestión Inmuebles</a>
						<%}%>
						<a href="Citas" class="btn">Citas</a>
						<a href="Logout" class="btn">Cerrar Sesión</a>
					</div>
				</div>
			</div>
			
		</main>

		<footer class="d-flex flex-wrap justify-content-between align-items-center py-3 px-5">
			<div class="col-md-4 d-flex align-items-center">
				<a href="Home" class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1">
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