package net.sf.housekeeper.swing.util;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 * An editor for a JSpinner which formats dates in a locale dependent manner.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$ 
 */
public final class DateEditor extends JSpinner.DefaultEditor
{

    /**
     * Creates a DateEditor using the current locale.
     * 
     * @param spinner The spinner for this editor.
     */
    public DateEditor(final JSpinner spinner)
    {
        super(spinner);

        final SpinnerDateModel model = (SpinnerDateModel) spinner.getModel();
        final DateFormatter formatter = new DateFormatter();
        final DefaultFormatterFactory factory = new DefaultFormatterFactory(
                formatter);
        final JFormattedTextField ftf = getTextField();
        ftf.setEditable(true);
        ftf.setFormatterFactory(factory);

        try
        {
            String maxString = formatter.valueToString(model.getStart());
            String minString = formatter.valueToString(model.getEnd());
            ftf.setColumns(Math.max(maxString.length(), minString.length()));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}