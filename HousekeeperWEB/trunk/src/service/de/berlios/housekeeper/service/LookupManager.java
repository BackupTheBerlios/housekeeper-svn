package de.berlios.housekeeper.service;

import java.util.List;

import de.berlios.housekeeper.dao.LookupDao;


/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * <p>
 * <a href="LookupManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupManager extends Manager {
    //~ Methods ================================================================

    public void setLookupDao(LookupDao dao);
    
    /**
     * Retrieves all possible roles from persistence layer
     * @return List
     */
    public List getAllRoles();
}
