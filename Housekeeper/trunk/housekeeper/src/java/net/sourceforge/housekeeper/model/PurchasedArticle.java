package net.sourceforge.housekeeper.model;

import java.util.Date;
import java.util.Collection;
/**
 * @author Adrian Gygax
 */
public class PurchasedArticle
{

	private Article article;

	private Collection consumption;

	private int id;

	private Date bestBeforeEnd;

	/**
	 * 
	 * 
	 */
	public PurchasedArticle()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 *  @return 
	 */
	public Article getArticle()
	{
		return article;
	}

	public void setArticle(Article article)
	{
		this.article = article;
	}

	public Collection getConsumption()
	{
		return consumption;
	}

	public void setConsumption(Collection consumption)
	{
		this.consumption = consumption;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getBestBeforeEnd()
	{
		return bestBeforeEnd;
	}

	public void setBestBeforeEnd(Date bestBeforeEnd)
	{
		this.bestBeforeEnd = bestBeforeEnd;
	}

}
