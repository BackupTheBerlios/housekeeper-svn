package net.sf.housekeeper.persistence;

import net.sf.housekeeper.util.LocalisationManager;

/**
 * Signals that the format version of a data source is not supported.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class UnsupportedFileVersionException extends Exception
{

    /**
     * The unsupported version.
     */
    private final int version;

    /**
     * Constructs an exception using a default error message.
     * 
     * @param version The unsupported file version.
     */
    public UnsupportedFileVersionException(final int version)
    {
        super("File version is not supported: " + version);

        this.version = version;
    }

    /**
     * Returns the unsupported version.
     * 
     * @return The unsupported version.
     */
    public int getVersion()
    {
        return version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage()
    {
        final String text = LocalisationManager.INSTANCE
                .getText("persistence.unsupportedVersion");
        return text;
    }

}