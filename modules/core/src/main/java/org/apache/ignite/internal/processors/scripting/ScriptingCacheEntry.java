package org.apache.ignite.internal.processors.scripting;


/**
 * Scripting cache entry.
 */
public class ScriptingCacheEntry {
    /** Key. */
    private Object key;

    /** Value. */
    private Object val;

    /**
     * @param key Key.
     * @param val Value.
     */
    public ScriptingCacheEntry(Object key, Object val) {
        if (key instanceof ScriptingObjectConverter8)
            this.key = ((ScriptingObjectConverter8)key).getFields();
        else
            this.key = key;

        if (val instanceof ScriptingObjectConverter8)
            this.val = ((ScriptingObjectConverter8)val).getFields();
        else
            this.val = val;
    }

    /**
     * @return Key.
     */
    public Object getKey() {
        return key;
    }

    /**
     * @param key Key.
     */
    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * @return Value.
     */
    public Object getValue() {
        return val;
    }

    /**
     * @param val Value.
     */
    public void setValue(Object val) {
        this.val = val;
    }
}