//package ait.cohort5860.accounting.controller;
//
//import ait.cohort5860.accounting.dto.exception.UserExistsException;
//import ait.cohort5860.accounting.dto.exception.UserNotFoundException;
//import ait.cohort5860.accounting.dto.exception.InvalidDataException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(UserExistsException.class)
//    public ResponseEntity<String> handleUserExists(UserExistsException e) {
//        System.out.println("Handling UserExistsException in GlobalExceptionHandler");
//        return ResponseEntity.status(HttpStatus.CONFLICT)
//                .body("User already exists");
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
//        System.out.println("Handling UserNotFoundException in GlobalExceptionHandler");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("User not found");
//    }
//
//    @ExceptionHandler(InvalidDataException.class)
//    public ResponseEntity<String> handleInvalidData(InvalidDataException e) {
//        System.out.println("Handling InvalidDataException in GlobalExceptionHandler");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("Invalid data provided");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneral(Exception e) {
//        System.out.println("Handling general exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Internal server error");
//    }
//}