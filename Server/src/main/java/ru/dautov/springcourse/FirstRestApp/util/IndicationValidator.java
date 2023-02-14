package ru.dautov.springcourse.FirstRestApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dautov.springcourse.FirstRestApp.models.Indication;
import ru.dautov.springcourse.FirstRestApp.services.SensorService;

@Component
public class IndicationValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public IndicationValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Indication.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Indication indication = (Indication) target;
        if (indication.getSensor() == null) {
            return;
        }

        if (sensorService.findByName(indication.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "Нет зарегестрированного сенсора с таким именем");
        }
    }
}
