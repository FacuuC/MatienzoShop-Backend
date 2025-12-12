package com.matienzoShop.celulares.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Capta cualquier error que suceda
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {

        // Imprimir en la consola
        System.out.println("🛑 --- ERROR CAPTURADO EN BACKEND --- 🛑");
        ex.printStackTrace();

        // 2. Responder al Front
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error_type", ex.getClass().getSimpleName()); // Nos dice qué tipo de error es
        errorResponse.put("message", ex.getMessage()); // Nos dice el mensaje técnico

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Este Handler capta el error especifico de validación al validar la lista de objetos recibidas en el postMapping
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleListValidation(HandlerMethodValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        List<String> detalles = new ArrayList<>();

        // Iteramos directamente sobre los errores unificados
        ex.getAllErrors().forEach(error -> {
            String mensaje = error.getDefaultMessage();

            // Intentamos detectar si el error pertenece a un campo específico (ej: "precio")
            // Para eso verificamos si es una instancia de FieldError
            if (error instanceof FieldError fieldError) {
                mensaje = fieldError.getField() + ": " + mensaje;
            }

            detalles.add(mensaje);
        });

        response.put("error", "Error de Validación en la Lista");
        response.put("fallos_encontrados", detalles);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions (MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName =((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonErrors(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("error", "Error de Formato JSON");

        // Obtenemos el mensaje técnico (ej: "Cannot deserialize instance of ArrayList...")
        // Usamos getRootCause() para ir al detalle específico si existe
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String mensajeDetallado = mostSpecificCause != null ? mostSpecificCause.getMessage() : ex.getMessage();
        response.put("detalle", mensajeDetallado);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
 }
