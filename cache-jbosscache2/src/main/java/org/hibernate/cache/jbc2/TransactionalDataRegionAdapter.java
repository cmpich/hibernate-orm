/*
 * Copyright (c) 2007, Red Hat Middleware, LLC. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, v. 2.1. This program is distributed in the
 * hope that it will be useful, but WITHOUT A WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. You should have received a
 * copy of the GNU Lesser General Public License, v.2.1 along with this
 * distribution; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Red Hat Author(s): Steve Ebersole
 */
package org.hibernate.cache.jbc2;

import org.jboss.cache.Cache;

import org.hibernate.cache.TransactionalDataRegion;
import org.hibernate.cache.CacheDataDescription;

/**
 * {@inheritDoc}
 *
 * @author Steve Ebersole
 */
public class TransactionalDataRegionAdapter extends BasicRegionAdapter implements TransactionalDataRegion {
	protected final CacheDataDescription metadata;

	public TransactionalDataRegionAdapter(Cache jbcCache, String regionName, CacheDataDescription metadata) {
		super( jbcCache, regionName );
		this.metadata = metadata;
	}

	/**
	 * Here, for JBossCache, we consider the cache to be transaction aware if the underlying
	 * cache instance has a refernece to the transaction manager.
	 */
	public boolean isTransactionAware() {
		return jbcCache.getConfiguration().getRuntimeConfig().getTransactionManager() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public CacheDataDescription getCacheDataDescription() {
		return metadata;
	}
}
