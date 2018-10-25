package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una {@link Especie} de bicho.
 * 
 * @author Charly Backend
 */

@Entity
public class Especie {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
    @Column(unique=true)
	private String nombre;
	private int altura;
	private int peso;
    @Enumerated(EnumType.STRING)
	private TipoBicho tipo;
	private int energiaInicial;
	private String urlFoto;
	private int cantidadBichos;
	@ManyToOne
	private Especie especieBase;
	@OneToOne(cascade = CascadeType.ALL)
	private Especie evolucion;
	@OneToMany(cascade = CascadeType.ALL)
	private List<CondicionEvolucion> condicionesDeEvolucion;

	public Especie(){   }
	
	public Especie(int id, String nombre, TipoBicho tipo) {
	    this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	/**
	 * @return el nombre de la especie (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return la altura de todos los bichos de esta especie
	 */
	public int getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public int getPeso() {
		return this.peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del bichomon por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta especie.
	 */
	public int getEnergiaInicial() {
		return this.energiaInicial;
	}
	public void setEnergiaIncial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	/**
	 * @return el tipo de todos los bichos de esta especie
	 */
	public TipoBicho getTipo() {
		return this.tipo;
	}
	public void setTipo(TipoBicho tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * especie.
	 */
	public int getCantidadBichos() {
		return this.cantidadBichos;
	}
	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}

    public List<CondicionEvolucion> getCondicionesDeEvolucion() {   return condicionesDeEvolucion;  }
    public void setCondicionesDeEvolucion(List<CondicionEvolucion> condicionesDeEvolucion) {    this.condicionesDeEvolucion = condicionesDeEvolucion;   }

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

    public Especie getEspecieBase() {   return especieBase; }
    public void setEspecieBase(Especie especieBase) {   this.especieBase = especieBase; }

    public Especie getEvolucion() { return evolucion;   }
    public void setEvolucion(Especie evolucion) {   this.evolucion = evolucion; }

	public Bicho crearBicho(String nombreBicho)
	{
		this.cantidadBichos++;
		return new Bicho(this, nombreBicho);
	}

    public Bicho crearBicho(){
        Bicho nuevoBicho    = new Bicho(this, "");
        nuevoBicho.setVictorias(0);
        nuevoBicho.setEnergia(this.getEnergiaInicial());
	    this.cantidadBichos++;
        return nuevoBicho;
    }

    public boolean puedeEvolucionar(Bicho bicho) {
        if (this.getEvolucion() != null)
        {
            return this .getCondicionesDeEvolucion()
                        .stream()
                        .allMatch((condicion) -> {return condicion.cumpleCondicion(bicho);});
        }
        return false;
    }
}
