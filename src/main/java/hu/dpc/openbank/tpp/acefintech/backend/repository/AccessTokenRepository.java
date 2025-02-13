/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 * https://mozilla.org/MPL/2.0/.
 */

package hu.dpc.openbank.tpp.acefintech.backend.repository;

import hu.dpc.openbank.tpp.acefintech.backend.enity.bank.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
    /**
     * Get latest user level access token for a desired scope.
     *
     * @param bankId
     * @param userName
     * @return
     */
    @Query(value = "select a.*\n" //
            + "      from access_token a\n" //
            + "      where a.USERNAME = :username\n" //
            + "        and a.BANK_ID = :bankid\n" //
            + "        and a.ACCESS_TOKEN_TYPE = 'user'", nativeQuery = true)
    AccessToken getLatest(@Param("bankid") String bankId, @Param("username") String userName);

    /**
     * Remove user level access token for a desired scope.
     *
     * @param bankId
     * @param userName
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from access_token where USERNAME = :username and BANK_ID = :bankid and ACCESS_TOKEN_TYPE = 'user'", 
        nativeQuery = true)
    void remove(@Param("bankid") String bankId, @Param("username") String userName);
}
