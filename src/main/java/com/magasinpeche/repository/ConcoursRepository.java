package com.magasinpeche.repository;

import com.magasinpeche.model.Concours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Long> {

    @Query("SELECT c FROM Concours c JOIN c.participations p WHERE p.emailParticipant = :email")
    List<Concours> findParticipationsByEmailParticipant(@Param("email") String email);

    List<Concours> findAllByOrderByDateDesc();
}
