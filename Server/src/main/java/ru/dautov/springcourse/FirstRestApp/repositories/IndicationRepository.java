package ru.dautov.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dautov.springcourse.FirstRestApp.models.Indication;

@Repository
public interface IndicationRepository extends JpaRepository<Indication, Integer> {
}
