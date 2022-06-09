<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="icon" href="./img/EZ HOME Logo.png">
		<title>Gestion</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
		<link href="./styles/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<% 
			if (session.getAttribute("errorAnunciarInmueble") != null) { 
				%><script>alert("<%=session.getAttribute("errorAnunciarInmueble")%>");</script><% 
				session.setAttribute("errorAnunciarInmueble", null);	
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

		<main id="anunciarInmueble">
			<div class="d-flex justify-content-center">
				<form method="post" enctype="multipart/form-data">
					<h2 class="pb-3 text-center"><u>Anuncie un Inmueble</u></h2>
					<div>
						<select class="form-select" name="tipo" id="tipo" onchange="getVal(this)" required>
							<option disabled selected value>Elija un Tipo <span style="color: green;">*</span></option>
							<option value="Vivienda">Vivienda</option>
							<option value="Garaje">Garaje</option>
							<option value="Local">Local</option>
							<option value="Oficina">Oficina</option>
							<option value="Trastero">Trastero</option>
						</select>
						<span id="secondList"></span>
					</div>
					<span id="secondList"></span>
					<div class="form-floating">
						<textarea class="form-control" rows="4" cols="50" name="descripcion" id="descripcion" required placeholder="descripcion"></textarea>
						<label for="descripcion">Descripción del Inmueble <span style="color: green;">*</span></label>
					</div>
					
					<div class="form-floating">
						<input class="form-control" type="number" name="precio" step="any" required placeholder="precio"/>
						<label for="precio">Precio <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="number" name="metrocuadrado" step="any" required placeholder="metrocuadrado"/>
						<label for="metrocuadrado">Metros² <span style="color: green;">*</span></label>
					</div>
					
					<div class="form-floating">
						<input class="form-control" type="number" name="numhabitacion" placeholder="numhabitacion"/>
						<label for="numhabitacion">Nº Habitaciones</label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="provincia" maxlength="30" required placeholder="provincia"/>
						<label for="provincia">Provincia <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="poblacion" maxlength="50" required placeholder="poblacion"/>
						<label for="poblacion">Población <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="number" name="codigopostal" required placeholder="codigopostal"/>
						<label for="codigopostal">Código postal <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="direccion" maxlength="40" required placeholder="direccion"/>
						<label for="direccion">Dirección <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">
						<input class="form-control" type="text" name="coordenada" placeholder="coordenada"/>
						<label for="coordenada">Coordenadas Google Maps</label>
					</div>

					<div>
						<label for="imagen" style="color: #e3c800;">Galería de Imágenes</label>
						<input class="form-control" type="file" name="imagen" accept="image/*" multiple placeholder="imagen">
					</div>

					<span class="d-flex justify-content-center">
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

		<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
		<script src="./scripts/form.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>