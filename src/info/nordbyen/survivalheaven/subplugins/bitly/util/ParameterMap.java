/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
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

package info.nordbyen.survivalheaven.subplugins.bitly.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The Class ParameterMap.
 */
class ParameterMap extends AbstractCollection<Map.Entry<String, List<String>>> {

    /** The parameters. */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, List<String>> parameters = new HashMap();

    /**
     * Adds the.
     * 
     * @param name the name
     * @param value the value
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void add(final String name, final String value) {
        List values = this.parameters.get(name);
        if (values == null) {
            values = new ArrayList();
        }
        values.add(value);
        this.parameters.put(name, values);
    }

    /**
     * Gets the.
     * 
     * @param name the name
     * @return the list
     */
    public List<String> get(final String name) {
        return this.parameters.get(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#iterator()
     */
    @Override
    public Iterator<Map.Entry<String, List<String>>> iterator() {
        return this.parameters.entrySet().iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        return this.parameters.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {
        return "ParameterMap [parameters=" + this.parameters + "]";
    }
}
