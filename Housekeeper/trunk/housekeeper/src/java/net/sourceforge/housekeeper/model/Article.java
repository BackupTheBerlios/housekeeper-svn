package net.sourceforge.housekeeper.model;

import java.util.Collection;

/**
 * @author Adrian Gygax
 */
public class Article
{

	private Collection purchasedArticle;

	private int id;

	private String name;

	private String store;

	private float price;

	private String quantityUnit;

	private int quantity;

	/**
	 * 
	 * 
	 */
	public Article()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
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

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

	public String getQuantityUnit()
	{
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit)
	{
		this.quantityUnit = quantityUnit;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

}
