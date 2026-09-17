package dev.yalerikk.paymentservice.api.errors;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, Long id) {
        super(entityName + " with id=" + id + " not found");
    }
}
