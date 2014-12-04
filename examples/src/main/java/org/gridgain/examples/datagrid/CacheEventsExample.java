/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.examples.datagrid;

import org.apache.ignite.*;
import org.apache.ignite.lang.*;
import org.gridgain.grid.*;
import org.gridgain.grid.cache.*;
import org.gridgain.grid.events.*;

import java.util.*;

import static org.gridgain.grid.events.GridEventType.*;

/**
 * This examples demonstrates events API. Note that grid events are disabled by default and
 * must be specifically enabled, just like in {@code examples/config/example-cache.xml} file.
 * <p>
 * Remote nodes should always be started with special configuration file which
 * enables P2P class loading: {@code 'ggstart.{sh|bat} examples/config/example-cache.xml'}.
 * <p>
 * Alternatively you can run {@link CacheNodeStartup} in another JVM which will
 * start GridGain node with {@code examples/config/example-cache.xml} configuration.
 */
public class CacheEventsExample {
    /** Cache name. */
    private static final String CACHE_NAME = "partitioned";

    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws GridException If example execution failed.
     */
    public static void main(String[] args) throws GridException, InterruptedException {
        try (Ignite g = Ignition.start("examples/config/example-cache.xml")) {
            System.out.println();
            System.out.println(">>> Cache events example started.");

            final GridCache<Integer, String> cache = g.cache(CACHE_NAME);

            // Clean up caches on all nodes before run.
            cache.globalClearAll(0);

            // This optional local callback is called for each event notification
            // that passed remote predicate listener.
            IgniteBiPredicate<UUID, GridCacheEvent> locLsnr = new IgniteBiPredicate<UUID, GridCacheEvent>() {
                @Override public boolean apply(UUID uuid, GridCacheEvent evt) {
                    System.out.println("Received event [evt=" + evt.name() + ", key=" + evt.key() +
                        ", oldVal=" + evt.oldValue() + ", newVal=" + evt.newValue());

                    return true; // Continue listening.
                }
            };

            // Remote listener which only accepts events for keys that are
            // greater or equal than 10 and if event node is primary for this key.
            IgnitePredicate<GridCacheEvent> rmtLsnr = new IgnitePredicate<GridCacheEvent>() {
                @Override public boolean apply(GridCacheEvent evt) {
                    System.out.println("Cache event [name=" + evt.name() + ", key=" + evt.key() + ']');

                    int key = evt.key();

                    return key >= 10 && cache.affinity().isPrimary(g.cluster().localNode(), key);
                }
            };

            // Subscribe to specified cache events on all nodes that have cache running.
            // Cache events are explicitly enabled in examples/config/example-cache.xml file.
            g.events(g.cluster().forCache(CACHE_NAME)).remoteListen(locLsnr, rmtLsnr,
                EVT_CACHE_OBJECT_PUT, EVT_CACHE_OBJECT_READ, EVT_CACHE_OBJECT_REMOVED);

            // Generate cache events.
            for (int i = 0; i < 20; i++)
                cache.putx(i, Integer.toString(i));

            // Wait for a while while callback is notified about remaining puts.
            Thread.sleep(2000);
        }
    }
}
