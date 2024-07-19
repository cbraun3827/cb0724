import com.toolrental.entity.RentalAgreement;
import com.toolrental.service.CheckoutService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class CheckoutServiceTest {
    CheckoutService checkoutService = new CheckoutService();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testCheckoutInvalidDiscount() {
        try {
            checkoutService.checkout("JAKR", LocalDate.of(2015, 9, 3), 5, 101);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Discount percent must be between 0 and 100", e.getMessage());
        } catch (Exception e) {
            fail("Expected IllegalArgumentException");
        }
    }

    @Test
    public void testCheckoutInvalidRentalDays () {
        CheckoutService checkoutService = new CheckoutService();
        try {
            checkoutService.checkout("LADW", LocalDate.now(), 0, 50);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Rental days must be greater than 0", e.getMessage());
        } catch (Exception e) {
            fail("Expected IllegalArgumentException");
        }
    }

    @Test
    public void testCheckoutLadder() {
        String expected = """
                Tool code: LADW
                Tool type: Ladder
                Tool brand: Werner
                Rental days: 3
                Checkout date: 07/02/20
                Due date: 07/05/20
                Daily rental charge: $1.99
                Charge days: 3
                Pre-discount charge: $5.97
                Discount percent: 10%
                Discount amount: $0.60
                Final charge: $5.37
                """;

        RentalAgreement rentalAgreement =  checkoutService.checkout("LADW", LocalDate.of(2020, 7, 2), 3, 10);
        assertNotNull(rentalAgreement);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testCheckoutChainsaw() {
        String expected = """
                Tool code: CHNS
                Tool type: Chainsaw
                Tool brand: Stihl
                Rental days: 5
                Checkout date: 07/02/15
                Due date: 07/07/15
                Daily rental charge: $1.49
                Charge days: 4
                Pre-discount charge: $5.96
                Discount percent: 25%
                Discount amount: $1.49
                Final charge: $4.47
                """;
        RentalAgreement rentalAgreement = checkoutService.checkout("CHNS", LocalDate.of(2015, 7, 2), 5, 25);
        assertNotNull(rentalAgreement);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testCheckoutDewaltJackhammer() {
        String expected = """
                Tool code: JAKD
                Tool type: Jackhammer
                Tool brand: DeWalt
                Rental days: 6
                Checkout date: 09/03/15
                Due date: 09/09/15
                Daily rental charge: $2.99
                Charge days: 3
                Pre-discount charge: $8.97
                Discount percent: 0%
                Discount amount: $0.00
                Final charge: $8.97
                """;

        RentalAgreement rentalAgreement = checkoutService.checkout("JAKD", LocalDate.of(2015, 9, 3), 6, 0);
        assertNotNull(rentalAgreement);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testCheckoutRidgidJackhammer() {
        String expected = """
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 9
                Checkout date: 07/02/15
                Due date: 07/11/15
                Daily rental charge: $2.99
                Charge days: 6
                Pre-discount charge: $17.94
                Discount percent: 0%
                Discount amount: $0.00
                Final charge: $17.94
                """;

        RentalAgreement rentalAgreement = checkoutService.checkout("JAKR", LocalDate.of(2015, 7, 2), 9, 0);
        assertNotNull(rentalAgreement);
        assertEquals(expected, outContent.toString());
    }

    @Test public void testCheckoutRidgidJackhammerWithDiscount() {
        String expected = """
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 4
                Checkout date: 07/02/20
                Due date: 07/06/20
                Daily rental charge: $2.99
                Charge days: 2
                Pre-discount charge: $5.98
                Discount percent: 50%
                Discount amount: $2.99
                Final charge: $2.99
                """;

        RentalAgreement rentalAgreement = checkoutService.checkout("JAKR", LocalDate.of(2020, 7, 2), 4, 50);
        assertNotNull(rentalAgreement);
        assertEquals(expected, outContent.toString());
    }


}
