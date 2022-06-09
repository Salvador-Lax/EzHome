package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CatalogoInm extends SimpleTagSupport {
	String codigo;
	Double precio;
	Double metroCuadrado;
	int numHabitacion;
	String poblacion;
	String provincia;
	String direccion;
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
		out.print("<p class='card-text'>Precio: " + getPrecio() + " €<br>" + gettNombre() + ", " + getStNombre()
				+ "<br>" + "Direccion: " + getDireccion() + ", en " + getPoblacion() + ", " + getProvincia()
				+ "<br>Habitaciones: " + getNumHabitacion() + ",&nbspMetros²: " + getMetroCuadrado() + "</p>");
		out.print("<a href='InfoInmueble?inm=" + getCodigo() + "' class='btn'>Mas detalles</a>");
		out.print("</div>");
		out.print("</div>");
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
