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

import info.nordbyen.survivalheaven.subplugins.bitly.util.data.Pair;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodBase.
 * 
 * @param <A> the generic type
 */
public abstract class MethodBase<A> implements BitlyMethod<A> {

    /** The name. */
    private final String name;
    /** The parameters. */
    private final Iterable<Pair<String, String>> parameters;

    /**
     * Instantiates a new method base.
     * 
     * @param name the name
     * @param parameters the parameters
     */
    public MethodBase(final String name, final Iterable<Pair<String, String>> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Instantiates a new method base.
     * 
     * @param name the name
     * @param parameters the parameters
     */
    public MethodBase(final String name, final Pair<String, String>[] parameters) {
        this(name, Arrays.asList(parameters));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.subplugins.bitly.util.BitlyMethod#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.subplugins.bitly.util.BitlyMethod#getParameters
     * ()
     */
    @Override
    public Iterable<Pair<String, String>> getParameters() {
        return this.parameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + this.name + ", parameters=" + this.parameters + "]";
    }
}
