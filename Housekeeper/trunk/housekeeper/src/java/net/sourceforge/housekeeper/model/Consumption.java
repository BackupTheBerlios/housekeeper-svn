package net.sourceforge.housekeeper.model;

import java.util.Date;
/**
 * @author Adrian Gygax
 */
public class Consumption
{

	private int quantity;

	private Date date;

	/**
	 * 
	 * 
	 */
	public Consumption()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

}
