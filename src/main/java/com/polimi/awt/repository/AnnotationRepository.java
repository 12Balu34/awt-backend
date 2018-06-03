package com.polimi.awt.repository;

import com.polimi.awt.model.Annotation;
import com.polimi.awt.model.Peak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
    Set<Annotation> findAllByPeakId(Long peakId);

    Annotation findAnnotationById(Long annotationId);

    Annotation findAnnotationByPeakAndWorkerId(Peak peak, Long workerId);

    @Query(value = "SELECT * FROM annotation an LEFT JOIN localized_peak_name lpn on an.id = lpn.annotation_id WHERE an.peak_id IN " +
            "(SELECT peak.id FROM peak WHERE campaign_id = :campaignId AND peak.to_be_annotated = 1)"
            , nativeQuery = true)
    List<Annotation> findAllAnnotationsByCampaignId(@Param("campaignId") Long campaignId);

    @Query(value = "SELECT * FROM annotation an LEFT JOIN localized_peak_name lpn on an.id = lpn.annotation_id WHERE an.peak_id IN " +
            "(SELECT peak.id FROM peak WHERE campaign_id = :campaignId AND peak.to_be_annotated = 1 AND an.is_accepted_by_manager = 0)"
            , nativeQuery = true)
    List<Annotation> findRejectedAnnotationsByCampaignId(@Param("campaignId") Long campaignId);

    @Query(value = "SELECT count(distinct an.peak_id) FROM annotation an WHERE an.peak_id IN (SELECT peak.id FROM peak " +
            "WHERE campaign_id = :campaignId AND peak.to_be_annotated = 1)"
            , nativeQuery = true)
    int countPeaksInAnnotatedState(@Param("campaignId") Long campaignId);

    @Query(value = "SELECT count(distinct an.peak_id) FROM annotation an WHERE an.peak_id IN " +
            "(SELECT peak.id FROM peak WHERE campaign_id = :campaignId AND peak.to_be_annotated = 1) AND an.is_accepted_by_manager = 0"
            , nativeQuery = true)
    int countPeaksWithRejectedAnnotations(@Param("campaignId") Long campaignId);

    @Query(value = "    SELECT count(*) as conflicts FROM (SELECT count(DISTINCT is_valid) as conflicts FROM annotation an " +
            "WHERE an.peak_id IN  (SELECT id from peak WHERE peak.campaign_id = :campaignId)" +
            "GROUP BY an.peak_id HAVING conflicts > 1) as helper",
            nativeQuery = true)
    int countNumberOfConflictsByCampaignId(@Param("campaignId") Long campaignId);


}

