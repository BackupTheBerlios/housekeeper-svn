package net.sourceforge.housekeeper.model;



/**
 * @author Adrian Gygax
 */
public class Article
{
	private String name;

	private String store;

	private double price;

	private String unit;

	private int quantity;

	/**
	 * 
	 * 
	 */
	public Article()
	{
		
	}
	
	public Article(String name, String store, double price, int quantity, String unit)
	{
		this.name = name;
		this.store = store;
		this.price = price;
		this.unit = unit;
		this.quantity = quantity;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStore()
	{
		return store;
	}

	public void setStore(String store)
	{
		this.store = store;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getQuantityUnit()
	{
		return unit;
	}

	public void setQuantityUnit(String quantityUnit)
	{
		this.unit = quantityUnit;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public String toString()
	{
		return name + " " + store + " " + price + " " + quantity + unit;
	}

}
