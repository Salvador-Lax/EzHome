<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="beans.CitasBean" %>
<%@page import="miModelo.ClienteVO" %>
<%@page import="miModelo.AgenteVO" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="icon" href="./img/EZ HOME Logo.png">
		<title>Citas</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    	<link href="./styles/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<header>
			<nav class="navbar">
				<div class="container-fluid">
					<a class="navbar-brand" href="Citas">
						<img
						src="./img/EZ HOME Logo.png"
						alt="logo"
						width="70"
						height="70"
						class="d-inline-block"
						/>
						Citas
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

		<main id="citas">
			<div class="d-flex justify-content-center">
				<div>
					<h2 class="text-center pt-3"><u>Citas</u></h2>
					<%
						if (session.getAttribute("mensajeCita") != null) {
							%><h5 class="pt-5"><%=session.getAttribute("mensajeCita")%></h5><%
							session.setAttribute("mensajeCita", null);
						} else {
							ArrayList<CitasBean> cbs;
							ClienteVO cvo;
							AgenteVO avo;
							
							cvo = (ClienteVO) session.getAttribute("cliente");
							avo = (AgenteVO) session.getAttribute("agente");
							
							cbs = new ArrayList<CitasBean>();
							
							cbs = (ArrayList<CitasBean>) session.getAttribute("citas");
										
							for (CitasBean cita : cbs) {
								if (cita != null) {%>
									<div class="card my-5">
										<div class="card-body">
											<p><%=cita.getTipo()%>, <%=cita.getSubtipo()%><br>
											Población: <%=cita.getProvincia()%>, <%=cita.getPoblacion()%><br>
											Dirección: <%=cita.getDireccion()%><br>
											<%if (avo != null) {
												%>Cliente: <%=cita.getNombre()%> <br>Teléfono: <%=cita.getTelefono()%> <br>E-Mail: <%=cita.getEmail()%><br><%
												if (cita.getFechayhora() != null) {
														out.println("Fecha y Hora: " + cita.getFechayhora() + "</p></div>");
														session.setAttribute("citaMod", cita);
														%><div class="card-footer">
															<a class="btn" href="ModificarCita?cli=<%=cita.getIdCliente()%>&inm=<%=cita.getCodigoInmueble()%>">Modificar</a>
															<a class="btn" href="CancelarCita?cli=<%=cita.getIdCliente()%>&inm=<%=cita.getCodigoInmueble()%>">Cancelar</a>
															<a class="btn" href="ContactarCita?cli=<%=cita.getIdCliente()%>">Contactar</a>
														</div><%
													} else {
														out.println("Fecha y Hora: Pendiente de concertar</p></div>");
														session.setAttribute("citaMod", cita);
														%><div class="card-footer">
															<a class="btn" href="ModificarCita?cli=<%=cita.getIdCliente()%>&inm=<%=cita.getCodigoInmueble()%>">Concertar</a>
															<a class="btn" href="CancelarCita?cli=<%=cita.getIdCliente()%>&inm=<%=cita.getCodigoInmueble()%>">Cancelar</a>
															<a class="btn" href="ContactarCita?cli=<%=cita.getIdCliente()%>">Contactar</a>
														</div><%
													}
											}
											if (cvo != null && cvo.getCodigoVerificar().equals("0")) {
												%>Agente: <%=cita.getNombre()%> Teléfono: <%=cita.getTelefono()%> E-Mail: <%=cita.getEmail()%><br><%
												if (cita.getFechayhora() != null) {
														out.println("Fecha y Hora: " + cita.getFechayhora() + "</p></div>");
														%><div class="card-footer">
															<a class="btn" href="CancelarCita?age=<%=cita.getIdAgente()%>&inm=<%=cita.getCodigoInmueble()%>">Cancelar</a>
															<a class="btn" href="ContactarCita?age=<%=cita.getIdAgente()%>">Contactar</a>
														</div><%
													} else {
														out.println("Fecha y Hora: Pendiente de revisar</p></div>");
														%><div class="card-footer">
															<a class="btn" href="CancelarCita?age=<%=cita.getIdAgente()%>&inm=<%=cita.getCodigoInmueble()%>">Cancelar</a>
															<a class="btn" href="ContactarCita?age=<%=cita.getIdAgente()%>">Contactar</a>
														</div><%
													}
											}
									%></div>				
								<%}
							}
							
						}			
					%>
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