package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.tables.CelebrationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelebrationRepository extends JpaRepository<CelebrationTemplate, Long> {
    String birthdayCelebration = "BIRTH_DAY";
    String anniversaryCelebration = "ANNIVERSARY";
    String newJoinCelebration = "NEW_JOIN";


    @Query("select c from CelebrationTemplate c " +
            "where c.statusFlag = com.cashlinkglobal.scheduler.entity.enums.StatusFlag.ACTIVE " +
            "and c.celebrationType in ?1 " +
            "order by c.createdDate DESC")
    List<CelebrationTemplate> getAllCelebration(String... celebrationType);






}
