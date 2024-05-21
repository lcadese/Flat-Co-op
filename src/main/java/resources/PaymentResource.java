package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PaymentDAO;
import domain.ErrorMessage;
import domain.Payments;
import domain.Task;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public class PaymentResource extends Jooby {

    private final Gson gson;

    public PaymentResource(PaymentDAO paymentDAO) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();

        path("/payments", () -> {

            post("/", ctx -> {
                Payments payment = gson.fromJson(ctx.body().value(), Payments.class);
                System.out.println(payment.toString());
                System.out.println(ctx.body().value());
                if (payment.getPaymentID() == null || payment.getPaymentID().isEmpty()) {
                    payment.setPaymentID(UUID.randomUUID().toString()); // Set a new UUID
                }
                try {
                    paymentDAO.createPayment(payment);
                    System.out.println(payment.toString());
                    return ctx.send(StatusCode.CREATED);
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error creating payment: " + e.getMessage())));
                }
            });

            get("/", ctx -> {
//                System.out.println("reached");
                return paymentDAO.getAllPayments();
            });

            get("/{paymentID}", ctx -> {
//                String taskID = ctx.path("taskID").value();
                String paymentID = ctx.path("paymentID").value();
                Payments payment = paymentDAO.getPayment(paymentID);
                if (payment != null) {
                    return ctx.setResponseType("application/json").send(gson.toJson(payment));
                } else {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.NOT_FOUND)
                            .send(gson.toJson(new ErrorMessage("Payment not found")));
                }
            });

            put("/{paymentID}", ctx -> {
                String paymentID = ctx.path("paymentID").value();
                Boolean payed = gson.fromJson(ctx.body().value(), Boolean.class);
                try {
                    paymentDAO.setPayed(paymentID, payed);
                    return ctx.setResponseType("application/json").send(gson.toJson("Payment status updated"));
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error updating payment status: " + e.getMessage())));
                }
            });

            get("/userID/{userID}",ctx ->{
                String userID = ctx.path("userID").value();
                Collection<Payments> payments = paymentDAO.getPaymentsByUserID(userID);
                return ctx.setResponseType("application/json").send(gson.toJson(payments));
            });

//            delete("/{taskID}/{userID}", ctx -> {
//                String taskID = ctx.path("taskID").value();
//                String userID = ctx.path("userID").value();
//                try {
//                    paymentDAO.removePayment(userID, taskID);
//                    return ctx.send(StatusCode.NO_CONTENT);
//                } catch (Exception e) {
//                    return ctx.setResponseType("application/json")
//                            .setResponseCode(StatusCode.BAD_REQUEST)
//                            .send(gson.toJson(new ErrorMessage("Error deleting payment: " + e.getMessage())));
//                }
//            });
        });
    }
}
