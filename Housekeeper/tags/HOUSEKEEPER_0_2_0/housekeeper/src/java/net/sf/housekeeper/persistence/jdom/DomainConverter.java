package net.sf.housekeeper.persistence.jdom;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;

import org.jdom.Element;

/**
 * A DomainConverter converts XML element hierarchies into domain objects and
 * vice-versa. It does support specific versions of the Housekeeper XML file
 * format.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface DomainConverter
{

    /**
     * Creates domain objects from XML objects and replaces all in-memory domain
     * data.
     * 
     * @return The converted Household object.
     * @param root The root element of the XML object tree. Must not be null.
     * @throws IllegalArgumentException if the given element is not a valid root
     *             element.
     * @throws UnsupportedFileVersionException if the version of the element
     *             structure is not supported by this converter.
     */
    Household convertToDomain(final Element root) throws UnsupportedFileVersionException;

    /**
     * Creates an XML element hierarchy with all in-memory domain objects.
     * 
     * @param household TODO
     * @return the element hierarchy.
     */
    Element convertDomainToXML(Household household);

    /**
     * Returns if a file version is supported by this DomainConverter.
     * 
     * @param version The version to check.
     * @return True if the version is supportes, false otherwise.
     */
    boolean isVersionSupported(final int version);
}