package com.api.payment_service.domain.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {
    //atributos apenas acessivel somente dentro da classe
    private UUID id;
    private UUID orderId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod method;
    private PaymentStatus status;


    //construtor para permitir inicializar os atributos
    public Payment(UUID id, UUID orderId, BigDecimal amount, String currency, PaymentMethod method, PaymentStatus status) {
        validateId(id);
        validateOrderId(orderId);
        validateAmount(amount);
        validateCurrency(currency);
        validateMethod(method);

        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    private void validateId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Payment ID is required.");
        }
    }

    private void validateOrderId(UUID orderId){
        if(orderId == null){
            throw new IllegalArgumentException("Payment order ID is required.");
        }
    }

    private void validateAmount(BigDecimal amount){
        if(amount == null){
            throw new IllegalArgumentException("Payment Amount is required.");
        }
    }

    private void validateCurrency(String currency){
        if(currency == null || currency.isBlank()){
            throw new IllegalArgumentException("Payment currency is required.");
        }
        if (currency.length() != 3){
            throw new IllegalArgumentException("Payment currency must have 3 characters.");
        }
    }

    private void validateMethod(PaymentMethod method){
        if(method == null){
            throw new IllegalArgumentException("Payment method is required.");
        }
    }

    private void validateStatus(PaymentStatus status){
        if(status == null){
            throw new IllegalArgumentException("Payment status is required.");
        }
    }

    //comportamentos/operações de dominio
    public  void toApprove(){
        if(status != PaymentStatus.PENDING){
            throw new IllegalArgumentException("Only pending payments can be approved.");
        }
         this.status = PaymentStatus.APPROVED;
    }

    public  void toReject(){
        if(status != PaymentStatus.PENDING){
            throw new IllegalArgumentException("Only pending payments can be rejected.");
        }
        this.status = PaymentStatus.REJECTED;
    }

    public  void toCancel(){
        if(status != PaymentStatus.APPROVED){
            throw new IllegalArgumentException("Only approved payments can be cancelled.");
        }
        this.status = PaymentStatus.CANCELLED;
    }

    public  void toRefund(){
        if(status != PaymentStatus.APPROVED){
            throw new IllegalArgumentException("Only approved payments can be refunded.");
        }
        this.status = PaymentStatus.REFUNDED;
    }


    // os getters e setters permite buscar e atualizar os atributos da classe

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
