package net.sf.housekeeper.persistence;


/**
 * Signals the the version or format of a data source is not supported.
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
     * Constant if no version at all has been specified.
     */
    public static final int UNSPECIFIED_VERSION = -1;
    
    /**
     * Constructs an exception using a default error message.
     * 
     * @param version The unsupported file version.
     */
    public UnsupportedFileVersionException(final int version)
    {
        super("File version " + version + " is not supported.");
        this.version = version;
    }

    /**
     * Constructs an exception with the given message and
     * an unspecified unsupported version.
     * 
     * @param message The detail message for this exception.
     */
    public UnsupportedFileVersionException(String message)
    {
        super(message);
        version = UNSPECIFIED_VERSION;
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

}
