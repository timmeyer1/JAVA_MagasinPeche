package com.magasinpeche.repository;

import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Long> {

    // Trouver tous les concours auxquels un client participe
    @Query("SELECT c FROM Concours c JOIN c.participations p WHERE p.client = :client")
    List<Concours> findConcoursByClient(Client client);

    // Récupérer les concours à venir, triés par date la plus proche
    @Query("SELECT c FROM Concours c WHERE c.date >= :currentDate ORDER BY c.date ASC")
    List<Concours> findUpcomingConcoursSortedByDate(@Param("currentDate") LocalDate currentDate);


    // Récupérer les 3 concours ayant la date la plus proche de maintenant
    List<Concours> findTop3ByDateGreaterThanOrderByDateAsc(LocalDate currentDate);
}
