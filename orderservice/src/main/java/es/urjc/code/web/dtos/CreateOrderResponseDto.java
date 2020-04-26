package es.urjc.code.web.dtos;

public class CreateOrderResponseDto {

    private Long orderId;
    private String message = "La Operación ha terminado con exito";

    public CreateOrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }

    public CreateOrderResponseDto(String message) {
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}