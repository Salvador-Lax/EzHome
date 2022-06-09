package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class GestionInm extends SimpleTagSupport {
	String codigo;
	String descripcion;
	Double precio;
	Double metroCuadrado;
	int numHabitacion;
	String provincia;
	String poblacion;
	int codigoPostal;
	String direccion;
	String coordenada;
	String tNombre;
	String stNombre;
	String iNombre;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		out.print("<div class='card mb-5'>");
		out.print("<img alt='" + getiNombre() + "' src='CargarImagenInmueble?inm=" + getCodigo()
				+ "' class='card-img-top'>");
		out.print("<div class='card-body'>");
		out.print("<p>Codigo de Inmueble: " + getCodigo()
				+ "<br>Precio: " + getPrecio() + " €"
				+ "<br>" + gettNombre() + ", " + getStNombre()
				+ "<br>Direccion: " + getDireccion() + ", en " + getPoblacion() + "(CP:" + getCodigoPostal() + "), "
				+ getProvincia()
				+ "<br>Habitaciones: " + getNumHabitacion() + ",&nbspMetros²: " + getMetroCuadrado()
				+ "<br>Coordenadas: " + getCoordenada()
				+ "<br>Descripcion:<br>" + getDescripcion() + "</p>");
		out.print("</div>");
		out.print("<div class='card-footer'>");
		out.print("<a class='btn' href='ModificarInmueble?inm=" + getCodigo()
				+ "'>Modificar</a><a class='btn' href='ModificarGaleria?inm="
				+ getCodigo() + "'>Galeria</a><a class='btn' href='EliminarInmueble?inm=" + getCodigo()
				+ "'>Eliminar</a>");
		out.print("</div>");
		out.print("</div>");
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getMetroCuadrado() {
		return metroCuadrado;
	}

	public void setMetroCuadrado(Double metroCuadrado) {
		this.metroCuadrado = metroCuadrado;
	}

	public int getNumHabitacion() {
		return numHabitacion;
	}

	public void setNumHabitacion(int numHabitacion) {
		this.numHabitacion = numHabitacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(String coordenada) {
		this.coordenada = coordenada;
	}

	public String gettNombre() {
		return tNombre;
	}

	public void settNombre(String tNombre) {
		this.tNombre = tNombre;
	}

	public String getStNombre() {
		return stNombre;
	}

	public void setStNombre(String stNombre) {
		this.stNombre = stNombre;
	}

	public String getiNombre() {
		return iNombre;
	}

	public void setiNombre(String iNombre) {
		this.iNombre = iNombre;
	}
}
