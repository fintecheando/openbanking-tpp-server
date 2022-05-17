/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 * https://mozilla.org/MPL/2.0/.
 */

package hu.dpc.openbank.tpp.acefintech.backend.repository;

import hu.dpc.openbank.tpp.acefintech.backend.enity.bank.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<BankInfo, String> {

    /**
     * Get latest user level access token for a desired scope.
     *
     * @param userName
     * @return
     */
    @Query(value = "select *\n" +
            "from banks b\n" +
            "where b.id in (\n" +
            "    select distinct BANK_ID\n" +
            "    from access_token\n" +
            "    where USERNAME = :username)", nativeQuery = true)
    List<BankInfo> getUserConnectedBanks(@Param("username") String userName);
}
