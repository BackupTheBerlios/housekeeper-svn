package net.sourceforge.housekeeper.storage;

import java.util.Collection;

import net.sourceforge.housekeeper.model.Article;

/**
 * @author Adrian Gygax
 */
public interface Storage
{
	public abstract void saveData();
	public abstract void loadData();
	/**
	 * 
	 * @return
	 */
	public abstract Collection getArticles();
	/**
	 * 
	 * @param collection
	 */
	public abstract void addArticle(Article article);
}