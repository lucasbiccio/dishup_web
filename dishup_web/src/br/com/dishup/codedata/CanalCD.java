package br.com.dishup.codedata;

/********************************
 * @author Lucas Biccio Ribeiro
 * @since 27/02/2013
 * @version 1.0 Enum responsible for encapsulate the system s channel s codedata.
 ********************************/
public enum CanalCD {

	/*********
	 * Channel s constants.
	 ********/
	DISHUP_CONSUMER_WEBSITE_CHANNEL   (1, "Dish Up - Consumidor Web Site"),
	DISHUP_RESTAURANT_WEBSITE_CHANNEL (2, "Dish Up - Restaurante Web Site"),
	DISHUP_APPMOBILE_CONSUMER_IPHONE  (3, "Dish Up - Consumidor Iphone"),
	DISHUP_APPMOBILE_CONSUMER_ANDROID (4, "Dish Up - Consumidor Android");
	
	/********
	 * Channel s atributes
	 ********/
	private final int id;
	private final String nome;
	
	/********
	 * Method responsible for get the channel s id.
	 * @return channel s id
	 *******/
	public int getId() {
		return id;
	}
	
	/********
	 * Method responsible for get the channel s id.
	 * @return channel s name
	 ********/
	public String getNome() {
		return nome;
	}
	
	/********
	 * Constructor
	 * @param id - channel s id
	 * @param nome - channel s name
	 ********/
	private CanalCD(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
}
