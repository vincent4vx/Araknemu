/*
 * This file is part of Araknemu.
 *
 * Araknemu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Araknemu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Araknemu.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2017-2019 Vincent Quatrevieux
 */

package fr.quatrevieux.araknemu.realm.authentication;

import fr.quatrevieux.araknemu.core.dbal.repository.EntityNotFoundException;
import fr.quatrevieux.araknemu.realm.host.HostService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Service for handle authentication
 */
final public class AuthenticationService {
    final private fr.quatrevieux.araknemu.data.living.repository.account.AccountRepository repository;
    final private HostService hosts;

    /**
     * Set of accounts which wait for authentication process
     * There are loaded, but not yet authenticated
     */
    final private Set<AuthenticationAccount> pending = Collections.synchronizedSet(new HashSet<>());

    /**
     * Map of authenticated accounts
     * There are linked to a session
     */
    final private ConcurrentMap<Integer, AuthenticationAccount> authenticated = new ConcurrentHashMap<>();

    public AuthenticationService(fr.quatrevieux.araknemu.data.living.repository.account.AccountRepository repository, HostService hosts) {
        this.repository = repository;
        this.hosts = hosts;
    }

    /**
     * Perform authenticate request
     *
     * This method is synchronized to ensure that two account are not requested login
     * in the same time
     */
    synchronized public void authenticate(AuthenticationRequest request) {
        AuthenticationAccount account;

        try {
            account = new AuthenticationAccount(
                repository.findByUsername(request.username()),
                this
            );
        } catch (EntityNotFoundException e) {
            request.invalidCredentials();
            return;
        }

        if (!account.checkPassword(request.password())) {
            request.invalidCredentials();
            return;
        }

        if (
            isAuthenticated(account)
            || pending.contains(account)
        ) {
            request.alreadyConnected();
            return;
        }

        pending.add(account);

        hosts.checkLogin(account, response -> {
            if (response) {
                request.isPlaying();
            } else {
                request.success(account);
            }

            pending.remove(account);
        });
    }

    /**
     * Check if the account is authenticated
     */
    public boolean isAuthenticated(AuthenticationAccount account) {
        if (!authenticated.containsKey(account.id())) {
            return false;
        }

        return authenticated.get(account.id()).isLogged();
    }

    /**
     * Remove account from authenticated accounts
     */
    void logout(AuthenticationAccount account) {
        authenticated.remove(account.id());
    }

    /**
     * Add account to authenticated account
     */
    void login(AuthenticationAccount account) {
        authenticated.put(account.id(), account);
    }
}
