/*
 * org.openmicroscopy.shoola.agents.rnd.model.GreyScaleManager
 *
 *------------------------------------------------------------------------------
 *
 *  Copyright (C) 2004 Open Microscopy Environment
 *      Massachusetts Institute of Technology,
 *      National Institutes of Health,
 *      University of Dundee
 *
 *
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *------------------------------------------------------------------------------
 */

package org.openmicroscopy.shoola.agents.rnd.model;


//Java imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JRadioButton;

//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.rnd.RenderingAgtCtrl;
import org.openmicroscopy.shoola.agents.rnd.editor.ChannelEditor;
import org.openmicroscopy.shoola.agents.rnd.metadata.ChannelData;

/** 
 * 
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author  <br>Andrea Falconi &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:a.falconi@dundee.ac.uk">
 * 					a.falconi@dundee.ac.uk</a>
 * @version 2.2 
 * <small>
 * (<b>Internal version:</b> $Revision$ $Date$)
 * </small>
 * @since OME2.2
 */
class GreyScalePaneManager
	implements ActionListener
{
	
	private GreyScalePane view;
	
	private RenderingAgtCtrl	eventManager;
	
	GreyScalePaneManager(GreyScalePane view) 
	{
		this.view = view;
	}
	
	void setEventManager(RenderingAgtCtrl eventManager)
	{
		this.eventManager = eventManager;
	}
	
	/** Attach listeners. */
	void attachObjectListener(Object component, int index)
	{
		AbstractButton b = null;
		if (component instanceof JButton) 
			b = (JButton) component;	
		else if (component instanceof JRadioButton)
			b = (JRadioButton) component;
		b.addActionListener(this);
		b.setActionCommand(""+index);
	}
	
	/** Handle events. */
	public void actionPerformed(ActionEvent e)
	{
		String s = (String) e.getActionCommand();
		Object obj = (Object) e.getSource();
		try {
			int w = Integer.parseInt(s);
			if (obj instanceof JRadioButton) 
				eventManager.setActive(w, ((JRadioButton) obj).isSelected());
			else showChannelInfo(w);
		} catch(NumberFormatException nfe) {
				throw nfe;  //just to be on the safe side...
		}    
	}
	
	/**
	 * Pop up the wavelength info editor.
	 * 
	 * @param w		wavelength index.
	 */
	private void showChannelInfo(int w) 
	{
		ChannelData[] cd = eventManager.getChannelData();
		eventManager.showDialog(new ChannelEditor(eventManager, cd[w], w));
	}
	
}
