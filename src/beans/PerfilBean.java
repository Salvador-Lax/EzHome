package beans;

import java.io.Serializable;

public class PerfilBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private String correo;
	private String telefono;

	public PerfilBean() {
	}

	public PerfilBean(String dni, String nombre, String apellido, String direccion, String correo, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.correo = correo;
		this.telefono = telefono;
	}

	public PerfilBean(String dni, String nombre, String apellido, String direccion, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String email) {
		this.correo = email;
	}
}
