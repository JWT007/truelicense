/*
 * Copyright (C) 2005 - 2019 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */
package global.namespace.truelicense.api.builder;

/**
 * A builder for some product.
 * Builders are generally <em>not</em> thread-safe.
 *
 * @param <Product> the type of the product.
 */
public interface GenBuilder<Product> {

    /** Builds and returns a new product. */
    Product build();
}
