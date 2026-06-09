/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.domain.repository;
/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.domain.model.Project;
import com.pmotracker.msproject.infrastructure.common.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    int countDealByCategoryCode(String categoryCode);

    Project findByCode(String code);

    List<Project> findByCodeInAndRecordStatusIs(List<String> codes, RecordStatus recordStatus);

    @Query(value = "select d.code as dealCode, d.title as dealTitle, d.end_date as dealExpiry, b.title as bookTitle, b.code as bookCode from deal d " +
            "join book_deal_mapping bdm on d.id = bdm.deal_db_id " +
            "join book b on b.code = bdm.book_code " +
            "where b.code=:bookCode and d.record_status='LIVE' ", nativeQuery = true)
    List<Project> getDealByBook(String bookCode);

}
