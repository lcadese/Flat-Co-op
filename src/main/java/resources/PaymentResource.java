package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PaymentDAO;
import domain.ErrorMessage;
import domain.Payments;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.math.BigDecimal;

public class PaymentResource extends Jooby {
    private final Gson gson;

    public PaymentResource(PaymentDAO paymentDAO) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();

        path("/payments", () -> {

            post("/", ctx -> {
                Payments payment = gson.fromJson(ctx.body().value(), Payments.class);
                try {
                    paymentDAO.createPayment(payment);
                    return ctx.send(StatusCode.CREATED);
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error creating payment: " + e.getMessage())));
                }
            });

            get("/{taskID}/{userID}", ctx -> {
                String taskID = ctx.path("taskID").value();
                String userID = ctx.path("userID").value();
                Payments payment = paymentDAO.getPayment(userID, taskID);
                if (payment != null) {
                    return ctx.setResponseType("application/json").send(gson.toJson(payment));
                } else {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.NOT_FOUND)
                            .send(gson.toJson(new ErrorMessage("Payment not found")));
                }
            });

            put("/{taskID}/{userID}", ctx -> {
                String taskID = ctx.path("taskID").value();
                String userID = ctx.path("userID").value();
                Boolean payed = gson.fromJson(ctx.body().value(), Boolean.class);
                try {
                    paymentDAO.setPayed(userID, taskID, payed);
                    return ctx.setResponseType("application/json").send(gson.toJson("Payment status updated"));
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error updating payment status: " + e.getMessage())));
                }
            });

            delete("/{taskID}/{userID}", ctx -> {
                String taskID = ctx.path("taskID").value();
                String userID = ctx.path("userID").value();
                try {
                    paymentDAO.removePayment(userID, taskID);
                    return ctx.send(StatusCode.NO_CONTENT);
                } catch (Exception e) {
                    return ctx.setResponseType("application/json")
                            .setResponseCode(StatusCode.BAD_REQUEST)
                            .send(gson.toJson(new ErrorMessage("Error deleting payment: " + e.getMessage())));
                }
            });
        });
    }
}
