package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import java.io.Serializable;

/**
 * Servicio generico para guardar o recuperar
 * objetos de forma genreica. Usado principalmente
 * en tests
 */
public class TestService {
	
	public void crearEntidad(Object object) {
		Runner.runInSession(() -> {
			Session session = Runner.getCurrentSession();
			session.save(object);
			return null;
		});
	}
	
	public <T> T recuperarEntidad(Class<T> tipo, Serializable key) {
		return Runner.runInSession(() -> {
			Session session = Runner.getCurrentSession();
			T valor = session.get(tipo, key);
			return valor;
		});
	}

}
