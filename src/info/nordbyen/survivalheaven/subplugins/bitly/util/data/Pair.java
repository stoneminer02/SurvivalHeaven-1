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

package info.nordbyen.survivalheaven.subplugins.bitly.util.data;
/**
 * The Class Pair.
 * 
 * @param <A> the generic type
 * @param <B> the generic type
 */
public final class Pair<A, B> {

    /**
     * P.
     * 
     * @param <A> the generic type
     * @param <B> the generic type
     * @param one the one
     * @param two the two
     * @return the pair
     */
    public static <A, B> Pair<A, B> p(final A one, final B two) {
        return new Pair<A, B>(one, two);
    }

    /** The one. */
    private final A one;
    /** The two. */
    private final B two;

    /**
     * Instantiates a new pair.
     * 
     * @param one the one
     * @param two the two
     */
    private Pair(final A one, final B two) {
        this.one = one;
        this.two = two;
    }

    /**
     * Gets the one.
     * 
     * @return the one
     */
    public A getOne() {
        return this.one;
    }

    /**
     * Gets the two.
     * 
     * @return the two
     */
    public B getTwo() {
        return this.two;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Pair [one=" + this.one + ", two=" + this.two + "]";
    }
}
