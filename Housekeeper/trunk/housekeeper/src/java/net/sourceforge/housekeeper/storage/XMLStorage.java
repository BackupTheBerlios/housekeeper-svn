package net.sourceforge.housekeeper.storage;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.housekeeper.model.Article;

/**
 * @author Adrian Gygax
 */
public final class XMLStorage extends Storage
{
	private List articles;

	private XMLEncoder xmlEncoder;
	private XMLDecoder xmlDecoder;
	
	private final static String FILENAME = "/home/phelan/test.xml";


	XMLStorage()
	{
		articles = new ArrayList();
	}
	
	public void add(Article article)
	{
		articles.add(article);
		setChanged();
		notifyObservers();
	}
	
	public void remove(Article article)
	{
		articles.remove(article);
		setChanged();
		notifyObservers();
	}
	
	

	public void saveData()
	{
		try
		{
			xmlEncoder =
				new XMLEncoder(
					new BufferedOutputStream(
						new FileOutputStream(FILENAME)));
		
			xmlEncoder.writeObject(articles);
			
			xmlEncoder.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		}
	}

	public void loadData()
	{
		try
		{
			xmlDecoder =
				new XMLDecoder(
					new BufferedInputStream(
						new FileInputStream(FILENAME)));
			Object result = xmlDecoder.readObject();
			
			articles = (List) result;
			
			setChanged();
			notifyObservers();
			
			xmlDecoder.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		
		
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.housekeeper.storage.Storage#getArticles()
	 */
	public List getArticles()
	{
		return articles;
	}

}
