/**
 * This file is part of SurvivalHeaven, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org.org>
 * Copyright (c) nordbyen.info
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.nordbyen.survivalheaven.gui;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author l0lkj
 * 
 */
public class GUISubPlugin extends SubPlugin {

    GUIWindow guiWindow;

    /**
     * @param name
     */
    public GUISubPlugin(final String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
     */
    @Override
    protected void disable() {
        guiWindow.setVisible(false);
        guiWindow.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
     */
    @Override
    protected void enable() {
        guiWindow = new GUIWindow();
        try {
            guiWindow.setIconImage(ImageIO.read(getClass().getResourceAsStream("/icon.png")));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
