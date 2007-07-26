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
package org.hibernate.cache.jbc2.entity;

import org.jboss.cache.Cache;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.jbc2.TransactionalDataRegionAdapter;

/**
 * Defines the behavior of the entity cache regions for JBossCache.
 *
 * @author Steve Ebersole
 */
public class EntityRegionImpl extends TransactionalDataRegionAdapter implements EntityRegion {

	public EntityRegionImpl(Cache jbcCache, String regionName, CacheDataDescription metadata) {
		super( jbcCache, regionName, metadata );
	}

	/**
	 * {@inheritDoc}
	 */
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		if ( AccessType.READ_ONLY.equals( accessType ) ) {
			return new ReadOnlyAccess( this );
		}
		if ( AccessType.TRANSACTIONAL.equals( accessType ) ) {
			return new TransactionalAccess( this );
		}

		// todo : add support for READ_WRITE ( + NONSTRICT_READ_WRITE ??? )

		throw new CacheException( "unsupported access type [" + accessType.getName() + "]" );
	}
}
