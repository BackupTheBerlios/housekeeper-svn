package de.berlios.housekeeper.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import de.berlios.housekeeper.dao.Dao;
import de.berlios.housekeeper.service.Manager;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * <p><a href="BaseManager.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseManager implements Manager {
    protected final Log log = LogFactory.getLog(getClass());
    protected Dao dao = null;
    
    /**
     * @see de.berlios.housekeeper.service.Manager#setDao(de.berlios.housekeeper.dao.Dao)
     */
    public void setDao(Dao dao) {
        this.dao = dao;
    }
    
    /**
     * @see de.berlios.housekeeper.service.Manager#getObject(java.lang.Class, java.io.Serializable)
     */
    public Object getObject(Class clazz, Serializable id) {
        return dao.getObject(clazz, id);
    }
    
    /**
     * @see de.berlios.housekeeper.service.Manager#getObjects(java.lang.Class)
     */
    public List getObjects(Class clazz) {
        return dao.getObjects(clazz);
    }
    
    /**
     * @see de.berlios.housekeeper.service.Manager#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject(Class clazz, Serializable id) {
        dao.removeObject(clazz, id);
    }
    
    /**
     * @see de.berlios.housekeeper.service.Manager#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        dao.saveObject(o);
    }
}
