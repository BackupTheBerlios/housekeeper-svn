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
public class XMLStorage extends Storage
{
	private List articles;

	private XMLEncoder xmlEncoder;

	private XMLDecoder xmlDecoder;


	public XMLStorage()
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
						new FileOutputStream("/home/phelan/Test.xml")));
		
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
						new FileInputStream("/home/phelan/Test.xml")));
			Object result = xmlDecoder.readObject();
			
			articles = (List) result;
			
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
