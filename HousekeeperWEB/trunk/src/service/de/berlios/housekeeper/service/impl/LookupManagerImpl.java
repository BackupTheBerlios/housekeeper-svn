package de.berlios.housekeeper.service.impl;

import java.util.ArrayList;
import java.util.List;

import de.berlios.housekeeper.dao.LookupDao;
import de.berlios.housekeeper.model.LabelValue;
import de.berlios.housekeeper.model.Role;
import de.berlios.housekeeper.service.LookupManager;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * <p><a href="LookupManagerImpl.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupManagerImpl extends BaseManager implements LookupManager {
    //~ Instance fields ========================================================

    private LookupDao dao;

    //~ Methods ================================================================

    public void setLookupDao(LookupDao dao) {
        super.dao = dao;
        this.dao = dao;
    }
    /**
     * @see de.berlios.housekeeper.service.LookupManager#getAllRoles()
     */
    public List getAllRoles() {
        List roles = dao.getRoles();
        List list = new ArrayList();
        Role role = null;

        for (int i = 0; i < roles.size(); i++) {
            role = (Role) roles.get(i);
            list.add(new LabelValue(role.getName(), role.getName()));
        }

        return list;
    }
}
