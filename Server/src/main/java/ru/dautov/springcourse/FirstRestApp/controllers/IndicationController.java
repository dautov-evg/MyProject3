package ru.dautov.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.dautov.springcourse.FirstRestApp.dto.IndicationDTO;
import ru.dautov.springcourse.FirstRestApp.models.Indication;
import ru.dautov.springcourse.FirstRestApp.services.IndicationService;
import ru.dautov.springcourse.FirstRestApp.util.IndicationErrorResponse;
import ru.dautov.springcourse.FirstRestApp.util.IndicationException;
import ru.dautov.springcourse.FirstRestApp.util.IndicationValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/indications")
public class IndicationController {
    private final IndicationService indicationService;
    private final IndicationValidator indicationValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public IndicationController(IndicationService indicationService, IndicationValidator indicationValidator, ModelMapper modelMapper) {
        this.indicationService = indicationService;
        this.indicationValidator = indicationValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<IndicationDTO> getIndications() {
        return indicationService.findAll().stream().map(this::convertToIndicationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return (int) indicationService.findAll().stream().filter(Indication::isRaining).count(); //int под вопросом
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid IndicationDTO indicationDTO,
                                          BindingResult bindingResult) {
        Indication indicationToAdd = convertToIndication(indicationDTO);

        indicationValidator.validate(indicationToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                        .append(";");
            }
            throw new IndicationException(errorMsg.toString());
        }

        indicationService.addIndication(indicationToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<IndicationErrorResponse> handleException(IndicationException e) {
        IndicationErrorResponse response = new IndicationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    private Indication convertToIndication(IndicationDTO indicationDTO) {
        return modelMapper.map(indicationDTO, Indication.class);
    }

    private IndicationDTO convertToIndicationDTO(Indication indication) {
        return modelMapper.map(indication, IndicationDTO.class);
    }
}
