package net.sourceforge.housekeeper.model;

import java.util.Date;
import java.util.Collection;
/**
 * @author Adrian Gygax
 */
public class Purchase
{

	private Collection purchasedArticle;

	private int id;

	private Date date;

	/**
	 * 
	 * 
	 */
	public Purchase()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Collection getPurchasedArticle()
	{
		return purchasedArticle;
	}

	public void setPurchasedArticle(Collection purchasedArticle)
	{
		this.purchasedArticle = purchasedArticle;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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
