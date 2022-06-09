<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="miModelo.InmuebleVO" %>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="icon" href="./img/EZ HOME Logo.png">
		<title>Gestion</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
		<link href="./styles/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<% 
			if (session.getAttribute("errorModificarInmueble") != null) { 
				%><script>alert("<%=session.getAttribute("errorModificarInmueble")%>");</script><% 
				session.setAttribute("errorModificarInmueble", null);	
			};
		%>
		<header class="sticky-top">
			<nav class="navbar">
				<div class="container-fluid">
				<a class="navbar-brand" href="GestionInmueble">
					<img
					src="./img/EZ HOME Logo.png"
					alt="logo"
					width="70"
					height="70"
					class="d-inline-block"
					/>
					Gestión
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
					<a href="Perfil" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Perfil</a>
					<hr>
					<a href="Logout" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Logout</a>
				</div>
			</div>
		</header>

		<main id="modificarInmueble">
			<div class="d-flex justify-content-center">
				<form method="post">
					<%
						InmuebleVO ivo;
					
						ivo = (InmuebleVO) session.getAttribute("datosInmueble");
						
						if (ivo != null) {%>
					<h2 class="text-center"><u>Modificar Inmueble</u></h2>
					<div class="form-floating">
						<textarea class="form-control" rows="4" cols="50" name="descripcion" id="descripcion" placeholder="descripcion"><%=ivo.getDescripcion()%></textarea>
						<label for="descripcion">Descripción</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="number" name="precio" step="any" placeholder="precio" value="<%=ivo.getPrecio()%>"/>
						<label for="precio">Precio</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="number" name="metrocuadrado" step="any" placeholder="metrocuadrado" value="<%=ivo.getMetrocuadrado()%>"/>
						<label for="metrocuadrado">Metros²</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="number" name="numhabitacion" placeholder="numhabitacion" value="<%=ivo.getNumhabitacion()%>"/>
						<label for="numhabitacion">Nº Habitaciones</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="provincia" placeholder="provincia" maxlength="30" value="<%=ivo.getProvincia()%>"/>
						<label for="provincia">Provincia</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="poblacion" placeholder="poblacion" maxlength="50" value="<%=ivo.getPoblacion()%>"/>
						<label for="poblacion">Población</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="number" name="codigopostal" placeholder="codigopostal" value="<%=ivo.getCodigopostal()%>"/>
						<label for="codigopostal">Codigo postal</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="direccion" placeholder="direccion" maxlength="40" value="<%=ivo.getDireccion()%>"/>
						<label for="direccion">Dirección</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="coordenada" placeholder="coordenada" value="<%=ivo.getCoordenada()%>"/>
						<label for="coordenada">Coordenadas Google Maps</label>
					</div>
					<span class="d-flex justify-content-center">
						<input type="submit" value="Modificar" class="btn" style="color: #e3c800;">
					</span>

					<span class="d-flex justify-content-center">
						<a href="GestionInmueble" class="text-center pt-3">¿Ha cambiado de idea? Regresar a Gestión</a>
					</span>
					<%}%>
				</form>
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

		<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
		<script src="./scripts/form.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>