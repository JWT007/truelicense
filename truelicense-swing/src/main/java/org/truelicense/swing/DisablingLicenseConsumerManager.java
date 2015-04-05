/*
 * Copyright (C) 2005-2015 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package org.truelicense.swing;

import javax.annotation.concurrent.Immutable;
import org.truelicense.core.License;
import org.truelicense.core.LicenseConsumerManager;
import org.truelicense.core.LicenseManagementException;
import org.truelicense.core.io.Source;
import org.truelicense.swing.util.Enabler;

/**
 * A decorating license consumer manager which disables a component before it
 * forwards the call to {@link #install} or {@link #uninstall} to the delegate
 * manager.
 * If the operation succeeds, the component remains disabled.
 * Otherwise, the component state gets recovered.
 *
 * @author Christian Schlichtherle
 */
@Immutable
final class DisablingLicenseConsumerManager
extends UpdatingLicenseConsumerManager {

    private static final long serialVersionUID = 0L;

    DisablingLicenseConsumerManager(
            Enabler enabler,
            LicenseConsumerManager manager) {
        super(manager, enabler);
    }

    @Override
    public License install(final Source source)
    throws LicenseManagementException {
        return run(new Action<License>() {
            @Override public License call() throws LicenseManagementException {
                return manager.install(source);
            }
        });
    }

    @Override
    public void uninstall() throws LicenseManagementException {
        run(new Action<Void>() {
            @Override public Void call() throws LicenseManagementException {
                manager.uninstall();
                return null;
            }
        });
    }

    private <T> T run(final Action<T> action)
    throws LicenseManagementException {
        final boolean enabled = enabled();
        disable();
        try {
            return action.call();
        } catch (final LicenseManagementException ex) {
            enabled(enabled);
            throw ex;
        } catch (final RuntimeException ex) {
            enabled(enabled);
            throw ex;
        } catch (final Error ex) {
            enabled(enabled);
            throw ex;
        }
    }

    private interface Action<T> { T call() throws LicenseManagementException; }
}