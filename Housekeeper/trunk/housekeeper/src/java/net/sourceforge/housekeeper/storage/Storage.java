package net.sourceforge.housekeeper.storage;

import java.util.List;
import java.util.Observable;

import net.sourceforge.housekeeper.model.Article;

/**
 * @author Adrian Gygax
 */
public abstract class Storage extends Observable
{
	public abstract void add(Article article);
	public abstract void remove(Article article);
	public abstract List getArticles();
	public abstract void saveData();
	public abstract void loadData();
}