package net.sf.housekeeper.persistence.jdom;

import net.sf.housekeeper.persistence.UnsupportedFileVersionException;

import org.apache.commons.logging.LogFactory;
import org.jdom.Element;

/**
 * A DomainConverterProxy delegates method calls to a
 * {@look net.sf.housekeeper.persistence.jdom.DomainConverter}which is able to
 * handle conversion of a desired file version. It keeps a list of which
 * DomainConverters are able to handle which file versions and delegates method
 * invocations to an appropriate instance.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class DomainConverterProxy implements DomainConverter
{

    /**
     * Name of the attribute wich specifies the file format version.
     */
    private static final String               FILE_VERSION_ATTRIBUTE = "version";

    /**
     * Singleton instance.
     */
    private static final DomainConverterProxy INSTANCE               = new DomainConverterProxy();

    /**
     * Do not use this constructor to get an object of this class, use
     * {@look #getInstance()}instead.
     */
    private DomainConverterProxy()
    {

    }

    /**
     * Returns the Singleton instance of this class.
     * 
     * @return the Singleton instance of this class.
     */
    static DomainConverterProxy getInstance()
    {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.jdom.DomainConverter#replaceDomain(org.jdom.Element)
     */
    public void replaceDomain(Element root) throws UnsupportedFileVersionException
    {
        //Get the file version
        final String versionString = root
                .getAttributeValue(FILE_VERSION_ATTRIBUTE);
        if (versionString == null)
        {
            throw new UnsupportedFileVersionException(
                    "Could not determine version of the file structure.");
        }

        //Check if version is supported
        final int version = Integer.parseInt(versionString);
        LogFactory.getLog(DomainConverter1.class).debug(
                                                        "Detected file version: "
                                                                + version);
        if (!isVersionSupported(version))
        {
            throw new UnsupportedFileVersionException(version);
        }

        //Create domain converter
        final DomainConverter converter = new DomainConverter1();
        converter.replaceDomain(root);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.jdom.DomainConverter#createDocument()
     */
    public Element convertDomainToXML()
    {
        final DomainConverter latestConverter = new DomainConverter1();
        final Element document = latestConverter.convertDomainToXML();
        return document;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.jdom.DomainConverter#isVersionSupported(int)
     */
    public boolean isVersionSupported(int version)
    {
        return version == 1;
    }

}