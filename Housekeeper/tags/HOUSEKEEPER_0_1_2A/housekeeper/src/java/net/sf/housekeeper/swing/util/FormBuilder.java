/*
 * This file is part of Housekeeper.
 * 
 * Housekeeper is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Housekeeper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Housekeeper; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Copyright 2003-2004, The Housekeeper Project
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.swing.util;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Generates a form which is compliant with the applications standards.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FormBuilder
{

    private ArrayList elements;

    /**
     * Initializes a FormBuilder.
     */
    public FormBuilder()
    {
        elements = new ArrayList();
    }

    /**
     * Adds a new row to the form. The label is placed to the left of the
     * component.
     * 
     * @param label The label for the component. Must not be null.
     * @param comp The component to be added. Must not be null.
     * @param length The length of this component. Must not be negative.
     */
    public void append(final String label, final Component comp,
                       final int length)
    {
        assert label != null : "Parameter label must not be null";
        assert comp != null : "Parameter comp must not be null";
        assert !(length < 0) : "Parameter length (" + length
                + ") is less than 0";

        final Element e = new Element(label, comp, length);
        elements.add(e);
    }

    /**
     * Builds a standards conforming JPanel containing all the added components.
     * 
     * @return A fully initialized JPanel.
     */
    public JPanel getPanel()
    {
        final DefaultFormBuilder builder = new DefaultFormBuilder(buildLayout());
        builder.setDefaultDialogBorder();
        final HashMap map = generateMap();
        final Iterator iter = elements.iterator();
        while (iter.hasNext())
        {
            final Element e = (Element) iter.next();
            final int span = ((Integer) map.get(new Integer(e.length)))
                    .intValue();
            builder.append(e.label, e.comp, span);
        }

        return builder.getPanel();
    }

    /**
     * Generates a sorted set of all lengths of the elements.
     * 
     * @return a sorted set of all lengths of the elements.
     */
    private SortedSet lengths()
    {
        final SortedSet lengthsSet = new TreeSet();

        final Iterator eli = elements.iterator();
        while (eli.hasNext())
        {
            final Element e = (Element) eli.next();
            lengthsSet.add(new Integer(e.length));

        }
        return lengthsSet;
    }

    /**
     * Generates a map where the key is a length and value is the according
     * number of columns an element has to span for the length.
     * 
     * @return A map of lengths to span values.
     */
    private HashMap generateMap()
    {
        final SortedSet lengthsSet = lengths();

        final HashMap map = new HashMap();
        final Iterator iter = lengthsSet.iterator();
        int i = 1;
        while (iter.hasNext())
        {
            map.put(iter.next(), new Integer(i));
            i++;
        }
        return map;
    }

    /**
     * Creates a FormLayout object using the attributes of the specified
     * elements.
     * 
     * @return Specific FormLayout.
     */
    private FormLayout buildLayout()
    {
        final SortedSet lengthsSet = lengths();
        final Object[] lengths = lengthsSet.toArray();
        final StringBuffer col = new StringBuffer();
        col.append("right:pref, 3dlu");
        col.append(", " + lengths[0] + "dlu");
        for (int i = 1; i < lengths.length; i++)
        {
            final int colSize = ((Integer) lengths[i]).intValue()
                    - ((Integer) lengths[i - 1]).intValue();
            col.append(", " + colSize + "dlu");
        }
        return new FormLayout(col.toString(), "");
    }

    /**
     * Representation of an element of the form which consists of a component
     * and its label.
     */
    private class Element
    {

        private String    label;

        private Component comp;

        private int       length;

        private Element(final String label, final Component comp,
                final int length)
        {
            this.label = label;
            this.comp = comp;
            this.length = length;
        }

    }
}