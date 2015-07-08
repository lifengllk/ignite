/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.yardstick.cache.model;

import org.apache.ignite.cache.query.annotations.*;

import java.io.*;

/**
 * Value used for indexed put test.
 */
public class Person1 implements Serializable {
    /** Value. */
    @QuerySqlField(index = true)
    private int val1;

    /**
     * Empty constructor.
     */
    public Person1() {
        // No-op.
    }

    /**
     * Constructs.
     *
     * @param val1 Indexed value.
     */
    public Person1(int val1) {
        this.val1 = val1;
    }

    /**
     * @return Get value.
     */
    public int getVal1() {
        return val1;
    }

    /**
     * Set value.
     *
     * @param val1 Value.
     */
    public void setVal1(int val1) {
        this.val1 = val1;
    }

    /** {@inheritDoc} */
    @Override public boolean equals(Object o) {
        return this == o || (o instanceof Person1) && val1 == ((Person1)o).val1;
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return val1;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return "IndexedValue1 [val1=" + val1 + ']';
    }
}