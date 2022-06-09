<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="miModelo.InmuebleVO" %>
<%@ page import="miModelo.ClienteVO" %>
<%@ page import="miModelo.AgenteVO" %>
<%@ taglib prefix="et" uri="etiquetas.tld" %>
<!DOCTYPE html>
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

		<main id="gestionInmueble">
			<div class="container-fluid">
				<div class="anuncio py-5">
					<a class="btn" href="AnunciarInmueble">Anunciar Inmueble</a>
				</div>
				<%
					InmuebleVO[] inmuebles;
					int pagina;
					int paginaVacia;
					
					if (request.getParameter("pag") == null) {
						pagina = 0;
					} else {
						pagina = Integer.parseInt(request.getParameter("pag"));
					}
					
					inmuebles = (InmuebleVO[]) session.getAttribute("inmuebles");
					paginaVacia = 0;
					for (InmuebleVO inm : inmuebles) {
						if (inm != null) {
							%>
							<et:GestionInm codigo="<%=inm.getCodigo()%>" descripcion="<%=inm.getDescripcion()%>" precio="<%=inm.getPrecio()%>" metroCuadrado="<%=inm.getMetrocuadrado()%>" numHabitacion="<%=inm.getNumhabitacion()%>" provincia="<%=inm.getProvincia()%>" poblacion="<%=inm.getPoblacion()%>" codigoPostal="<%=inm.getCodigopostal()%>" direccion="<%=inm.getDireccion()%>" coordenada="<%=inm.getCoordenada()%>" tNombre="<%=inm.getTnombre()%>" stNombre="<%=inm.getStnombre()%>" iNombre="<%=inm.getInombre()%>"/>
						<%} else {
							paginaVacia++;
							if (paginaVacia == 6) {
								%><a href="GestionInmueble" class="d-flex justify-content-center not-found"><h2>No se ha encontrado contenido.</h2></a>
							<%}
						} 
					}
				%>
			</div>
			
			<nav class="d-flex justify-content-center py-3">
				<ul class="pagination">
					<li class="page-item"><a href="GestionInmueble?pag=<%=pagina-1%>" <%if (pagina > 0) { %>class="page-link"<%} else {%>class="page-link disabled"<%} %>><span aria-hidden="true">&laquo;</span></a></li>
					<%
						for (int i = 0; i < (Integer) session.getAttribute("paginaFinal"); i++) {
							%><li <% if (pagina == i) {%>class="page-item active"<%} else {%>class="page-item"<%} %>class="page-item"><a class="page-link" href="GestionInmueble?pag=<%=i%>"><%=i+1%></a></li><%
						}
					%>
					<li class="page-item"><a href="GestionInmueble?pag=<%=pagina+1%>" <%if ((pagina + 1) < (Integer) session.getAttribute("paginaFinal") && (Integer) session.getAttribute("paginaFinal") != 1) { %>class="page-link"<%} else {%>class="page-link disabled"<%} %>><span aria-hidden="true">&raquo;</span></a></li>
				</ul>
			</nav>
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