package ru.dautov.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dautov.springcourse.FirstRestApp.models.Indication;
import ru.dautov.springcourse.FirstRestApp.repositories.IndicationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IndicationService {
    private final IndicationRepository indicationRepository;
    private final SensorService sensorService;

    @Autowired
    public IndicationService(IndicationRepository indicationRepository, SensorService sensorService) {
        this.indicationRepository = indicationRepository;
        this.sensorService = sensorService;
    }

    public List<Indication> findAll() {
        return indicationRepository.findAll();
    }

    @Transactional
    public void addIndication(Indication indication) {
        enrichIndication(indication);
        indicationRepository.save(indication);
    }

    private void enrichIndication(Indication indication) {
        indication.setSensor(sensorService.findByName(indication.getSensor().getName()).get());
        indication.setIndicationDateTime(LocalDateTime.now());
    }
}
