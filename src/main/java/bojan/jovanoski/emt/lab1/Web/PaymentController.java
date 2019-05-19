package bojan.jovanoski.emt.lab1.Web;

import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.Models.Transaction;
import bojan.jovanoski.emt.lab1.Service.PaymentService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import bojan.jovanoski.emt.lab1.Service.TransactionService;
import bojan.jovanoski.emt.lab1.dto.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class PaymentController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    private TransactionService transactionService;

    private PaymentService paymentService;

    private ProductService productService;

    public PaymentController(PaymentService paymentService, ProductService productService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @RequestMapping("/checkout/{id}")
    public String checkoutProduct(@PathVariable("id") String ProductID, Long ID, Model model) {
        model.addAttribute("product", productService.getById(ID));
        model.addAttribute("name", productService.getById(ID).getName());
        model.addAttribute("amount", (int) productService.getById(ID).getPrice() * 100);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "checkoutProduct";
    }

    @PostMapping("/charge/{id}")
    public String chargeProduct(@PathVariable("id") Long ID, ChargeRequest chargeRequest, Model model) throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        Charge charge = paymentService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        if (charge.getStatus().equals("succeeded")) {
            Transaction transaction = new Transaction();
            transaction.setPurchasedProduct(productService.getById(ID));
            transaction.setUsername(chargeRequest.getStripeEmail());
            transaction.setTransactionDate(new Date());
            transaction.setAmount(productService.getById(ID).getPrice());
            transactionService.addNewTransaction(transaction);
        }
        return "checkoutResult";
    }


    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }

}
