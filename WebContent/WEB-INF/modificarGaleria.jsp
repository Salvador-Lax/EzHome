<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="miModelo.ClienteVO" %>
<%@ page import="miModelo.AgenteVO" %>
<%@ page import="miModelo.ImagenVO" %>
<%@ page import="java.util.ArrayList" %>
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
		<%if (session.getAttribute("errorEliminarImagen") != null) {
        	%> <script>alert("<%=session.getAttribute("errorEliminarImagen")%>");</script>
        	<% session.setAttribute("errorEliminarImagen", null); 
        }; %>
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

		<main id="modificarGaleria" class="pb-5">
		<br><br>
			<div id="galeriaFondo" class="d-flex justify-content-center flex-column">
				<h2 class="text-center pt-5"><u>Gestión Galería</u></h2>
				<% 
					ArrayList<ImagenVO> galeria;

					galeria = new ArrayList<ImagenVO>();

					if (session.getAttribute("galeria") != null) {
					
						galeria = (ArrayList<ImagenVO>) session.getAttribute("galeria");					
				%>
						<div id="galeria" class="carousel slide pt-5" data-bs-ride="carousel">
							<div class="carousel-indicators">
								<%for (int i = 0; i < galeria.size(); i++) {
									if (i == 0) {
										%><button type="button" data-bs-target="#galeria" data-bs-slide-to="<%=i%>" class="active" aria-current="true" aria-label="Slide 1"></button><%
									} else {
										%><button type="button" data-bs-target="#galeria" data-bs-slide-to="<%=i%>" aria-current="true" aria-label="Slide 1"></button><%
									}
								}%>
							</div>
						<div class="carousel-inner">
						
						<%
							for (int i = 0; i < galeria.size(); i++) {
								if (galeria.get(i) != null) {
									if (i == 0) {
										%><div class="carousel-item active">
											<img class="d-block w-20" alt="<%=galeria.get(i).getNombre()%>" src="CargarImagenInmueble?img=<%=galeria.get(i).getId()%>">
											<div class="carousel-caption">
												<h2><a href="EliminarImagen?inm=<%=request.getParameter("inm")%>&img=<%=galeria.get(i).getId()%>">Eliminar Imagen</a></h2>
											</div>
										</div>
									<%} else {
										%><div class="carousel-item">
											<img class="d-block w-20" alt="<%=galeria.get(i).getNombre()%>" src="CargarImagenInmueble?img=<%=galeria.get(i).getId()%>">
											<div class="carousel-caption">
												<h2><a href="EliminarImagen?inm=<%=request.getParameter("inm")%>&img=<%=galeria.get(i).getId()%>">Eliminar Imagen</a></h2>
											</div>
										</div>
									<%}
									}
							}%>
						</div>
						<button class="carousel-control-prev" type="button" data-bs-target="#galeria" data-bs-slide="prev">
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Previous</span>
						</button>
						<button class="carousel-control-next" type="button" data-bs-target="#galeria" data-bs-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Next</span>
						</button>
					</div>
				
				<%} else {
					if (session.getAttribute("errorModificarGaleria") != null) {
			        	%> <h5 class="text-center pt-3"><%=session.getAttribute("errorModificarGaleria")%></h5>
			        	<% session.setAttribute("errorModificarGaleria", null); 
			        };
				}
				%>
				<form id="nuevaImagen" method="post" enctype="multipart/form-data">
					<h5 class="text-center">Añadir Imágenes</h5>
					<div>
						<label for="imagen" style="color: #e3c800;">Galeria de Imágenes</label>
						<input class="form-control" type="file" name="imagen" accept="image/*" multiple placeholder="imagen">
					</div>

					<span class="d-flex justify-content-center pt-3">
						<input type="submit" value="Publicar" class="btn" style="color: #e3c800;">
					</span>

					<span class="d-flex justify-content-center">
						<a href="GestionInmueble" class="text-center pt-3">¿Ha cambiado de idea? Regresar a Gestión</a>
					</span>
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
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>